package com.example.ecomress;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecomress.Modal.Products;
import com.example.ecomress.prevalent.Prevalent;
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


public class ProductDetailsActivity extends AppCompatActivity {
    private Button addtoCart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName;
    private String Pid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pid=getIntent().getStringExtra("pid");
        setContentView(R.layout.activity_product_details);

       addtoCart=findViewById(R.id.add_product_to_cart_btn);
        numberButton=findViewById(R.id.product_price_number_BTN);
        productImage =findViewById(R.id.product_image_details);
        productPrice=findViewById(R.id.product_price_details);
        productDescription=findViewById(R.id.product_description_details);
        productName =findViewById(R.id.product_name_details);
        getProductDetails(Pid);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingCartToList();
            }
        });
    }

    private void addingCartToList() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
         saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calendar.getTime());
      final  DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Cart List");

       final HashMap<String, Object> cartMap=new HashMap<>();
        cartMap.put("pid",Pid);
        cartMap.put("name",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");
        ref.child("User View").child("phone")
                .child("Products").child(Pid).updateChildren(cartMap).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    ref.child("Admin View").child("phone")
                            .child("Products").child(Pid).updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {

                                        Toast.makeText(ProductDetailsActivity.this ,"Added to cart list" ,Toast.LENGTH_SHORT).show();
                                        Intent i =new Intent(ProductDetailsActivity.this,Drawer.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }

            }
        });


    }

    private void getProductDetails(String pid) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Products");

        ref.child(Pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    Products products=snapshot.getValue(Products.class);
                    productName.setText(products.getName());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
