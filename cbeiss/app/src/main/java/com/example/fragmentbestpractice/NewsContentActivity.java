package com.example.fragmentbestpractice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class NewsContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String newsTitle, String newsContent,String newsurl) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        intent.putExtra("news_url",newsurl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        String newsTitle = getIntent().getStringExtra("news_title"); // 获取传入的新闻标题
        String newsContent = getIntent().getStringExtra("news_content"); // 获取传入的新闻内容
        String newsUrl = getIntent().getStringExtra("news_url");//获取传入的新闻链接
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent, newsUrl); // 刷新NewsContentFragment界面
    }

}
