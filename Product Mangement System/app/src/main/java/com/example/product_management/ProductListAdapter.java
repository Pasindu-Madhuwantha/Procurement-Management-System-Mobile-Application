package com.example.product_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Products> productlist;

    public ProductListAdapter(Context context, int layout, ArrayList<Products> productlist) {
        this.context = context;
        this.layout = layout;
        this.productlist = productlist;
    }

    @Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public Object getItem(int position) {
        return productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView movieimage;
        TextView mName;
        TextView mName2;
        TextView mName3;


    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row =view;
        ViewHolder holder= new ViewHolder();

        if(row == null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =inflater.inflate(layout,null);

            holder.mName=(TextView) row.findViewById(R.id.mName);
            holder.mName2=(TextView) row.findViewById(R.id.mName2);
            holder.mName3=(TextView) row.findViewById(R.id.mName3);
            holder.movieimage=(ImageView) row.findViewById(R.id.imgMovies);
            row.setTag(holder);
        }

        else{
            holder=(ViewHolder) row.getTag();

        }

        Products movie= productlist.get(position);

        holder.mName.setText(movie.getProductName());
        holder.mName2.setText(movie.getSupplier());
        holder.mName3.setText(movie.getQuantity());

        byte[] movieimage=movie.getProductImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(movieimage,0,movieimage.length);
        holder.movieimage.setImageBitmap(bitmap);




        return row;
    }
}
