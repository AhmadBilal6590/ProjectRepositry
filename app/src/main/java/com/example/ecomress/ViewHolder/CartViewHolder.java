package com.example.ecomress.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ecomress.Interface.itemClickListner;
import com.example.ecomress.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, TxtProductPrice, TxtProductquantity;
    public itemClickListner Listner;


    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        TxtProductPrice = itemView.findViewById(R.id.cart_product_price);
        TxtProductquantity = itemView.findViewById(R.id.cart_product_quantity);


    }


    @Override
    public void onClick(View v) {
        Listner.onClick(v, getAdapterPosition(), false);

    }

    public void setItemClickListner(itemClickListner listner) {
        this.Listner = listner;

        {

        }


    }

}
