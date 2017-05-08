package com.example.forget.projectpet;

import android.os.AsyncTask;

public class ListFragment extends BaseListFragment {
    public void processInit() {
        super.processInit();
        getData();
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
            getData();
            return "Success";
        }

        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            getListItemsForMainActivity();
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
