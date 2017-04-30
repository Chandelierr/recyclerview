package com.chandelier.recyclerview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String TAG ="MainActivity";
    private RecyclerView recyclerView;
    private myAdapter mAdapter;
    private int lastVisibleItem ;
    private LinearLayoutManager linearLayoutManager;
    private okhttpClass okhttp;
    private int page = 1;
    private List<picRes>  more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            okhttp = new okhttpClass();
        } catch (IOException e) {
            e.printStackTrace();
        }
        more= new ArrayList<>();//用来存放要加载的 Item（在这里是图片）对象（主要是url）
        initView();//初始化布局
        setListener();//设置监听事件
        new GetData().execute("http://gank.io/api/data/福利/10/1");//先加载一些 Item
    }
    private void initView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recyclerView.clearOnScrollListeners();
    }

    private void setListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.i(TAG,"我在滚动1");
                super.onScrollStateChanged(recyclerView, newState);
                Log.i(TAG,"我在滚动2");
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=linearLayoutManager.getItemCount()) {
                    Log.i(TAG,"我在滚动3");
                    new GetData().execute("http://gank.io/api/data/福利/10/"+(++page));
                    Log.i(TAG,"我在滚动4");
                }
                Log.i(TAG,"我在滚动5");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private class GetData extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String responce = null;
            try {
                responce = okhttp.getOp(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responce;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!TextUtils.isEmpty(result)){

                JSONObject jsonObject;
                JSONArray jsonarray = new JSONArray();
                int count = 0;

                try {
                    jsonObject = new JSONObject(result);
                    jsonarray = jsonObject.getJSONArray("results");
                    count = jsonarray.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[] shujus = new String[count];


                for (int x = 0; x < count; x++){
                    try {
                        shujus[x] = jsonarray.getJSONObject(x).getString("url");
                        Log.i(TAG,"shujus["+x+"]:"+shujus[x]);
                        picRes pic = new picRes(shujus[x]);
                        more.add(pic);
                        Log.i(TAG,"将pic加入到more中："+x);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(mAdapter==null){
                    mAdapter = new myAdapter(MainActivity.this,more);
                    Log.i(TAG,"new 一个 myAdapter。");
                    recyclerView.setAdapter(mAdapter);
                    Log.i(TAG,"设置好 一个 myAdapter。");
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
