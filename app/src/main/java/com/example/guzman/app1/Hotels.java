package com.example.guzman.app1;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class Hotels extends Fragment {
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    Database database;

    public Hotels() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotels, container, false);
        
        database = new Database(getContext());
        
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        listView = (ListView) view.findViewById(R.id.list);
        
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();

        return view;
    }

    private void load() {

        Background background = new Background(getContext(), listView);
        background.execute();
    }

    @Override
    public String toString() {
        return ("Hotels");
    }

    private class Background extends AsyncTask<String, String, String> {

        Context context;

        ListView listView;

        ProgressDialog progressDialog;

        HttpURLConnection httpURLConnection;

        BufferedReader bufferedReader;

        StringBuilder stringBuilder;

        public Background(Context context, ListView listView) {
            this.context = context;
            this.listView = listView;
            }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            swipeRefreshLayout.setRefreshing(true);

            progressDialog = new ProgressDialog(context);

            progressDialog.setMessage("Downloading...");

            progressDialog.show();

        }



        @Override
        protected String doInBackground(String... params) {
            return DownloadHotels();
        }
        private String DownloadHotels() {
            stringBuilder = new StringBuilder();
            String line;
            httpURLConnection = new GetConnected().GetConnected();
            try {

                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setConnectTimeout(120000);
                httpURLConnection.connect();

                bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(httpURLConnection.getInputStream())));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                httpURLConnection.disconnect();
            } catch (ProtocolException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
            
            return stringBuilder.toString();
        }
        protected class GetConnected {
            URL url;

            HttpURLConnection httpU;

            String sign_url = new Ip(context).Hotels();

            public HttpURLConnection GetConnected (){

                if (sign_url != null){
                    try {
                        url = new URL(sign_url);

                        httpU = (HttpURLConnection) url.openConnection();

                    } catch (MalformedURLException e) {
                        return null;
                    } catch (IOException e) {
                        return null;

                    }
                    return httpU;
                }

                return null;
            }
        }
        
        public void execute() {
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            swipeRefreshLayout.setRefreshing(false);
            
            if (result !=null){
                ParserData parserData = new ParserData(context, listView, result);
                
                parserData.execute(result);
                
            }else {
                TastyToast.makeText(getContext(), "Error Encountered Can't connect to the Server", TastyToast.LENGTH_LONG,TastyToast.ERROR);
            }
        }

        private class ParserData extends AsyncTask<String, Void, Boolean>{

            Context context;

            ListView listView;

            ProgressDialog progressDialog;

            String jsondata;

            ArrayList<Spacecraft> arrayList =new ArrayList<>();

            public ParserData(Context context, ListView listView, String jsondata) {
            this.context = context;
            this.listView = listView;
            this.jsondata = jsondata;

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Boolean doInBackground(String... params) {
                jsondata = params[0];
                return ParseJsonData();
            }

            @Override
            protected void onPostExecute(Boolean bool) {
                super.onPostExecute(bool);

                if (bool){
                    AdapterClass adapterClass = new AdapterClass(arrayList, context, listView );
                    listView.setAdapter(adapterClass);
                }else{
                    TastyToast.makeText(getContext(), "Error rncountered During Processing", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }
            }


            private Boolean ParseJsonData(){
                boolean val = false;

                try {
                    JSONArray jsonArray = new JSONArray(jsondata);

                    JSONObject jsonObject = null;

                    int i = 0;

                    Spacecraft spacecraft;

                    while (i < jsonArray.length()){

                        jsonObject = jsonArray.getJSONObject(i);

                        spacecraft = new Spacecraft();


                    }
                } catch (JSONException e) {

                }
                return val;
            }

            private class Spacecraft {
            }

            private class AdapterClass extends BaseAdapter{

                ArrayList<Spacecraft> spacecraftArrayList;
                Context context;
                ListView listView;

                public AdapterClass(ArrayList<Spacecraft> arrayList, Context context, ListView listView) {
                this.spacecraftArrayList = spacecraftArrayList;
                this.context = context;
                this.listView = listView;

                }

                @Override
                public int getCount() {
                    return spacecraftArrayList.size();
                }

                @Override
                public Object getItem(int position) {
                    return spacecraftArrayList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    ViewHolder viewHolder = null;

                    if (viewHolder == null){
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel, null);
                        viewHolder = new ViewHolder(convertView);
                        convertView.setTag(viewHolder);

                    }else {
                        viewHolder = (ViewHolder) convertView.getTag();
                    }
                   return convertView;
                }

                public class ViewHolder {


                    View view;


                    public ViewHolder(View view) {

                        this.view = view;

//                        starting_time = (TextView) view.findViewById(R.id.txtName);
//
//                        end_time = (TextView) view.findViewById(R.id.txtId);
//
//                        slot = (TextView) view.findViewById(R.id.aa);
//
//                        status = (TextView) view.findViewById(R.id.bb);
//
//                        date = (TextView) view.findViewById(R.id.cc);
//
//                        imageView = (ImageView) view.findViewById(R.id.ViewBookings);

                    }
                }
            }
        }
    }

}
