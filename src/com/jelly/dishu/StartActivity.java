package com.jelly.dishu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class StartActivity extends Activity {
	ImageButton manu;
	ImageButton start;
	ImageButton exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		manu = (ImageButton) findViewById(R.id.manual);
		start = (ImageButton) findViewById(R.id.start);
		exit = (ImageButton) findViewById(R.id.exit);
		manu.setOnClickListener(myClick);
		start.setOnClickListener(myClick);
		exit.setOnClickListener(myClick);
	}

	/**
	 * 点击图标，打开关于界面的事件对象
	 */
	OnClickListener myClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.start:
				intent.setClass(StartActivity.this, MainActivity.class);
				startActivity(intent);
				break;
			case R.id.manual:
				intent.setClass(StartActivity.this, AboutActivity.class);
				startActivity(intent);
				break;
			case R.id.exit:
				break;
			}
			StartActivity.this.finish();

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

}
