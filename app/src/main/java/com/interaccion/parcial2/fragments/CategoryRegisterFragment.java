package com.interaccion.parcial2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.interaccion.parcial2.R;
import com.interaccion.parcial2.activities.MainActivity;
import com.interaccion.parcial2.database.DatabaseHelper;


public class CategoryRegisterFragment extends Fragment {


    private EditText categoryNameET;
    private Button saveCategoryBtn;
    private DatabaseHelper myDb;

    public CategoryRegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_category_register, container, false);

        categoryNameET = view.findViewById(R.id.categoryNameEt);
        saveCategoryBtn = view.findViewById(R.id.saveCategoryBtn);
        myDb = MainActivity.myDb;

        saveCategoryBtn.setOnClickListener(v -> {

            String name = categoryNameET.getText().toString().trim();

            if(TextUtils.isEmpty(name)) {
                saveCategoryBtn.setError("Este campo no puede estar vacio");
                saveCategoryBtn.setFocusable(true);
                return;
            }

            //TODO save data on db

            boolean isInserted = myDb.insertCategory(name);

            if (isInserted) {
                Toast.makeText(getActivity(), "Category inserted correctly!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Problem inserting Category!", Toast.LENGTH_SHORT).show();
            }

            ProductHandlerFragment productHandlerFragment = new ProductHandlerFragment();

            if(this.getArguments() != null){

                Bundle bundle = this.getArguments();
                productHandlerFragment.setArguments(bundle);

            }

            getFragmentManager().beginTransaction().replace(R.id.flFragment, productHandlerFragment, "REGISTERFRAG").commit();

        });
        return view;
    }
}