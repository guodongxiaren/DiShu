package com.jelly.dishu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_statistics);
		
		Intent intent = getIntent();
		Bundle b = intent.getExtras();
		int score = b.getInt("score");
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("您的得分");
		builder.setMessage(score+"分！");
		builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				StatisticsActivity.this.finish();
			}
		});
		builder.setNegativeButton("再来一局", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
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
