package com.interaccion.parcial2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.interaccion.parcial2.R;
import com.interaccion.parcial2.fragments.ProductHandlerFragment;
import com.interaccion.parcial2.fragments.ProductsViewFragment;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.flFragment, new ProductsViewFragment(), "CONCEPTFRAG").commit();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new ProductHandlerFragment(), "REGISTERFRAG").commit();
        });


    }

}