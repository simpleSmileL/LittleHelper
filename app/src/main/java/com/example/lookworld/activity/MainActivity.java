package com.example.lookworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.example.lookworld.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends Activity {

	private Button bt_bews;
	private Button bt_weathear;
	private Button bt_location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bt_bews = (Button) findViewById(R.id.bt_news);
		bt_weathear = (Button) findViewById(R.id.bt_weather);
		bt_location = (Button) findViewById(R.id.bt_location);
		copyDB("address.db");

		bt_location.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,QueryNumberActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		bt_weathear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		bt_bews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent  = new Intent(MainActivity.this,PhotoActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}

	private void copyDB(final String path) {
		// TODO Auto-generated method stub
		File file = new File(getFilesDir(), path);
		if (file.exists() && file.length() > 0) {
			//Log.i(tag, "数据库存在，无需拷贝");
		} else {

			new Thread() {
				public void run() {

					try {
						InputStream is = getAssets().open(path);
						File file = new File(getFilesDir(), path);
						FileOutputStream fos = new FileOutputStream(file);
						byte[] buffer = new byte[1024];
						int len = -1;
						while ((len = is.read(buffer)) != -1) {
							fos.write(buffer, 0, len);
						}
						is.close();
						fos.close();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}.start();
		}
	}


}
