package com.example.product_management;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String ProductName,String ProductDescription,String supplier,String Quantity,byte[] ProductImage,String price){
        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO PRODUCTS VALUES (NULL,?,?,?,?,?,?)";

        SQLiteStatement statement =database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,ProductName);
        statement.bindString(2,ProductDescription);
        statement.bindString(3,supplier);
        statement.bindString(4,Quantity);
        statement.bindBlob(5,ProductImage);
        statement.bindString(6,price);

        statement.executeInsert();
    }

    public void updateData(String ProductName,String ProductDescription,String supplier,String Quantity,byte[] productImage,String price,int id){
        SQLiteDatabase database=getWritableDatabase();

        String sql="UPDATE PRODUCTS SET ProductName =?, ProductDescription =?, supplier =?, Quantity =?, productImage =?, price=? WHERE id=?";
        SQLiteStatement statement=database.compileStatement(sql);

        statement.bindString(1,ProductName);
        statement.bindString(2,ProductDescription);
        statement.bindString(3,supplier);
        statement.bindString(4,Quantity);
        statement.bindBlob(5,productImage);
        statement.bindString(6,price);
        statement.bindDouble(7,(double)id);

        statement.execute();
        database.close();

    }

    public void Deletedata(int id){
        SQLiteDatabase database=getWritableDatabase();

        String sql="DELETE FROM PRODUCTS WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();

    }




    public Cursor getData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
