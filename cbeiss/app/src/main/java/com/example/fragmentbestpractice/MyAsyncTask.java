package com.example.fragmentbestpractice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 天亮就出发 on 2016/12/29.
 */

public class MyAsyncTask extends AsyncTask<String, Void, List<News>> {

    public AsyncResponse asyncResponse;

    public void setOnAsyncResponse(AsyncResponse asyncResponse)
    {
        this.asyncResponse = asyncResponse;
    }

    public boolean isFinished = false;

    @Override
    protected List<News> doInBackground(String... params) {
        HttpURLConnection connection = null;
        List<News> newsList = new ArrayList<>();
        int number = 0;
        try {
            URL url = new URL(params[0]); // 声明一个URL
            connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
            connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
            connection.setConnectTimeout(80000); // 设置连接建立的超时时间
            connection.setReadTimeout(80000); // 设置网络报文收发超时时间
            Log.d("MyAsyncTask","reachededfirst");
            InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                News news = new News();
                news.setTitle(line);
                news.setDatetime(reader.readLine());
                newsList.add(news);
                Log.d("MyAsyncTask","reacheded"+(++number));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("MyAsyncTask","reachededresult");
        isFinished = true;
        return newsList;
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        Log.d("NewsTemp","enter");
        if (newsList != null)
        {
            Log.d("MyAsyncTask","Succeededok");
            asyncResponse.onDataReceivedSuccess(newsList);//将结果传给回调接口中的函数
        }
        else {
            Log.d("MyAsyncTask","Failedok");
            asyncResponse.onDataReceivedFailed();
        }
    }
}
