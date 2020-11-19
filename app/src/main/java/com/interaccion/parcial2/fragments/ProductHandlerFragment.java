package com.interaccion.parcial2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.interaccion.parcial2.R;


public class ProductHandlerFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText productNameET;
    private EditText productPriceET;
    private Spinner categorySpn;
    private Button addCategoryBtn;
    private Button saveProductBtn;
    private Button modifyProductBtn;
    private Button deleteProductBtn;

    public ProductHandlerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_register, container, false);

        productNameET = view.findViewById(R.id.productNameET);
        productPriceET = view.findViewById(R.id.productPriceET);
        categorySpn = view.findViewById(R.id.CategoriesSpn);
        addCategoryBtn = view.findViewById(R.id.addCategoryBtn);
        saveProductBtn = view.findViewById(R.id.saveProductBtn);
        modifyProductBtn = view.findViewById(R.id.modifyProductBtn);
        deleteProductBtn = view.findViewById(R.id.deleteProductBtn);


        //Fill with info if coming back from creating a new category
        if(this.getArguments() != null){
            Bundle bundle = this.getArguments();
            Log.wtf("ARGS", "ARGS" + bundle);
            if(bundle.getBoolean("MODIFY")) {
                saveProductBtn.setVisibility(View.GONE);
                deleteProductBtn.setVisibility(View.VISIBLE);
                modifyProductBtn.setVisibility(View.VISIBLE);
            }
            if(bundle.getString("NAME") != null) {
                productNameET.setText(bundle.getString("NAME"));
            }
            if(bundle.getString("PRICE") != null) {
                productPriceET.setText(bundle.getString("PRICE"));
            }

            //TODO HANDLE BUNDLE . GET STRING FOR SPINNER
        }


        //TODO fill up an String array with database's data to change "genders" variable in line below
//        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genders);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categorySpn.setAdapter(adapter);
//        categorySpn.setOnItemSelectedListener(this);

        addCategoryBtn.setOnClickListener( v-> {

            CategoryRegisterFragment categoryRegisterFragment = new CategoryRegisterFragment();
            Bundle bundle = createBundle();

            categoryRegisterFragment.setArguments(  bundle != null ? bundle : null);
            getFragmentManager().beginTransaction().replace(R.id.flFragment, categoryRegisterFragment, "CATEGORYFRAG").commit();

        });

        saveProductBtn.setOnClickListener( v-> {

            if (!checkValues()) return;

            //TODO Save data on database
            //

            //TODO handle spinner's value
            //
            getFragmentManager().beginTransaction().replace(R.id.flFragment, new ProductsViewFragment(), "VIEWFRAG").commit();

        });

        modifyProductBtn.setOnClickListener( v-> {

            if (!checkValues()) return;
            //TODO update data on db

        });



        return view;
    }

    private Bundle createBundle() {

        String name = productNameET.getText().toString().trim();
        String price = productPriceET.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
            Bundle bundle = new Bundle();
            bundle.putString("NAME", name);
            bundle.putString("PRICE", price);

            return bundle;
        }

        else if(!TextUtils.isEmpty(name)) {
            Bundle bundle = new Bundle();
            bundle.putString("NAME", name);

            return bundle;
        }
        else if(!TextUtils.isEmpty(price)) {
            Bundle bundle = new Bundle();
            bundle.putString("PRICE", price);

            return bundle;
        }

        return null;
    }

    private Boolean checkValues() {
        String name = productNameET.getText().toString().trim();
        String price = productPriceET.getText().toString().trim();

        if(TextUtils.isEmpty(name)) {
            productNameET.setError("Este campo no puede estar vacio");
            productNameET.setFocusable(true);
            return false;
        }

        else if(TextUtils.isEmpty(price)) {
            productPriceET.setError("Este campo no puede estar vacio");
            productPriceET.setFocusable(true);
            return false;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}