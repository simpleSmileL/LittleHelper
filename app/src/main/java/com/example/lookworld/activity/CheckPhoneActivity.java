package com.example.lookworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookworld.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckPhoneActivity extends Activity {

	protected static final int PHONENUMBER = 1;
	protected static final int LOCATION = 2;
	protected static final int PHONEJX = 3;
	protected static final int ERROR = 4;
	private EditText et_phone;
	private TextView tv_phone;
	private TextView tv_luck;
	private TextView tv_address;
	private Button bt_home;
	private LinearLayout cp_ll;
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case PHONENUMBER:
				tv_phone.setText("你的号码是："+(String) msg.obj);
				break;

			case LOCATION:
				tv_address.setText("你要查询的号码归属地为："+(String) msg.obj);
				break;
				
			case PHONEJX:
				tv_luck.setText("该号码的运营商为："+(String) msg.obj);
				break;
				
			case ERROR:

				Toast.makeText(CheckPhoneActivity.this, "获取失败，检查网络", Toast.LENGTH_SHORT).show();
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkphone_layout);
		et_phone = (EditText) findViewById(R.id.et_phone);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_luck = (TextView) findViewById(R.id.tv_luck);
		tv_address = (TextView) findViewById(R.id.tv_address);
		bt_home = (Button) findViewById(R.id.home);
		cp_ll = (LinearLayout) findViewById(R.id.cp_ll);
		cp_ll.getBackground().setAlpha(100);
		
		bt_home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CheckPhoneActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	public void click(View view) {
		String phone = et_phone.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(CheckPhoneActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
			return ;
		}
		final String path = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile="+phone;
		new Thread(){
			public void run(){
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setReadTimeout(5000);
					int code = conn.getResponseCode();
					if(code == 200){
						InputStream is = conn.getInputStream();
						XmlPullParser parser = Xml.newPullParser();
						parser.setInput(is, "gbk");
						int type = parser.getEventType();
						while(type != XmlPullParser.END_DOCUMENT){
							//解析xml文件
							if(type == XmlPullParser.START_TAG){
								if("chgmobile".equals(parser.getName())){
									String phonenumber = parser.nextText();
									Message msg = Message.obtain();
									msg.what = PHONENUMBER;
									msg.obj = phonenumber;
									handler.sendMessage(msg);
									
								}else if("city".equals(parser.getName())){
									String location = parser.nextText();
									Message msg = Message.obtain();
									msg.what = LOCATION;
									msg.obj = location;
									handler.sendMessage(msg);
								}else if("supplier".equals(parser.getName())){
									String phoneJx = parser.nextText();
									Message msg = Message.obtain();
									msg.what = PHONEJX;
									msg.obj = phoneJx;
									handler.sendMessage(msg);
								}
							}
							type = parser.next();
						}
						
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = ERROR;
					handler.sendMessage(msg);
				}
			}
			
		}.start();
		
		
		
	}

	
}
