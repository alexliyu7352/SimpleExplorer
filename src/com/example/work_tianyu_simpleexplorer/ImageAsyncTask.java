package com.example.work_tianyu_simpleexplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ImageAsyncTask extends AsyncTask<File, String, ArrayList<String>> {

	private static String TAG = "ImageAsyncTask";

	private Context mContext;
	private ListView mListView;
	private TextView mTextView;
	private ArrayList<String> data;
	

	// 图片存放
	private List<File> imgList = new ArrayList<>();
	// 音乐存放
	private List<File> audioList = new ArrayList<>();
	// 视频存放
	private List<File> videoList = new ArrayList<>();
	// 其他存放
	private List<File> othersList = new ArrayList<>();

	// MIME分类所需字符串
	private final String[][] MIME_MapTable = {
			// {后缀名， MIME类型}
			{ "3gp", "video/3gpp" }, { "apk", "application/vnd.android.package-archive" }, { "asf", "video/x-ms-asf" },
			{ "avi", "video/x-msvideo" }, { "bin", "application/octet-stream" }, { "bmp", "image/bmp" },
			{ "c", "text/plain" }, { "class", "application/octet-stream" }, { "conf", "text/plain" },
			{ "cpp", "text/plain" }, { "doc", "application/msword" }, { "exe", "application/octet-stream" },
			{ "gif", "image/gif" }, { "gtar", "application/x-gtar" }, { "gz", "application/x-gzip" },
			{ "h", "text/plain" }, { "htm", "text/html" }, { "html", "text/html" },
			{ "jar", "application/java-archive" }, { "java", "text/plain" }, { "jpeg", "image/jpeg" },
			{ "jpg", "image/jpeg" }, { "js", "application/x-javascript" }, { "log", "text/plain" },
			{ "m3u", "audio/x-mpegurl" }, { "m4a", "audio/mp4a-latm" }, { "m4b", "audio/mp4a-latm" },
			{ "m4p", "audio/mp4a-latm" }, { "m4u", "video/vnd.mpegurl" }, { "m4v", "video/x-m4v" },
			{ "mov", "video/quicktime" }, { "mp2", "audio/x-mpeg" }, { "mp3", "audio/x-mpeg" }, { "mp4", "video/mp4" },
			{ "mpc", "application/vnd.mpohun.certificate" }, { "mpe", "video/mpeg" }, { "mpeg", "video/mpeg" },
			{ "mpg", "video/mpeg" }, { "mpg4", "video/mp4" }, { "mpga", "audio/mpeg" },
			{ "msg", "application/vnd.ms-outlook" }, { "ogg", "audio/ogg" }, { "pdf", "application/pdf" },
			{ "png", "image/png" }, { "pps", "application/vnd.ms-powerpoint" },
			{ "ppt", "application/vnd.ms-powerpoint" }, { "prop", "text/plain" },
			{ "rar", "application/x-rar-compressed" }, { "rc", "text/plain" }, { "rmvb", "audio/x-pn-realaudio" },
			{ "rtf", "application/rtf" }, { "sh", "text/plain" }, { "tar", "application/x-tar" },
			{ "tgz", "application/x-compressed" }, { "txt", "text/plain" }, { "wav", "audio/x-wav" },
			{ "wma", "audio/x-ms-wma" }, { "wmv", "audio/x-ms-wmv" }, { "wps", "application/vnd.ms-works" },
			// {"xml", "text/xml"},
			{ "xml", "text/plain" }, { "z", "application/x-compress" }, { "zip", "application/zip" }, { "", "*/*" } ,
			{"mkv", "video/"}};
	
	public ImageAsyncTask(Context mContext, ListView mListView, TextView mTextView, ArrayList<String> data) {
		super();
		this.mContext = mContext;
		this.mListView = mListView;
		this.mTextView = mTextView;
		this.data = data;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mTextView.setText("Scan preparing");
	}
	
	@Override
	protected ArrayList<String> doInBackground(File... params) {
		ArrayList<String> data = new ArrayList<>();
		find(params[0]);
		List<File> t = getImgList();
		for (File i : t) {
			data.add(i.getName());
		}
		return data;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		mTextView.setText("Scanning:" + values[0]);
	}
	
	@Override
	protected void onPostExecute(ArrayList<String> result) {
		super.onPostExecute(result);
		this.data = result;
		mListView.setAdapter(new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, data));
		mTextView.setText("Scan complete");
	}
	
	// 递归调用此方法来获取目录下所有文件
		public void find(File file) {
			File[] t = file.listFiles();
			
			for (int i = 0; i < t.length; i++) {
				if (t[i].isDirectory()) {
					publishProgress(t[i].getAbsolutePath());
					find(t[i]);
				} else {
					classify(t[i]);
				}
			}
		}

		// 判断尾缀，使用MIME类型进行分类
		public void classify(File file) {
			String fileName = file.getName();
			String postfix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
			if (postfix == "") {
				othersList.add(file);
			}
			for (int i = 0; i < MIME_MapTable.length; i++) {
				if (postfix.equals(MIME_MapTable[i][0])) {
					if (MIME_MapTable[i][1].startsWith("image"))
						imgList.add(file);
					else if (MIME_MapTable[i][1].startsWith("audio"))
						audioList.add(file);
					else if (MIME_MapTable[i][1].startsWith("video"))
						videoList.add(file);
					else 
						othersList.add(file);
				} 

			}
		}

		public List<File> getImgList() {
			return imgList;
		}

		public List<File> getAudioList() {
			return audioList;
		}

		public List<File> getVideoList() {
			return videoList;
		}

		public List<File> getOthersList() {
			return othersList;
		}

		
}
