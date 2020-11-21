package com.interaccion.parcial2.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.interaccion.parcial2.R;
import com.interaccion.parcial2.activities.MainActivity;
import com.interaccion.parcial2.fragments.ProductHandlerFragment;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private String[] id;
    private String[] name;
    private String[] price;
    private String[] category;
    private Context context;


    public RecyclerViewAdapter(Context context, String ids[] ,String names[], String prices[], String categories[]) {
        Log.wtf("RECYCLER VIEW ADAPTER" , "ADAPTER CREATED");
        this.id = ids;
        this.context = context;
        this.name = names;
        this.price = prices;
        this.category = categories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_products_list_view, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.wtf("RECYCLERVIEW", "DATA: " + name[position] + price[position] + category[position]);
        holder.names.setText(name[position]);
        holder.price.setText(price[position]);
        holder.category.setText(category[position]);

        holder.itemView.setOnClickListener( v-> {
            Bundle bundle = new Bundle();
            bundle.putString("NAME", name[position]);
            bundle.putString("PRICE", price[position]);
            bundle.putString("ID", id[position]);
            bundle.putBoolean("MODIFY", true);

            MainActivity activity = (MainActivity) context;
            Fragment productHandlerFragment = new ProductHandlerFragment();
            productHandlerFragment.setArguments(bundle);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, productHandlerFragment, "REGISTERFRAG").commit();


        });
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView names,price,category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.productNameTv);
            price = itemView.findViewById(R.id.productPriceTv);
            category = itemView.findViewById(R.id.productCategoryTv);
        }
    }
}
