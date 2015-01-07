package com.baidu.bvideoviewsample1;

import com.baidu.bvideoviewsample1.R;
import com.baidu.bvideoviewsample1.filesselect.FileSelectDialog;
import com.baidu.bvideoviewsample1.filesselect.FileSelectedCallBackBundle;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private final String TAG = "MainActivity";
	private Button mSelectVideoBtn;
	private Button mPlayBtn;
	private EditText mSourceET;
	private Dialog mFileSelectDialog;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		initUI();
	}
	
	void initUI(){
	    mSelectVideoBtn = (Button) findViewById(R.id.select_video_Btn);
		mPlayBtn = (Button)findViewById(R.id.playBtn);
		mSourceET = (EditText)findViewById(R.id.getET);
		mPlayBtn.setOnClickListener(this);
		mSelectVideoBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch(id){
		case R.id.playBtn:
			/**
			 * 播放一个视频
			 */
			playVideo();
			break;
		case R.id.select_video_Btn:
		    selectVideo();
		    break;
		default:
			break;
		}
	}
	
	private void selectVideo() {
        if (mFileSelectDialog == null) {
            FileFilter filter = createFileFilter();
            mFileSelectDialog = FileSelectDialog.createDialog(MainActivity.this, "选择视频", filter, new FileSelectedCallBackBundle() {
                @Override
                public void onFileSelected(Bundle bundle) {
                    String uri = bundle.getString("uri");
                    mSourceET.setText(uri);
                    if (mFileSelectDialog != null && mFileSelectDialog.isShowing()) {
                        mFileSelectDialog.dismiss();
                    }
                }
            });
        }
        mFileSelectDialog.show();
    }
        
    private FileFilter createFileFilter() {
        final HashMap<String, String> supportTypeMap = new HashMap<String, String>();
        supportTypeMap.put("rmvb", "rmvb");
        supportTypeMap.put("mp4", "mp4");
        supportTypeMap.put("3gp", "3gp");
        supportTypeMap.put("mov", "mov");
        supportTypeMap.put("mkv", "mkv");
        supportTypeMap.put("ts", "ts");
        supportTypeMap.put("flv", "flv");
        supportTypeMap.put("asf", "asf");
        supportTypeMap.put("wmv", "wmv");
        supportTypeMap.put("rm", "rm");
        supportTypeMap.put("avi", "avi");
        supportTypeMap.put("f4v", "f4v");
        supportTypeMap.put("m3u8", "m3u8");
        supportTypeMap.put("ac3", "ac3");
        supportTypeMap.put("mpg", "mpg");
        supportTypeMap.put("vob", "vob");
        supportTypeMap.put("swf", "swf");

        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                }

                String path = pathname.getName().toLowerCase();
                if (path != null) {
                    int index = path.lastIndexOf('.');
                    if (index != -1) {
                        String suffix = path.substring(index + 1);
                        if (supportTypeMap.get(suffix) != null) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
        return filter;
    }

    private void playVideo() {
		String source = mSourceET.getText().toString();
		if (source == null || source.equals("")) {
			/**
			 * 简单检测播放源的合法性,不合法不播放
			 */
			Toast.makeText(this, "please input your video source", 500).show();
			source = "http://devimages.apple.com/iphone/samples/bipbop/gear4/prog_index.m3u8";
			Intent intent = new Intent(this, VideoViewPlayingActivity.class);
			intent.setData(Uri.parse(source));
			startActivity(intent);			

			
		} else {
			Intent intent = new Intent(this, VideoViewPlayingActivity.class);
			intent.setData(Uri.parse(source));
			startActivity(intent);
		}
	}
}
