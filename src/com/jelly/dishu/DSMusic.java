package com.jelly.dishu;


import com.jelly.dishu.tool.DSEngine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class DSMusic extends Service {
	public static boolean isRunning = false;
	MediaPlayer player;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		player = MediaPlayer.create(this, DSEngine.MUSIC_RUNING);
		player.setLooping(DSEngine.LOOP_BACKGROUND_MUSIC);
		player.setVolume(DSEngine.R_VOLUME, DSEngine.L_VOLUME);
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		player.stop();
		player.release();
	}
	@Override
	public void onLowMemory() {
		player.stop();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try{
		player.start();
		isRunning = true;
		}catch(Exception e){
			isRunning = false;
			player.stop();
		}
		return 1;
	}
}
