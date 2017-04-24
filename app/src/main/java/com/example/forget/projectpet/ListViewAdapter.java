package com.example.forget.projectpet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class ListViewHolder extends RecyclerView.ViewHolder{
    public ImageView   productImageView;
    public ImageView   saleImageView;
    public ImageView   freeShippingImageView;
    public ImageView   likeImageView;
    public TextView    productTextView;
    public TextView    shoppingMallTextView;
    public TextView    priceTextView;

    ListViewHolder(View itemView){
        super(itemView);

        productImageView = (ImageView) itemView.findViewById(R.id.product_image_view);
        saleImageView = (ImageView) itemView.findViewById(R.id.sale_image_view);
        freeShippingImageView = (ImageView) itemView.findViewById(R.id.free_shipping_image_view);
        likeImageView = (ImageView) itemView.findViewById(R.id.like_image_view);
        productTextView = (TextView) itemView.findViewById(R.id.product_name_text_view);
        shoppingMallTextView = (TextView) itemView.findViewById(R.id.shopping_mall_name_text_view);
        priceTextView = (TextView) itemView.findViewById(R.id.price_text_view);
    }
}

public class ListViewAdapter extends RecyclerView.Adapter<ListViewHolder>{
    ArrayList<ListItem> listItems = new ArrayList<>();
    Context context;

    ListViewAdapter(Context _context, ArrayList<ListItem> _listItems){
        context = _context;
        listItems = _listItems;
    }

    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder holder = new ListViewHolder(v);
        return holder;
    }

    public void onBindViewHolder(ListViewHolder holder, int position) {
        //holder.productImageView =
        //holder.saleImageView =
        //holder.freeShippingImageView =
        //holder.likeImageView =
        holder.productTextView.setText(listItems.get(position).getProductName());
        holder.shoppingMallTextView.setText(listItems.get(position).getShoppingMallName());
    }

    public int getItemCount() {
        return listItems.size();
    }
}
