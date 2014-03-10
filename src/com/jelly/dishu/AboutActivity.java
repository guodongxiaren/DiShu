package com.jelly.dishu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jelly.dishu.fragment.AboutFragment;
import com.jelly.dishu.fragment.FAQFragment;
import com.jelly.dishu.fragment.FeedFragment;
import com.jelly.dishu.fragment.RulerFragment;

public class AboutActivity extends FragmentActivity {

	String[] options;
	ListView listView;
	LayoutInflater inflater;
	int[] icons = {R.drawable.rule,R.drawable.wenhao,
			R.drawable.tanhao,R.drawable.xinfeng};
	FragmentManager fragmentManager;
	ImageButton backStart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = getResources().getStringArray(R.array.option);
		ArrayAdapter<String> adapter = new OptionAdapter(this,R.layout.list_item, R.id.option, options);
		fragmentManager = this.getSupportFragmentManager();
		
		backStart = (ImageButton)findViewById(R.id.back_start);
		backStart.setOnClickListener(back);
		
		listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OptionClicked());

	}
	/**
	 * 返回按钮的单击事件
	 */
	OnClickListener back = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AboutActivity.this,StartActivity.class);
			startActivity(intent);
			AboutActivity.this.finish();
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	/**
	 * 
	 * @author Acer
	 *
	 */
	class OptionClicked implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Log.i("position", position+"");
			Fragment myFragment = new RulerFragment() ;
			switch(position){
			case 0:myFragment = new RulerFragment();break;
			case 1:myFragment = new FAQFragment();break;
			case 2:myFragment = new AboutFragment(); break;
			case 3:myFragment = new FeedFragment();break;
			}
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.container, myFragment);
			fragmentTransaction.commit();
		}
		
	}
	/**
	 * my adapter using to show ListView
	 * @author Acer
	 *
	 */
	class OptionAdapter extends ArrayAdapter<String>{
		String[] options; 
		public OptionAdapter(Context context, int resource,
				int textViewResourceId, String[] options) {
			super(context, resource, textViewResourceId, options);
			this.options = options;
		}
		@Override
		public View getView(int position, View conview, ViewGroup parent) {
			View view = inflater.inflate(R.layout.list_item, null);
			ImageView im = (ImageView)view.findViewById(R.id.icon);
			im.setBackgroundResource(icons[position]);
			TextView tv = (TextView)view.findViewById(R.id.option);
			tv.setText(options[position]);
			tv.getPaint().setFakeBoldText(true);/// set Chinese is bold.
			return view;
		}
		
	
	}

}
