package com.example.work_tianyu_simpleexplorer;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class AudioFragment extends Fragment{

	private ListView mListView;
	private TextView mTextView;
	
	ArrayList<String> data = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.audio_fragment, container, false);
		mListView = (ListView) view.findViewById(R.id.listview_image);
		mTextView = (TextView) view.findViewById(R.id.textview_image);
		//后台线程更新数据
		initData(new File(getString(R.string.path)));
		return view;
	}
	
	// 初始化显示所有图片的ListView的数据
	private void initData(File file) {
		AudioAsyncTask ia = new AudioAsyncTask(getActivity(),mListView,mTextView,data);
		ia.execute(file);
	}
}
