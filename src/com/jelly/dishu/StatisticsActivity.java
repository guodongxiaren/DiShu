package com.jelly.dishu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class StatisticsActivity extends Activity {
	boolean excel = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics);

		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		int score = b.getInt("score");

		SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
		int s = sp.getInt("MAX_SCORE", 0);
		Log.i("max", s+"");
		if (s == 0 || s < score) {
			Editor editor = sp.edit();
			editor.putInt("MAX_SCORE", score);
			boolean b1 = editor.commit();
			Log.i("bbb", b1+"");
			excel = true;
		}
		
		Log.i("after", sp.getInt("MAX_SCORE", 0)+"");
		AlertDialog.Builder builder = new Builder(this);
		if (excel)
			builder.setTitle("破记录");
		else
			builder.setTitle("历史最高分" + s);
		builder.setMessage("您的得分 " + score + "！");
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				StatisticsActivity.this.finish();
			}
		});
		builder.setNegativeButton("再来一局",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(StatisticsActivity.this,
								MainActivity.class);
						startActivity(intent);
						StatisticsActivity.this.finish();
					}
				});
		builder.create().show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

}
