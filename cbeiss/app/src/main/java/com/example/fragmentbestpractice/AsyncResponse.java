package com.example.fragmentbestpractice;

import java.util.List;

/**
 * Created by 天亮就出发 on 2016/12/29.
 */

public interface AsyncResponse {
    void onDataReceivedSuccess(List<News> newsList);
    void onDataReceivedFailed();
}
