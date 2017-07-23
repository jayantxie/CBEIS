package com.example.fragmentbestpractice;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class HelloActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello);
		TextView mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
		TextView mBtnForum = (TextView) findViewById(R.id.main_btn_forum);
		mBtnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						Intent intent = new Intent(HelloActivity.this, MainActivity.class);
						startActivity(intent);
					}
				};
				Timer timer = new Timer();
				timer.schedule(task, 0);//3秒后执行TimeTask的run方法
			}
		});
		mBtnForum.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						Intent intent = new Intent(HelloActivity.this, ForumActivity.class);
						intent.putExtra("url","http://ssr-1008.s1.natapp.cc");
						startActivity(intent);
					}
				};
				Timer timer = new Timer();
				timer.schedule(task, 0);//3秒后执行TimeTask的run方法
			}
		});
	}

}