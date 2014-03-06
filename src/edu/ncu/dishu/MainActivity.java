package edu.ncu.dishu;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

public class MainActivity extends Activity implements OnTouchListener{
int s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(new DrawView(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return false;
	}
	public class DrawView extends View{
		//SurfaceHolder surface = null;
		AssetManager assets = null;
		Bitmap dishu = null;
		public DrawView(Context context) {
			super(context);
			//surface = getHolder();
			assets = context.getAssets();
			try {
				InputStream istream = assets.open("dishu.png");
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				dishu = BitmapFactory.decodeStream(istream, null, options);
				istream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawColor(Color.rgb(85, 107, 47));
			canvas.drawBitmap(dishu, 20, 20,null);
		}
		
		
	}

}
