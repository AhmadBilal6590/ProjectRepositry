package com.example.ecomress.ViewHolder;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomress.R;
import com.example.ecomress.Interface.itemClickListner;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,textProductDescription , textProductPrice;
    public ImageView imageView;
    public itemClickListner  Listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.Product_image_card);
        txtProductName=itemView.findViewById(R.id.Product_name_card);
        textProductDescription=itemView.findViewById(R.id.Product_description_card);
        textProductPrice=itemView.findViewById(R.id.Product_price_card);



    }
    public void setItemclickListner(itemClickListner listner)
    {
        this.Listner=listner;

    }





    @Override
    public  void onClick(View v)
    {
        Listner.onClick(v,getAdapterPosition(),false);

    }

}

