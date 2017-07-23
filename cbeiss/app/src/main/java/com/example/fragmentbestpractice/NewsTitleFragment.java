package com.example.fragmentbestpractice;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;

    private List<News> newsList = null;

    private News news = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        RecyclerView newsTitleRecyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);

        String titleUrlString = Constant.URL_First;
        try {
            final MyAsyncTask titleAsyncTask = new MyAsyncTask();
            titleAsyncTask.execute(titleUrlString);
            Log.d("MyAsyncTask","ssucceeded");
            titleAsyncTask.setOnAsyncResponse(new AsyncResponse() {
                //通过自定义的接口回调获取AsyncTask中onPostExecute返回的结果变量
                @Override
                public void onDataReceivedSuccess(List<News> newsList1) {
                    newsList = newsList1;//如此，我们便把onPostExecute中的变量赋给了成员变量environmentList
                    Log.d("MyAsyncTask","Succeedednimabia");
                }
                @Override
                public void onDataReceivedFailed() {
                    Log.d("MyAsyncTask","Failed");
                }
            });
            while(titleAsyncTask.isFinished == false);
            Log.d("NewsTemp","gone");
        } catch (Exception e) {
            e.printStackTrace();
        }
        newsTitleRecyclerView.setAdapter(new NewsAdapter());
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true; // 可以找到news_content_layout布局时，为双页模式
        } else {
            isTwoPane = false; // 找不到news_content_layout布局时，为单页模式
        }
    }


    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {          //如果在类外定义ViewHolder,可能会和RecyclerView.ViewHolder冲突

            TextView newsTitleText;

            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }

        public NewsAdapter() {
            Log.d("NewsTemp","gone2");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = holder.getAdapterPosition();
                    news = newsList.get(number);
                    String contentUrlString = Constant.URL_Content + number;
                    try {
                        final MyContentAsyncTask contentAsyncTask = new MyContentAsyncTask();
                        contentAsyncTask.execute(contentUrlString);
                        contentAsyncTask.setOnAsyncResponseContent(new AsyncResponseContent() {
                            //通过自定义的接口回调获取AsyncTask中onPostExecute返回结果的变量
                            @Override
                            public void onDataReceivedSuccess(News newsTemp) {
                                Log.d("NewsTemp",newsTemp.getContent());
                                news.setContent(newsTemp.getContent());
                                news.setUrl(newsTemp.getUrl());
                                Log.d("MyContentAsyncTask","Succeeded");
                            }
                            @Override
                            public void onDataReceivedFailed() {
                                Log.d("MyContentAsyncTask","Failed");
                            }
                        });
                        while(contentAsyncTask.isFinished == false);
                        Log.d("NewsTemp","gone");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("MyContentAsyncTask","ERROR!");
                    }

                    if (isTwoPane) {
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent(), news.getUrl());
                    } else {
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent(),news.getUrl());
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 1000);//3秒后执行TimeTask的run方法
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news = newsList.get(position);
            String titletext = news.getTitle() + "\n" + news.getDatetime();
            holder.newsTitleText.setText(titletext);
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

    }
}
