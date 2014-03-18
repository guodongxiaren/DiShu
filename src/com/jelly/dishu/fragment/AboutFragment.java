package com.jelly.dishu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.jelly.dishu.R;

public class AboutFragment extends Fragment {
	ScrollView scrollView;
	ImageView imageView;
	Handler handler = new Handler();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about, container,false); 
		scrollView = (ScrollView)rootView.findViewById(R.id.scrollView);
		imageView = (ImageView)rootView.findViewById(R.id.image_about);

		return rootView;
	}
/**
 * һ��Ҫ�ڴ˷�����ʹ��Handler��post������
 * onCreateView�У������ԡ�Ҫ��onCreateView
 * ִ����֮����ܵõ���Ӧ�ĸ߶ȡ�
 */
	@Override
	public void onResume() {
		super.onResume();
		handler.post(scrollBottom);
	}

	Runnable scrollBottom = new Runnable(){
		@Override
		public void run() {
			int off = imageView.getMeasuredHeight() - scrollView.getHeight();
			if(off>0){
				scrollView.scrollBy(0, 1);
				if(scrollView.getScrollY()==off)
					Thread.currentThread().interrupt();
			}
			//�ӳ٣���һ��ʱ��handlerִ��һ�Σ��Դ˴ﵽ����������Ч��
			handler.postDelayed(this, 10L);
		}
	};
}
