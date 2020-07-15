package com.example.ecomress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminCatagoryActivity extends AppCompatActivity {


    private ImageView tShirts, sportsTshirts, femaleDress, sweathers;
    private ImageView glasses, hatsCaps, walletsBagsPurs, shoes;
    private ImageView  headPhonesHandFree, Laptops, watches, mobilePhones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_catagory);

        tShirts=(ImageView) findViewById(R.id.t_shirt);
        sportsTshirts=(ImageView) findViewById(R.id.sports_t_shirt);
        femaleDress=(ImageView) findViewById(R.id.female_dresses);
        sweathers=(ImageView)findViewById(R.id.sweather);
        glasses=(ImageView)findViewById(R.id.glasses);
        hatsCaps=(ImageView)findViewById(R.id.hat);
        walletsBagsPurs=(ImageView)findViewById(R.id.pursses);
        shoes=(ImageView)findViewById(R.id.shoeses);
        headPhonesHandFree=(ImageView)findViewById(R.id.headphoness);
        Laptops=(ImageView)findViewById(R.id.laptops);
        watches=(ImageView)findViewById(R.id.watches);
        mobilePhones=(ImageView)findViewById(R.id.mobile);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class );
                intent.putExtra("Category", "tShirts");
                startActivity(intent);
            }
        });
        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "sportsTshirts");
                startActivity(intent);

            }
        });
        femaleDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "femaleDress");
                startActivity(intent);

            }
        });

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "sweathers");
                startActivity(intent);

            }
        });
        hatsCaps .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "hatsCaps");
                startActivity(intent);

            }
        });
        walletsBagsPurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "walletsBagsPurs");
                startActivity(intent);

            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "shoes ");
                startActivity(intent);

            }
        });

        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "headPhonesHandFree");
                startActivity(intent);

            }
        });
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "Laptops");
                startActivity(intent);

            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "watches");
                startActivity(intent);

            }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminCatagoryActivity.this,AdminAddView.class);
                intent.putExtra("Category", "mobilePhones");
                startActivity(intent);

            }
        });


    }
}
