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

/**
 * Created by 天亮就出发 on 2016/12/29.
 */

public class MyContentAsyncTask extends AsyncTask<String, Integer, News> {
    public AsyncResponseContent asyncResponseContent;

    public void setOnAsyncResponseContent(AsyncResponseContent asyncResponseContent)
    {
        this.asyncResponseContent = asyncResponseContent;
    }

    public boolean isFinished = false;

    @Override
    protected News doInBackground(String... params) {
        HttpURLConnection connection = null;
        News news = new News();
        StringBuilder content = new StringBuilder("");
        try {
            URL url = new URL(params[0]); // 声明一个URL
            connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
            connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
            connection.setConnectTimeout(80000); // 设置连接建立的超时时间
            connection.setReadTimeout(80000); // 设置网络报文收发超时时间
            InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            news.setUrl(reader.readLine());    //读取url
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            news.setContent(content.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("NewsTemp","Unfinished");
        isFinished = true;
        return news;
    }

    @Override
    protected void onPostExecute(News news) {
        Log.d("NewsTemp","enter");
        if (news != null)
        {
            asyncResponseContent.onDataReceivedSuccess(news);//将结果传给回调接口中的函数
        }
        else {
            asyncResponseContent.onDataReceivedFailed();
        }
    }
}
