package com.example.ecomress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecomress.prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    private ImageView profilePic;
    private EditText fullNameEditTxt,phoneEditTxt,addressEditTxt;
    private TextView profileChnageTxtbtn, closeTxtbtn,saveTxtbtn;
    private Uri imageUri;
    private String myUrl="";
    private StorageReference storageProfilepicRef;
    private String Checker="";
    private StorageTask uploadTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
      storageProfilepicRef= FirebaseStorage.getInstance().getReference().child("Profile Pictures");
        profilePic=findViewById(R.id.settings_profile_image);
        fullNameEditTxt  =findViewById(R.id.settings_Fullname);
        phoneEditTxt=findViewById(R.id.settings_phone_number);
        addressEditTxt=findViewById(R.id.settings_Address);
        profileChnageTxtbtn=findViewById(R.id.Update_profile_Settings_btn);
        closeTxtbtn=findViewById(R.id.close_settings);
        saveTxtbtn=findViewById(R.id.Update_settings);

        closeTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saveTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Checker.equals("clicked"))
                {
                    userInfoSaved();

                }
                else{
                    updateOnlyUserInfo();

                }
            }
        });
        profileChnageTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checker="clicked";
                CropImage.activity(imageUri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);
            }
        });


        userInfoDisplay(profilePic, fullNameEditTxt,phoneEditTxt,addressEditTxt);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri =result.getUri();
            profilePic.setImageURI(imageUri);

        }
        else{
            Toast.makeText(this,"Error ,Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this,SettingsActivity.class));
            finish();
        }
    }



    private void userInfoSaved() {
         if(TextUtils.isEmpty(fullNameEditTxt.getText().toString()))
         {
             Toast.makeText(this,"Name is Manadorty", Toast.LENGTH_SHORT).show();
         }
       else if(TextUtils.isEmpty(addressEditTxt.getText().toString()))
        {
            Toast.makeText(this,"Address is Manadorty", Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(phoneEditTxt.getText().toString()))
        {
            Toast.makeText(this,"Name is Manadorty", Toast.LENGTH_SHORT).show();
        }
       else if(Checker.equals("clicked"))
        {
           uploadImage();
        }






    }

    private void uploadImage() {
        if(imageUri != null)
        {
            final StorageReference fileRef=storageProfilepicRef.child(Prevalent.CurrentonlineUser.getPhone() + ".jpg");
            uploadTask=fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {


                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                    {
                        Uri downloadUrl=  task.getResult();
                        myUrl =downloadUrl.toString();
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("User");
                        HashMap<String, Object> userMap=new HashMap<>();
                        userMap.put("name",fullNameEditTxt.getText().toString());
                        userMap.put("address",addressEditTxt.getText().toString());
                        userMap.put("phone",phoneEditTxt.getText().toString());
                        userMap.put("image", myUrl);
                        ref.child("phone").updateChildren(userMap);

                        startActivity(new Intent(SettingsActivity.this,Drawer.class));
                        finish();

                    }
                    else
                    {
                        Toast.makeText(SettingsActivity.this,"Error", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
        else
        {
            Toast.makeText(SettingsActivity.this,"Image is not selected", Toast.LENGTH_SHORT).show();

        }

    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("User");
        HashMap<String, Object> userMap=new HashMap<>();
        userMap.put("name",fullNameEditTxt.getText().toString());
        userMap.put("address",addressEditTxt.getText().toString());
        userMap.put("phone",phoneEditTxt.getText().toString());
        ref.child("phone").updateChildren(userMap);


        startActivity(new Intent(SettingsActivity.this,MainActivity.class));
        finish();


    }


    private void userInfoDisplay(final ImageView profilePic, final EditText fullNameEditTxt, final EditText phoneEditTxt,final EditText addressEditTxt) {

        DatabaseReference  UserRef= FirebaseDatabase.getInstance().getReference().child("User").child("phone");


        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("image").exists())
                {
                    String image=snapshot.child("image").getValue().toString();
                    String  name=snapshot.child("name").getValue().toString();
                    String  phone=snapshot.child("phone").getValue().toString();
                    String address=snapshot.child("address").getValue().toString();
                    Picasso.get().load(image).into(profilePic);
                    fullNameEditTxt.setText(name);
                    phoneEditTxt.setText(phone);
                    addressEditTxt.setText(address);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
