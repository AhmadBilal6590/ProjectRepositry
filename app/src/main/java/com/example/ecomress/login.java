package com.example.ecomress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecomress.Modal.UserClass;
import com.example.ecomress.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login<Private> extends AppCompatActivity {

    private EditText InputPhoneNumber,InputPassword;
    private Button LoginButton;
    private TextView AdminLink, NotAdminLink;
    private  String parentDbName="User";
    private CheckBox chkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton=(Button) findViewById(R.id.login_btn);
        chkBox=findViewById(R.id.login_checkBox1);
        Paper.init(this);
        AdminLink=(TextView) findViewById(R.id.admin_panel_link);
        NotAdminLink=(TextView) findViewById(R.id.Notadmin_panel_link);
        InputPhoneNumber=(EditText)findViewById(R.id.login_phone_number_input);
        InputPassword=(EditText) findViewById(R.id.login_password_input);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDbName="admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDbName="User";
            }
        });

    }

    private void LoginUser() {
        String phone=InputPhoneNumber.getText().toString();
        String password=InputPassword.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "please write your phone number.." ,Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "pleas write your password.."  ,Toast.LENGTH_SHORT).show();
        }
        else
        {
         AllowAccessToAccount(phone,password);
        }
    }

    private  void AllowAccessToAccount(final String phone, final String password)
    {
        if(chkBox.isChecked())
        {
            Paper.book().write(Prevalent.UserPhonekey,phone);
            Paper.book().write(Prevalent.Userpasswordkey,password);

        }
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();


    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                UserClass usersData=dataSnapshot.child(parentDbName).child(phone).getValue(UserClass.class);
//                    String name1=  dataSnapshot.child(parentDbName).child(phone).child("name").getValue().toString();
//                    String password1=  dataSnapshot.child(parentDbName).child(phone).child("password").getValue().toString();
//                    String phone1=  dataSnapshot.child(parentDbName).child(phone).child("phone").getValue().toString();

                 if(usersData.getPhone().equals(phone))
                 {

                     if(usersData.getPassword().equals(password))
                     {

                         if(parentDbName.equals("admins"))
                         {
                             Toast.makeText(login.this,"Admin login successfully",Toast.LENGTH_SHORT).show();
                             Intent intent=new Intent(login.this,AdminCatagoryActivity.class);
                             startActivity(intent);



                         }
                         else if(parentDbName.equals("User"))
                         {
                             Toast.makeText(login.this,"login successfully",Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(login.this,Drawer.class);
                                startActivity(intent);
                             //   Prevalent.CurrentonlineUser=userdata;

                         }


                     }
                     else
                     {
                         Toast.makeText(login.this, "password is incorrect",Toast.LENGTH_SHORT).show();
                     }
                 }
                }
                else
                {
                    Toast.makeText(login.this, "Account with this "+phone+" number do not exits",Toast.LENGTH_SHORT).show();

                    Toast.makeText(login.this,"you need to create a new account",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
