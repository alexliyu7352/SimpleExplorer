package com.example.work_tianyu_simpleexplorer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

public class TopFragment extends Fragment implements OnClickListener{

	private Button mButtonImage;
	private Button mButtonHistory;
	private Button mButtonAudio;
	private Button mButtonVideo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.top_fragment, container, false);
		mButtonImage = (Button) view.findViewById(R.id.buton_img);
		mButtonHistory = (Button) view.findViewById(R.id.buton_history);
		mButtonAudio = (Button) view.findViewById(R.id.buton_audio);
		mButtonVideo = (Button) view.findViewById(R.id.button_video);
		
		mButtonVideo.setOnClickListener(this);
		mButtonImage.setOnClickListener(this);
		mButtonAudio.setOnClickListener(this);
		mButtonHistory.setOnClickListener(this);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.add(new HistoryFragment(), null);
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction	transaction = fragmentManager.beginTransaction();
		VideoFragment 		videoFragment 	= new VideoFragment();
		ImageFragment 		imgFragment 	= new ImageFragment();
		AudioFragment 		audioFragment 	= new AudioFragment();
		HistoryFragment 	historyFragment = new HistoryFragment();
		switch (v.getId()) {
		case R.id.button_video:
			transaction.replace(R.id.content_layout, videoFragment);
			break;
		case R.id.buton_img:
			transaction.replace(R.id.content_layout, imgFragment);
			break;
		case R.id.buton_audio:
			transaction.replace(R.id.content_layout, audioFragment);
			break;
		case R.id.buton_history:
			transaction.replace(R.id.content_layout, historyFragment);
			break;
		default:
			break;
		}
		transaction.commit();
	}
}
