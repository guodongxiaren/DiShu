package com.jelly.dishu.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jelly.dishu.R;

public class FeedFragment extends Fragment {
	Button send;
	EditText edit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_feedback, container,false); 
		send = (Button)rootView.findViewById(R.id.sendSMS);
		edit = (EditText)rootView.findViewById(R.id.editText);
		send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SmsManager sms = SmsManager.getDefault();
				String msg = edit.getText().toString().trim();
				sms.sendTextMessage("1807040663", null, msg, null,  null);
			}
		});
		return rootView;
	}

}
