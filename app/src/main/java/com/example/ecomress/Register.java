package com.example.ecomress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    private Button createAccountBtn;
    private EditText InputName,InputPhoneNumber,InputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createAccountBtn=(Button) findViewById(R.id.Register_btn);
        InputName=(EditText)findViewById(R.id.Register_name_input);
        InputPhoneNumber=(EditText)findViewById(R.id.Register_phone_number_input);
        InputPassword=(EditText) findViewById(R.id.Register_password_input);
        
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        
        String name=InputName.getText().toString();
        String phone=InputPhoneNumber.getText().toString();
        String password=InputPassword.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "pleas write your name..", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "please write your phone number.." ,Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "pleas write your password.."  ,Toast.LENGTH_SHORT).show();
        }
        else
        {
            ValidatePhoneNumber(name,phone,password);
        }
    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password) {
     final DatabaseReference RootRef;
     RootRef= FirebaseDatabase.getInstance().getReference();
     RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             if(!(dataSnapshot.child("User").child(phone).exists()))
             {
                 HashMap<String, Object> userDataMap=new HashMap<>();
                 userDataMap.put("phone",phone);
                 userDataMap.put("password",password);
                 userDataMap.put("name", name);
                 RootRef.child("User").child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task)
                     {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(Register.this ,"Congratulation , your account is created", Toast.LENGTH_SHORT).show();
                          Intent intent=new Intent(Register.this ,login.class);
                          startActivity(intent);
                      }
                      else
                      {

                          Toast.makeText(Register.this, "Network Error Pleas try Again..",Toast.LENGTH_SHORT).show();
                      }


                     }
                 });

             }
             else
             {
                 Toast.makeText(Register.this ,"this" + phone + "already exits",Toast.LENGTH_SHORT).show();
                 Toast.makeText(Register.this , "pleas try again with another phone number", Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(Register.this ,MainActivity.class);
                 startActivity(intent);
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
    }
}
