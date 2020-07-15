package com.example.ecomress;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomress.Modal.Cart;
import com.example.ecomress.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NexProcessBtn;
    private TextView txtTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.Cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NexProcessBtn=findViewById(R.id.next_process_btn);
        txtTotalAmount=findViewById(R.id.total_Price_btn_cart_screen);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(ref.child("User View").child("phone").child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtProductName.setText(model.getName());
                holder.TxtProductPrice.setText("Price "+model.getPrice() +"$");
                holder.TxtProductquantity.setText("Quantity =" +model.getQuantity());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]
                        {
                                "Edit",
                                "Remove"


                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
                        builder.setTitle("Cart Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0)
                                {
                                    Intent i=new Intent(Main2Activity.this,ProductDetailsActivity.class);
                                    i.putExtra("pid", model.getPid());
                                    startActivity(i);


                                }
                                if(which==1)
                                {
                                       ref.child("User View")
                                               .child("phone").child("Products").child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {

                                               if(task.isSuccessful())
                                               {
                                                   Toast.makeText(Main2Activity.this, "Item removed" ,Toast.LENGTH_SHORT).show();

                                                   Intent i=new Intent(Main2Activity.this,Drawer.class);
                                                   i.putExtra("pid", model.getPid());
                                                   startActivity(i);
                                               }

                                           }
                                       });


                                }

                            }
                        });
                        builder.show();

                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
               View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
               CartViewHolder holder=new CartViewHolder(v);
               return  holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        }


}
