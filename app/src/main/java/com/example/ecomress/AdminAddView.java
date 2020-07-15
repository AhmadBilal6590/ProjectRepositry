package com.example.ecomress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddView extends AppCompatActivity {
    private String CatagoryName, pDescription, price, pname, saveCurrentDate, saveCurrentTime;
    private ImageView InputProductImage;
    private Button AddNewProductBtn;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int REQ_CODE_GalleryPic=1;
    private Uri ImageUri;
    private  String ProductRandomKey, downloadImageUrl;
    private StorageReference productImagesRef;
    private DatabaseReference productsRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_view);
        CatagoryName=getIntent().getExtras().get("Category").toString();
        productsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        productImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        AddNewProductBtn=(Button)findViewById(R.id.product_add_btn);
        InputProductImage=(ImageView)findViewById(R.id.select_product_image);
        InputProductName=(EditText)findViewById(R.id.product_name);
        InputProductDescription=(EditText)findViewById(R.id.product_description);
        InputProductPrice=(EditText)findViewById(R.id.product_price);

        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        AddNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             ValidateProductData();
            }
        });


    }

    private void ValidateProductData() {
        pDescription=InputProductDescription.getText().toString();
        price=InputProductPrice.getText().toString();
        pname=InputProductName.getText().toString();
        if(ImageUri==null)
        {
            Toast.makeText(AdminAddView.this," product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pDescription))
        {
            Toast.makeText(AdminAddView.this,"product description is mandatory...", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(price))
        {
            Toast.makeText(AdminAddView.this,"product price is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pname))
        {
            Toast.makeText(AdminAddView.this,"product name is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }

    private void  StoreProductInformation() {
        Calendar calender=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calender.getTime());


        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentTime.format(calender.getTime());

           ProductRandomKey    = saveCurrentDate + saveCurrentTime;

           final StorageReference filePath=productImagesRef.child(ImageUri.getLastPathSegment() + ProductRandomKey + ".jpg");
           final UploadTask uploadTask=filePath.putFile(ImageUri);

           uploadTask.addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   String message=e.toString();
                   Toast.makeText(AdminAddView.this,"Error"+message, Toast.LENGTH_SHORT).show();

               }
           }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Toast.makeText(AdminAddView.this,"Image uploaded Successfuly..", Toast.LENGTH_SHORT).show();

                   Task<Uri>  uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                       @Override
                       public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                           if(!task.isSuccessful())
                           {
                               throw task.getException();

                           }
                           downloadImageUrl= filePath.getDownloadUrl().toString();
                           return filePath.getDownloadUrl();
                       }
                   }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                       @Override
                       public void onComplete(@NonNull Task<Uri> task) {
                           if(task.isSuccessful())
                           {
                               downloadImageUrl=task.getResult().toString();
                               Toast.makeText(AdminAddView.this,"getting product image url successfully", Toast.LENGTH_SHORT).show();
                               saveProductInfoToDataBase();
                           }
                       }
                   });
               }
           });

    }

    private void saveProductInfoToDataBase() {

        HashMap<String, Object> productMap=new HashMap<>();
        productMap.put("pid",ProductRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",pDescription);
        productMap.put("image",downloadImageUrl);
        productMap.put("catagory",CatagoryName);
        productMap.put("price",price);
        productMap.put("name",pname);

        productsRef.child(ProductRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent=new Intent(AdminAddView.this,AdminCatagoryActivity.class);
                            startActivity(intent);

                            Toast.makeText(AdminAddView.this,"product is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            String message=task.getException().toString();
                            Toast.makeText(AdminAddView.this,"Error"+ message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void OpenGallery() {
        Intent galleryIntent= new Intent();
         galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
         galleryIntent.setType("image/*");
         startActivityForResult(galleryIntent,REQ_CODE_GalleryPic);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_CODE_GalleryPic && resultCode==RESULT_OK &&  data!=null)
        {
            ImageUri=data.getData();

            InputProductImage.setImageURI(ImageUri);

        }
    }
}
