package com.example.fragmentbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class NewsContentFragment extends Fragment {

    private View view;
    private ScrollView sv;        //滑动句柄
    private TextView newsTitleText;
    private TextView newsContentText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag, container, false);
        return view;
    }

    public void refresh(String newsTitle, String newsContent, String newsUrl) {
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        final String thisNewsUrl = newsUrl;
        newsTitleText = (TextView) view.findViewById (R.id.news_title);
        newsContentText = (TextView) view.findViewById(R.id.news_content);
        sv = (ScrollView) view.findViewById(R.id.ScrollView01);  //得到翻页句柄
        newsTitleText.setText(newsTitle); // 刷新新闻的标题
        newsContentText.setText(newsContent); // 刷新新闻的内容
        sv.scrollTo(0,newsContentText.getMeasuredHeight()); //跳至数据最后一页
        newsTitleText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), ForumActivity.class);
                        intent.putExtra("url",thisNewsUrl);
                        startActivity(intent);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 0);//3秒后执行TimeTask的run方法
            }
        });
    }

}
