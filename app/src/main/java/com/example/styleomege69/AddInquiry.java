//package com.example.styleomege69;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class AddInquiry extends AppCompatActivity
//{
//    private TextView productname;
//    private EditText subject,message;
//    private Button submit;
//    private String saveCurretnDate,saveCurrentTime,inquiryID,productName;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_inquiry);
//
//        productname=(TextView)findViewById(R.id.inquiry_producat_name);
//        subject=(EditText)findViewById(R.id.inquiry_subject);
//        message=(EditText)findViewById(R.id.inquiry_message);
//        submit=(Button)findViewById(R.id.inquiry_button);
//        productName=getIntent().getStringExtra("Name");
//        productname.setText(productName);
//    }
//}
