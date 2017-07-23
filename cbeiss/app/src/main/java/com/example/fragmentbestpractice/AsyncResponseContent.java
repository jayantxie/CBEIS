package com.example.fragmentbestpractice;


/**
 * Created by 天亮就出发 on 2016/12/29.
 */

public interface AsyncResponseContent {
    void onDataReceivedSuccess(News news);
    void onDataReceivedFailed();
}
