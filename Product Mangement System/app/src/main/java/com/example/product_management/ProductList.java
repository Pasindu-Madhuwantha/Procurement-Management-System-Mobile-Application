package com.example.product_management;

import static com.example.product_management.MainActivity6.productImagetoByte;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Products> list;
    ProductListAdapter adapter=null;

    ActivityResultLauncher<String> mGetcontent3;
    ActivityResultLauncher<String> mGetcontent4;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);


        //Get the movie image for update window
        mGetcontent3 =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                try {
                    InputStream inputStream = getContentResolver().openInputStream(result);

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ProductImage1.setImageBitmap(bitmap);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        });

        //get the cover image for update window

//        mGetcontent4 =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//
//                try {
//                    InputStream inputStream = getContentResolver().openInputStream(result);
//
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    coverimage1.setImageBitmap(bitmap);
//
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });


        gridView = (GridView) findViewById(R.id.gridView);
        list =new ArrayList<>();
        adapter= new ProductListAdapter(this,R.layout.moiveview,list);
        gridView.setAdapter(adapter);

        // get data from sqlite

        Cursor cursor =MainActivity6.sqLiteHelper.getData("SELECT id,ProductName,supplier,Quantity,ProductImage FROM PRODUCTS");
        list.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String ProductName=cursor.getString(1);
            String SupplierName=cursor.getString(2);
            String qty =cursor.getString(3);
            byte[] ProductImage=cursor.getBlob(4);

            list.add(new Products(id,ProductName,SupplierName,qty,ProductImage));

        }

        adapter.notifyDataSetChanged();

        //delete or update toast
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ProductList.this, "Long Click On Item To Update or Delete", Toast.LENGTH_SHORT).show();
            }
        });


        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position , long l) {

                CharSequence[] items={"Update","Delete"};
                AlertDialog.Builder dialog=new AlertDialog.Builder(ProductList.this);

                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {


                        if(item==0){
                            //update
                            Cursor c= MainActivity6.sqLiteHelper.getData("SELECT id FROM PRODUCTS");
                            ArrayList<Integer> arrID= new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));

                            }
                            //dialog update
                            showDialogUpdate(ProductList.this, arrID.get(position));


                        }

                        else{
                            //delete
                            Cursor c= MainActivity6.sqLiteHelper.getData("SELECT id FROM PRODUCTS");
                            ArrayList<Integer> arrID= new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));

                            }

                            showDialogDelete(arrID.get(position));

                        }
                    }
                });
                dialog.show();
                return true;
            }
        });


    }

    ImageView ProductImage1;
    ImageView coverimage1;
    String moviename1;


    private  void showDialogUpdate(Activity activity,int position){
        Dialog dialog =new Dialog(activity);
        dialog.setContentView(R.layout.update_movies_activity);
        dialog.setTitle("Update");



         EditText ProductName=(EditText) dialog.findViewById(R.id.productName);
        final EditText prodductdescription=(EditText) dialog.findViewById(R.id.productDescription);
        final EditText supplier=(EditText) dialog.findViewById(R.id.supplier);
         EditText price =(EditText) dialog.findViewById(R.id.price);
        ProductImage1 =(ImageView) dialog.findViewById(R.id.productImage);
        final EditText quantity=(EditText) dialog.findViewById(R.id.Quantity);
        Button btnUpdate=(Button) dialog.findViewById(R.id.buttonUpdate);

        // get data of row  clicked from sqlite

        Cursor cursor =MainActivity6.sqLiteHelper.getData("SELECT* FROM PRODUCTS WHERE id="+position);
        list.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String productName=cursor.getString(1);
            ProductName.setText(productName); //set moivename to dialog update

            String productDescription=cursor.getString(2);
            prodductdescription.setText(productDescription);

            String supplier1=cursor.getString(3);
            supplier.setText(supplier1);

            String quantity1=cursor.getString(4);
            quantity.setText(quantity1);

            byte [] productimage1=cursor.getBlob(5);
            ProductImage1.setImageBitmap(BitmapFactory.decodeByteArray(productimage1,0,productimage1.length));


            String price1=cursor.getString(6);
            price.setText(price1);


             Products moviesUpdate= new Products(id,productName,supplier1,price1,productimage1);

             moviesUpdate.MoivesUpdate(id,productName,productDescription,quantity1,supplier1,price1,productimage1);

            list.add(moviesUpdate);

        }






        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.8);
        dialog.getWindow().setLayout(width, height);
        dialog.show();




        ProductImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetcontent3.launch("image/*");

            }
        });




//        coverimage1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mGetcontent4.launch("image/*");
//
//            }
//        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    MainActivity6.sqLiteHelper.updateData(
                            ProductName.getText().toString().trim(),
                            prodductdescription.getText().toString().trim(),
                            supplier.getText().toString().trim(),
                            quantity.getText().toString().trim(),
                            productImagetoByte(ProductImage1),

                            price.getText().toString().trim(),
                            position


                    );
                    dialog.dismiss();
                    Toast.makeText(activity, "Update Successfully!!!", Toast.LENGTH_SHORT).show();

                }

                catch(Exception error){
                    Log.e("Update Error",error.getMessage());
                }
                updateMovieList();

            }
        });

    }


    private void showDialogDelete(final int idMovies){
        AlertDialog.Builder dialogDelete =new AlertDialog.Builder(ProductList.this);

        dialogDelete.setTitle("Warning!");
        dialogDelete.setMessage("Are You Sure want to delete this?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    MainActivity6.sqLiteHelper.Deletedata(idMovies);
                    Toast.makeText(ProductList.this, "Deleted Successfully!!!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Log.e("error",e.getMessage());
                }
                updateMovieList();


            }
        });
        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();

            }
        });
        dialogDelete.show();
    }




    private  void updateMovieList(){
        // get data from sqlite

        Cursor cursor =MainActivity6.sqLiteHelper.getData("SELECT id,ProductName,supplier,Quantity,ProductImage FROM PRODUCTS");
        list.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String ProductName=cursor.getString(1);
            String SupplierName=cursor.getString(2);
            String qty =cursor.getString(3);
            byte[] ProductImage=cursor.getBlob(4);

            list.add(new Products(id,ProductName,SupplierName,qty,ProductImage));

        }

        adapter.notifyDataSetChanged();


    }


}
