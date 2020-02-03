package com.example.styleomege69;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.styleomege69.Model.Products;
import com.example.styleomege69.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity
{
    private Button addToCartButton,ShareButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productDescription,productName,productPrice;
    private String productID ="",state="normal";
    private Products currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID=getIntent().getStringExtra("pid");

        addToCartButton=(Button)findViewById(R.id.pd_add_cart);
        productImage=(ImageView)findViewById(R.id.product_image_details);
        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn);
        productDescription=(TextView)findViewById(R.id.product_description_details);
        productName=(TextView)findViewById(R.id.product_name_details);
        productPrice=(TextView)findViewById(R.id.product_price_details);

        ShareButton=(Button)findViewById(R.id.shareProduct);

        getProductDetails();

        addToCartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (state.equals("Order Placed")||state.equals("Order Shipped"))
                {
                    Toast.makeText(ProductDetailsActivity.this, "Your Order Needs to be Shipped to Purchase More Products ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    addToCartList();
                }
            }
        });

        ShareButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                String message=currentProduct.getPname()+"\n"+currentProduct.getDescription()+"\n"+currentProduct.getPrice();
                intent.putExtra(Intent.EXTRA_TEXT,message);
                intent.setType("text/plain");
                Intent share=Intent.createChooser(intent,null);
                startActivity(share);

            }
        });




    }

    @Override
    protected void onStart()
    {
        super.onStart();

        CheckOrderState();
    }

    private void addToCartList()
    {
        String saveCurrrentTime,saveCurrentDate;

        Calendar callForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrrentTime=currentDate.format(callForDate.getTime());

        final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference()
                .child("Cart List");

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getName())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getName())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(ProductDetailsActivity.this, "Added To Cart List", Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(ProductDetailsActivity.this,HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });







    }

    private void getProductDetails()
    {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productID).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    Products products=dataSnapshot.getValue(Products.class);
                    productName.setText(products.getPname());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);
                    currentProduct=products;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef=FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getName());
        ordersRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    String shippingState=dataSnapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                       state="Order Shipped";
                    }
                    else if(shippingState.equals("not shipped"))
                    {
                        state="Order Placed";
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }


}
