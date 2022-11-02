package com.example.product_management;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity6 extends AppCompatActivity {

    Button add,mAdd,cAdd,mList;
    EditText productname, prodductdescription, supplier, quantity, price;
    ImageView productimage,coverimage;
    ActivityResultLauncher<String> mGetcontent;
    ActivityResultLauncher<String> mGetcontent1;

    final int REQUEST_CODE_GALLERY=999;


    public static SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        init();
        sqLiteHelper = new SQLiteHelper(this,"Products.sqlite",null,1);

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS PRODUCTS (id INTEGER PRIMARY KEY AUTOINCREMENT,ProductName VARCHAR,ProductDescription VARCHAR,supplier VARCHAR,Quantity VARCHAR,productImage BLOB,price VARCHAR)");




        //Get the movie image
        mGetcontent =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {

                        try {
                            InputStream inputStream = getContentResolver().openInputStream(result);

                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            productimage.setImageBitmap(bitmap);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mGetcontent.launch("image/*");

            }
        });

        //get the cover image
//
//        mGetcontent1 =registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//
//                try {
//                    InputStream inputStream = getContentResolver().openInputStream(result);
//
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    coverimage.setImageBitmap(bitmap);
//
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        });
//
//        cAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mGetcontent1.launch("image/*");
//
//            }
//        });

    //Data pass on add button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    sqLiteHelper.insertData(

                            productname.getText().toString().trim(),
                            prodductdescription.getText().toString().trim(),
                            supplier.getText().toString().trim(),
                            quantity.getText().toString().trim(),
                            productImagetoByte(productimage),
                            price.getText().toString().trim()
                    );

                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    productname.setText("");
                    prodductdescription.setText("");
                    supplier.setText("");
                    quantity.setText("");
                    productimage.setImageResource(R.mipmap.ic_launcher);
                    price.setText("");
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        //movie list
        mList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity6.this, ProductList.class);
                startActivity(intent);
            }
        });



    }

        //convert to byte
    public static byte[] productImagetoByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    private void init(){
        add=findViewById(R.id.buttonUpdate);
        mAdd=findViewById(R.id.mAdd);

        productname =findViewById(R.id.productName);
        prodductdescription =findViewById(R.id.productDescription);
        supplier =findViewById(R.id.supplier);
        quantity =findViewById(R.id.price);
        price =findViewById(R.id.Quantity);
        productimage =findViewById(R.id.productImage);
        //coverimage=findViewById(R.id.coverimage1);
        mList=findViewById(R.id.MovieList);



    }





}