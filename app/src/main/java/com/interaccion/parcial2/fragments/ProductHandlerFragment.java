package com.interaccion.parcial2.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.Toast;

import com.interaccion.parcial2.R;
import com.interaccion.parcial2.activities.MainActivity;
import com.interaccion.parcial2.database.DatabaseHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class ProductHandlerFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText productNameET;
    private EditText productPriceET;
    private Spinner categorySpn;
    private Button addCategoryBtn;
    private Button saveProductBtn;
    private Button modifyProductBtn;
    private Button deleteProductBtn;
    private Button deleteCategoryBtn;
    private String spn_opt;
    private DatabaseHelper myDb;
    private ArrayList<HashMap<String, String>> categoryList;

    private String[] cat = {"NO CATEGORIES AVAILABLE"};;

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
        deleteCategoryBtn = view.findViewById(R.id.deleteCategoryBtn);
        myDb = MainActivity.myDb;
        //Spinner
        fillSpinner();


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

        }

        /*
           HANDLING ON BUTTONS PRESSED
           ><><><><><><><><><><><><><><><><><
        */
        addCategoryBtn.setOnClickListener( v-> {

            CategoryRegisterFragment categoryRegisterFragment = new CategoryRegisterFragment();
            Bundle bundle = createBundle();

            categoryRegisterFragment.setArguments(  bundle != null ? bundle : null);
            getFragmentManager().beginTransaction().replace(R.id.flFragment, categoryRegisterFragment, "CATEGORYFRAG").commit();

        });

        saveProductBtn.setOnClickListener( v-> {

            if (!checkValues()) return;

            String name = productNameET.getText().toString().trim();
            String price = productPriceET.getText().toString().trim();

            boolean isInserted = myDb.insertProductData(name, price, spn_opt);
            if (isInserted) {
                Toast.makeText(getActivity(), "Product created correctly !", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Error creating Product", Toast.LENGTH_SHORT).show();
            }

            //Change Fragment
            getFragmentManager().beginTransaction().replace(R.id.flFragment, new ProductsViewFragment(), "VIEWFRAG").commit();

        });

        modifyProductBtn.setOnClickListener( v-> {

            if (!checkValues()) return;

            String name = productNameET.getText().toString().trim();
            String price = productPriceET.getText().toString().trim();

            Bundle bundle = this.getArguments();

            boolean isUpdated = myDb.updateProduct(bundle.getString("ID") , name, price, spn_opt);

            if(isUpdated) {
                Toast.makeText(getActivity(), "Updated correctly ", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "Update failed", Toast.LENGTH_SHORT).show();
            }

            getFragmentManager().beginTransaction().replace(R.id.flFragment, new ProductsViewFragment(), "VIEWFRAG").commit();

        });

        deleteProductBtn.setOnClickListener( v-> {
            Bundle bundle = this.getArguments();

            new AlertDialog.Builder(getContext())
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation

                            boolean isDeleted = myDb.deleteProduct(bundle.getString("ID"));
                            if(isDeleted) {
                                Toast.makeText(getActivity(), "Deleted correctly ", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Delete failed", Toast.LENGTH_SHORT).show();
                            }
                            getFragmentManager().beginTransaction().replace(R.id.flFragment, new ProductsViewFragment(), "VIEWFRAG").commit();

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();





        });

        deleteCategoryBtn.setOnClickListener(v-> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            for (HashMap<String, String> category : categoryList) {
                                if(category.get("name") == spn_opt) {
                                    myDb.deleteCategory(category.get("id"));
                                    Fragment frg = null;
                                    frg = getFragmentManager().findFragmentByTag("REGISTERFRAG");
                                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(frg);
                                    ft.attach(frg);
                                    ft.commit();
                                }
                            }


                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
        return view;
    }


    /*
           HELPER FUNCTIONS
           ><><><><><><><><><><><><><><><><><
    */

    private void fillSpinner() {
        categoryList = myDb.getAllCategories();
        saveProductBtn.setEnabled(true);


        if(categoryList.size() == 0) {
            //MAKE INDEX 0 = NO CATEGORIES AVAILABLE AND DISABLE SAVE BUTTON
            //TODO CHECK IF ^^^ IS WORKING


            saveProductBtn.setEnabled(false);
            ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,cat);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpn.setAdapter(adapter);
            categorySpn.setOnItemSelectedListener(this);


            Toast.makeText(getActivity(), "No categories available!", Toast.LENGTH_SHORT).show();
            return;
        }

        cat = new String[categoryList.size()];
        int i = 0;
        for (HashMap<String, String> category : categoryList) {
            cat[i] = category.get("name");
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,cat);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpn.setAdapter(adapter);
        categorySpn.setOnItemSelectedListener(this);
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
        spn_opt = cat[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}