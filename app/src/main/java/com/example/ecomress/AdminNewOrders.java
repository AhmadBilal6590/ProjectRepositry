package com.example.ecomress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class AdminNewOrders extends AppCompatActivity {
    private String CatagoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);
        CatagoryName=getIntent().getExtras().get("Category").toString();
        Toast.makeText(this,CatagoryName ,Toast.LENGTH_SHORT).show();
    }
}
