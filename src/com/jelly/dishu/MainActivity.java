package com.jelly.dishu;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ImageView im1, im2, im3, im4, im5, im6, im7, im8, im9;
	Random r = new Random();
	int score=0;
	volatile int count = 30;
	boolean continueYesNo = true;
	TextView tv ;
	Thread mainThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// MyView myView = new MyView(this);
		// setContentView(myView);
		setContentView(R.layout.activity_main);
		tv = (TextView)findViewById(R.id.textView1);
		im1 = (ImageView) findViewById(R.id.ImageView01);
		im2 = (ImageView) findViewById(R.id.ImageView02);
		im3 = (ImageView) findViewById(R.id.ImageView03);
		im4 = (ImageView) findViewById(R.id.ImageView04);
		im5 = (ImageView) findViewById(R.id.ImageView05);
		im6 = (ImageView) findViewById(R.id.ImageView06);
		im7 = (ImageView) findViewById(R.id.ImageView07);
		im8 = (ImageView) findViewById(R.id.imageView1);
		// im9 = (ImageView)findViewById(R.id.im9);

		im1.setOnClickListener(clickedView);
		im2.setOnClickListener(clickedView);
		im3.setOnClickListener(clickedView);
		im4.setOnClickListener(clickedView);
		im5.setOnClickListener(clickedView);
		im6.setOnClickListener(clickedView);
		im7.setOnClickListener(clickedView);
		im8.setOnClickListener(clickedView);
		// im9.setOnClickListener(clickedView);
		// Timer timer = new Timer(true);
		// timer.schedule(task, 1000,1000);
			mainThread = new Thread(new MyThread());
			mainThread.start();
			
	}
/**
 * listView item点击事件*/
	public OnClickListener clickedView = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (!v.getBackground().equals(new ColorDrawable(Color.argb(0, 0, 0, 0)))) {
				Log.i("tag", ""+v.getBackground().toString());
				Message msg = new Message();
				score+=50;
				msg.what=score;
				scoreHandler.sendMessage(msg);
			}
		}
	};
	
	public class MyThread implements Runnable {

		private static final String TAG = "tag";

		@Override
		public void run() {
			
			while (count != 0) {
				count--;
				try {
					Thread.sleep(1000);
					int a = r.nextInt(8)+1;
					Message msg = new Message();
					msg.what = a;
					handler.sendMessage(msg);
					Thread.sleep(250);
					Message msg2 = new Message();
					msg2.what = -a;
					handler.sendMessage(msg2);
					Log.i(TAG, count+"");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Intent intent = new Intent(MainActivity.this,StatisticsActivity.class);
			Bundle b = new Bundle();
			b.putInt("score", score);
			intent.putExtras( b);
			startActivity(intent);
			MainActivity.this.finish();
		}
	}

	@SuppressLint("HandlerLeak")
	Handler scoreHandler = new Handler(){

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			tv.setText(msg.what+"");
		}
		
	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				im1.setBackgroundResource(R.drawable.dishu);
				break;
			case 2:
				im2.setBackgroundResource(R.drawable.dishu);
				break;
			case 3:
				im3.setBackgroundResource(R.drawable.dishu);
				break;
			case 4:
				im4.setBackgroundResource(R.drawable.dishu);
				break;
			case 5:
				im5.setBackgroundResource(R.drawable.dishu);
				break;
			case 6:
				im6.setBackgroundResource(R.drawable.dishu);
				break;
			case 7:
				im7.setBackgroundResource(R.drawable.dishu);
				break;
			case 8:
				im8.setBackgroundResource(R.drawable.dishu);
				break;
			case -1:
				im1.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -2:
				im2.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -3:
				im3.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -4:
				im4.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -5:
				im5.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -6:
				im6.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -7:
				im7.setBackgroundColor(Color.TRANSPARENT);
				break;
			case -8:
				im8.setBackgroundColor(Color.TRANSPARENT);
				break;
//			case 9:
//				im9.setBackgroundResource(R.drawable.dishu);
//				break;
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
