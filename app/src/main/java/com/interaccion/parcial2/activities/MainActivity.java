package com.interaccion.parcial2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.interaccion.parcial2.R;
import com.interaccion.parcial2.database.DatabaseHelper;
import com.interaccion.parcial2.fragments.ProductHandlerFragment;
import com.interaccion.parcial2.fragments.ProductsViewFragment;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper myDb;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        Log.wtf("MAIN ACT DATABASE HELPER", myDb.getDatabaseName());



        getSupportFragmentManager().beginTransaction().add(R.id.flFragment, new ProductsViewFragment(), "VIEWFRAG").commit();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new ProductHandlerFragment(), "REGISTERFRAG").commit();
        });


    }
}