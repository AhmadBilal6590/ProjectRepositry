package com.example.ecomress.ViewHolder;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnContextClickListener {
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public boolean onContextClick(View v) {
        return false;
    }
}
