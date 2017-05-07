package com.example.forget.projectpet;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListFragment extends BaseFragment {
    private ListFragmentPresenter listFragmentPresenter = null;

    public View initView(LayoutInflater inflater, ViewGroup container){
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);

        initRecyclerView(recyclerView);

        listFragmentPresenter = new ListFragmentPresenter(context, recyclerView);
        getData();

        return view;
    }

    public void initRecyclerView(RecyclerView recyclerView){
        recyclerView.setHasFixedSize(true);

        final int columnCount = 2;
        final int dividerWidth = 20;
        final int dividerHeight = 20;
        recyclerView.addItemDecoration(new ListViewItemDecoration(columnCount, dividerWidth, dividerHeight));
        recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
    }

    public void setListitems(ArrayList<ListItem> _listItems){
        listFragmentPresenter.setListItems(_listItems);
        listFragmentPresenter.updateList();
    }

    public boolean isFinishLoadListItems(){
        return ((MainActivity)context).isFinishLoad();
    }

    public void getListItemsForMainActivity(){
        setListitems(((MainActivity)context).getListItems());
    }

    private void getData() {
        try {
            ReadListItemTask readListItemTask = new ReadListItemTask();
            readListItemTask.execute();
        }catch (Exception exception){
            System.out.println(exception.toString());
        }
    }

    private class ReadListItemTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            getListItemsForMainActivity();
            return "Success";
        }

        protected void onPostExecute(String string) {
            super.onPostExecute(string);
        }

        String getData(){
            boolean bStop = false;
            while(!bStop){
                if(isFinishLoadListItems()){
                    bStop = true;
                }
            }

            return "Success";
        }
    }
}
