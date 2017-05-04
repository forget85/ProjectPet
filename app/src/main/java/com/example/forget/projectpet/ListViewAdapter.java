package com.example.forget.projectpet;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import junit.framework.Assert;

import java.util.ArrayList;

class ListViewHolder extends RecyclerView.ViewHolder{
    public ImageView    productImageView;
    public TextView     saleTextView;
    public TextView     freeShippingTextView;
    public ImageView    likeImageView;
    public TextView     productTextView;
    public TextView     shoppingMallTextView;
    public TextView     priceTextView;

    ListViewHolder(View itemView){
        super(itemView);

        productImageView = (ImageView) itemView.findViewById(R.id.product_image_view);
        saleTextView = (TextView) itemView.findViewById(R.id.sale_text_view);
        freeShippingTextView = (TextView) itemView.findViewById(R.id.free_shipping_text_view);
        likeImageView = (ImageView) itemView.findViewById(R.id.like_image_view);
        productTextView = (TextView) itemView.findViewById(R.id.product_name_text_view);
        shoppingMallTextView = (TextView) itemView.findViewById(R.id.shopping_mall_name_text_view);
        priceTextView = (TextView) itemView.findViewById(R.id.price_text_view);
    }

    void bind(Context context, ListItem listItem){
        //productImageView =
        //saleImageView =
        //freeShippingImageView =
        //likeImageView =
        Glide.with(context).load(listItem.getImageUrl()).into(productImageView);
        priceTextView.setText(listItem.getCost());
        productTextView.setText(listItem.getProductName());
        shoppingMallTextView.setText(listItem.getShoppingMallName());
        updateOnClickListener(context, listItem);
    }

    void updateOnClickListener(final Context context, final ListItem listItem){
        View.OnClickListener moveItemPageListner = new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(context, listItem.getProductName() + " click", Toast.LENGTH_SHORT).show();
            }
        };
        productImageView.setOnClickListener(moveItemPageListner);
        productTextView.setOnClickListener(moveItemPageListner);
    }
}

public class ListViewAdapter extends RecyclerView.Adapter<ListViewHolder> {
    ArrayList<ListItem> listItems = new ArrayList<>();
    Context context;

    ListViewAdapter(Context _context) {
        context = _context;
    }

    public void updateData(ArrayList<ListItem> _listItems) {
        listItems = _listItems;
    }

    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.bind(context, listItems.get(position));
    }

    public int getItemCount() {
        return listItems.size();
    }
}

class ListViewItemDecoration extends RecyclerView.ItemDecoration{
    private int dividerWidth;
    private int dividerHeight;
    private int columnCount;
    public ListViewItemDecoration(int _columnCount, int _dividerWidth, int _dividerHeight){
        dividerWidth = _dividerWidth;
        dividerHeight = _dividerHeight;
        columnCount = _columnCount;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();

        outRect.bottom = dividerHeight;
        outRect.left = dividerWidth;

        Assert.assertTrue(0 < columnCount);
        if(itemPosition % columnCount - 1 == 0) {
            outRect.right = dividerWidth;
        }
    }
}
