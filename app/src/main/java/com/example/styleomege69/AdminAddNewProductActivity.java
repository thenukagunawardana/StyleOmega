package com.example.styleomege69;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.styleomege69.Prevalent.Prevalent;

public class AdminAddNewProductActivity extends AppCompatActivity
{
    private String CategoryName;
    private Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText ProductName_in,ProductDescription_in,ProductPrice_in;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName=getIntent().getExtras().get("category").toString();

        AddNewProductButton=(Button)findViewById(R.id.add_new_product);
        InputProductImage=(ImageView)findViewById(R.id.select_product_image);
        ProductName_in=(EditText)findViewById(R.id.product_name);
        ProductDescription_in=(EditText)findViewById(R.id.product_description);
        ProductPrice_in=(EditText)findViewById(R.id.product_price);






    }
}
