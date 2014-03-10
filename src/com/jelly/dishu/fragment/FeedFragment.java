package com.jelly.dishu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jelly.dishu.R;

public class FeedFragment extends Fragment {
	Button send;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_feedback, container,false); 
		send = (Button)rootView.findViewById(R.id.sendSMS);
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SmsManager sms = SmsManager.getDefault();
				sms.sendTextMessage("15970635077", null, "hello", null, null);
			}
		});
		return rootView;
	}

}
