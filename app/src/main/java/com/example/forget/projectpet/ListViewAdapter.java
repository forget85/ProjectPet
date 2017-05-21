package com.example.forget.projectpet;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import junit.framework.Assert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ListViewHolder extends RecyclerView.ViewHolder{
    private ImageView    productImageView;
    private TextView     saleTextView;
    private TextView     freeShippingTextView;
    private ImageView    likeImageView;
    private TextView     productTextView;
    private TextView     shoppingMallTextView;
    private TextView     priceTextView;

    ListViewHolder(View itemView){
        super(itemView);

        productImageView = (ImageView) itemView.findViewById(R.id.product_image_view);
        saleTextView = (TextView) itemView.findViewById(R.id.sale_text_view);
        freeShippingTextView = (TextView) itemView.findViewById(R.id.free_shipping_text_view);
        likeImageView = (ImageView) itemView.findViewById(R.id.like_image_button_view);
        productTextView = (TextView) itemView.findViewById(R.id.product_name_text_view);
        //shoppingMallTextView = (TextView) itemView.findViewById(R.id.shopping_mall_name_text_view);
        priceTextView = (TextView) itemView.findViewById(R.id.price_text_view);
    }

    void bind(Context context, ListItem listItem){
        Glide.with(context).load(listItem.getImageUrl()).into(productImageView);
        priceTextView.setText(listItem.getCost());
        productTextView.setText(listItem.getProductName());
        //shoppingMallTextView.setText(listItem.getShoppingMallName());
        updateOnClickListener(context, listItem);
    }

    void updateOnClickListener(final Context context, final ListItem listItem){
        View.OnClickListener moveItemPageListner = new View.OnClickListener(){
            public void onClick(View view){
                ((MainActivity)context).openItemWebView(listItem.getProductUrl());
            }
        };
        productImageView.setOnClickListener(moveItemPageListner);
        productTextView.setOnClickListener(moveItemPageListner);
        likeImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ((MainActivity)context).onChangeLikeItem(true, listItem);
            }
        });
    }
}

public class ListViewAdapter extends RecyclerView.Adapter<ListViewHolder> implements Filterable{
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_SEARCH = 1;

    int listViewType = TYPE_DEFAULT;

    private class ListFilter extends Filter{
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            HashMap<String, ListItem> filterItemLists = new HashMap<>();
            if(constraint == null || constraint.length() == 0){
                if(listViewType == TYPE_SEARCH){
                    filterResults.values = filterItemLists;
                    filterResults.count = 0;
                }else {
                    filterResults.values = listItems;
                    filterResults.count = listItems.size();
                }
            }else{
                for(Map.Entry<String, ListItem> entry : listItems.entrySet()){
                    ListItem listItem = (ListItem)entry.getValue();
                    if(listItem.getProductName().toUpperCase().contains(constraint.toString().toUpperCase())){
                        filterItemLists.put(entry.getKey(), listItem);
                    }
                }

                filterResults.values = filterItemLists;
                filterResults.count = filterItemLists.size();
            }

            return filterResults;
        }

        protected void publishResults(CharSequence constraint, FilterResults results) {
            listViewItems = (HashMap<String, ListItem>) results.values;
            updateViewItemKeys();

            if(0 < results.count){
                notifyDataSetChanged();
            }else if(listViewType == TYPE_SEARCH){
                notifyDataSetChanged();
            }
        }
    }

    HashMap<String, ListItem> listItems = new HashMap<>();
    HashMap<String, ListItem> listViewItems = new HashMap<>();
    ArrayList<String> viewItemKeys = new ArrayList<>();

    Context context;

    Filter listFilter;

    ListViewAdapter(Context _context) {
        context = _context;
    }

    public void setListViewType(int _listViewType){
        listViewType = _listViewType;
    }

    public void updateData(HashMap<String, ListItem> _listItems) {
        listItems = _listItems;
        listViewItems = listItems;
        updateViewItemKeys();
    }

    public void updateViewItemKeys(){
        viewItemKeys.clear();
        if(listViewItems != null) {
            for (String Key : listViewItems.keySet()) {
                viewItemKeys.add(Key);
            }
        }
    }

    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }

    public void onBindViewHolder(ListViewHolder holder, int position) {
        if(viewItemKeys.isEmpty())
            return;

        holder.bind(context, listViewItems.get(viewItemKeys.get(position)));
    }

    public int getItemCount() {
        return listViewItems.size();
    }

    public Filter getFilter() {
        if(listFilter == null)
            listFilter = new ListFilter();

        return listFilter;
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
