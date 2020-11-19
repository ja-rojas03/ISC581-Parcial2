package com.interaccion.parcial2.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.interaccion.parcial2.R;


public class ProductsViewFragment extends Fragment {

    ListView listView;

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
        listView = view.findViewById(R.id.productsFragmentLv);


        //TODO do the thing for the db to work and get the data for the array adapter
//        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.fragment_concept_list_view, R.id.tv_lv, conceptNames);
//
//        listView.setAdapter(adapter);
        return view;
    }
}