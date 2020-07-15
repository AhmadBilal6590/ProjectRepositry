package com.example.ecomress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.ecomress.Modal.UserClass;
import com.example.ecomress.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
     private Button joinNowButton,loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        joinNowButton=(Button)findViewById(R.id.main_join_btn);
        loginButton=(Button)findViewById(R.id.main_login_btn);
        Paper.init(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this, login.class);
                startActivity(intent);

            }
        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });

        String UserPhoneKey=Paper.book().read(Prevalent.UserPhonekey);
        String Userpasswordkey=Paper.book().read(Prevalent.Userpasswordkey);
        if(UserPhoneKey != "" && Userpasswordkey !="")
        {    if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(Userpasswordkey) )
        {
            AllowAccess(UserPhoneKey,Userpasswordkey);
        }


        }








    }

    private void AllowAccess(final String phone, final String password) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("User").child(phone).exists())
                {
                     UserClass usersData=dataSnapshot.child("User").child(phone).getValue(UserClass.class);
//                    String name1=  dataSnapshot.child(parentDbName).child(phone).child("name").getValue().toString();
//                    String password1=  dataSnapshot.child(parentDbName).child(phone).child("password").getValue().toString();
//                    String phone1=  dataSnapshot.child(parentDbName).child(phone).child("phone").getValue().toString();

                    if(usersData.getPhone().equals(phone))
                    {

                        if(usersData.getPassword().equals(password))
                        {

//                            if(parentDbName.equals("admins"))
//                            {
//                                Toast.makeText(MainActivity.this,"Admin login successfully",Toast.LENGTH_SHORT).show();
//                                Intent intent=new Intent(MainActivity.this,AdminCatagoryActivity.class);
//                                startActivity(intent);
//
//
//
//                            }
                        //
                           // {
                                Toast.makeText(MainActivity.this,"login successfully",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(MainActivity.this,Drawer.class);
                                startActivity(intent);
                                //   Prevalent.CurrentonlineUser=userdata;

                          //  }


                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "password is incorrect",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this "+phone+" number do not exits",Toast.LENGTH_SHORT).show();

                   // Toast.makeText(MainActivity.this,"you need to create a new account",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
