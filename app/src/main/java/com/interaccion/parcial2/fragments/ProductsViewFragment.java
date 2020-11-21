package com.interaccion.parcial2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.interaccion.parcial2.R;
import com.interaccion.parcial2.activities.MainActivity;
import com.interaccion.parcial2.adapters.RecyclerViewAdapter;
import com.interaccion.parcial2.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class ProductsViewFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseHelper myDb;

    public ProductsViewFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_view, container, false);
        recyclerView = view.findViewById(R.id.productsFragmentRv);
        myDb = MainActivity.myDb;


        int i = 0;
        ArrayList<HashMap<String, String>> productsList = myDb.getAllProducts();
        String[] productIds = new String[productsList.size()];
        String[] productNames = new String[productsList.size()];
        String[] productPrice = new String[productsList.size()];
        String[] productCategory = new String[productsList.size()];

        for (HashMap<String, String> category : productsList) {
            productIds[i] = category.get("id");
            productNames[i] = category.get("name");
            productPrice[i] = category.get("price");
            productCategory[i] = category.get("category");
            i++;
        }

        Log.wtf("PRODUCTS VIEW FRAGMENT", "DATA: " + productsList.toString());

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),productIds, productNames, productPrice, productCategory);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        //TODO do the thing for the db to work and get the data for the array adapter
//        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.fragment_concept_list_view, R.id.tv_lv, conceptNames);
//
//        listView.setAdapter(adapter);
        return view;
    }
}