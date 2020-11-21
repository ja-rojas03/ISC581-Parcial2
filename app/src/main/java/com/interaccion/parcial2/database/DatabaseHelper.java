package com.interaccion.parcial2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "parcial.db";

    private static final String PRODUCT_TABLE_NAME = "product_data";
    private static final String PRODUCT_COL_1 = "ID";
    private static final String PRODUCT_COL_2 = "NAME";
    private static final String PRODUCT_COL_3 = "PRICE";
    private static final String PRODUCT_COL_4 = "CATEGORY";

    private static final String CATEGORY_TABLE_NAME = "category_data";
    private static final String CATEGORY_COL_1 = "ID";
    private static final String CATEGORY_COL_2 = "NAME";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PRODUCT_TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PRICE TEXT,CATEGORY TEXT) "
        );

        Log.wtf("DATABASE HELPER", String.valueOf(db.isOpen()) + db.getPath());

        db.execSQL("create table " + CATEGORY_TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertProductData(String name, String price, String category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COL_2, name);
        contentValues.put(PRODUCT_COL_3, price);
        contentValues.put(PRODUCT_COL_4, category);

        long result = db.insert(PRODUCT_TABLE_NAME,null, contentValues);
        return result == -1 ? false : true;
    }

    public boolean insertCategory(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COL_2, name);

        long result = db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        return result == -1 ? false : true;
    }

    public ArrayList<HashMap<String, String>> getAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        String query = "SELECT * FROM "+ PRODUCT_TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            HashMap<String,String> product = new HashMap<>();
            product.put("id",cursor.getString(0));
            product.put("name",cursor.getString(1));
            product.put("price",cursor.getString(2));
            product.put("category",cursor.getString(3));
            productList.add(product);
        }

        return  productList;
    }

    public ArrayList<HashMap<String, String>> getAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> categoryList = new ArrayList<>();

        String query = "select * from "+ CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            HashMap<String,String> category = new HashMap<>();
            category.put("id",cursor.getString(0));
            category.put("name",cursor.getString(1));
            categoryList.add(category);
        }

        return  categoryList;
    }

    public boolean updateProduct(String id, String name, String price, String category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_COL_1, id);
        contentValues.put(PRODUCT_COL_2, name);
        contentValues.put(PRODUCT_COL_3, price);
        contentValues.put(PRODUCT_COL_4, category);

        long res = db.update(PRODUCT_TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        return res == -1 ? false: true;
    }

    public boolean deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.delete(PRODUCT_TABLE_NAME, "ID = ?", new String[] { id });
        return res == -1 ? false: true;

    }

    public boolean deleteCategory(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.delete(CATEGORY_TABLE_NAME, "ID = ?", new String[] { id });
        return res == -1 ? false: true;

    }

}
