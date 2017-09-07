package com.example.lookworld.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lookworld.R;
import com.example.lookworld.util.StreamTools;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import bean.Weather;

public class WeatherActivity extends Activity {
	protected static final int GANMAO = 0;
	protected static final int SUCCESS = 1;
	protected static final int ERROR_CITY = 2;
	protected static final int ERROR = 3;
	private EditText et_citynam;
	private Button bt_check;
	private Button bt_home;
	private List<Weather> list;
	private LinearLayout ll;
	private ListView weather_lv;
	private SharedPreferences sp;
	private ProgressDialog pd;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GANMAO:
				String ganmao = (String) msg.obj;
				TextView t = (TextView) findViewById(R.id.ganmao);
				t.setText("今天天气舒适状况：" + ganmao);
				break;

			case SUCCESS:
				
				weather_lv.setAdapter(new BaseAdapter() {

					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						View view = View.inflate(WeatherActivity.this,
								R.layout.weather_item, null);

						TextView date = (TextView) view
								.findViewById(R.id.weather_date);
						TextView fengli = (TextView) view
								.findViewById(R.id.weather_fengli);
						TextView low = (TextView) view
								.findViewById(R.id.weather_low);

						date.setText("日期：" + list.get(position).getDate()
								+ "\n" + list.get(position).getType());

						fengli.setText("风速："
								+ list.get(position).getFengxiang()
								+ list.get(position).getFengli() + "\t");

						low.setText("温度：" + list.get(position).getLow() + "\t"
								+ list.get(position).getHight());

						return view;

					}

					@Override
					public long getItemId(int position) {
						return 0;
					}

					@Override
					public Object getItem(int position) {
						return null;
					}

					@Override
					public int getCount() {
						return list.size();
					}
				});

				break;

			case ERROR_CITY:
				Toast.makeText(WeatherActivity.this, "城市名称错误", Toast.LENGTH_SHORT).show();
				break;

			case ERROR:
				Toast.makeText(WeatherActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
				break;
			}


		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		et_citynam = (EditText) findViewById(R.id.et_cityname);
		bt_check = (Button) findViewById(R.id.bt_check);
		bt_home = (Button) findViewById(R.id.bt_home);
		weather_lv = (ListView) findViewById(R.id.weather_lv);
		ll = (LinearLayout) findViewById(R.id.ll);
		pd = new ProgressDialog(WeatherActivity.this);
	
		sp = getSharedPreferences("simple", 0);
		String city = sp.getString("cityname", "");

		if(city != null){
			et_citynam.setText(city);
		}
		Options opts = new Options();

		opts.inSampleSize = 10;
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.guan, opts);
		ll.setBackground(new BitmapDrawable(bitmap));
		bt_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WeatherActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

		bt_check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String cityname = et_citynam.getText().toString().trim();
				if (TextUtils.isEmpty(cityname)) {
					Toast.makeText(WeatherActivity.this, "请检查城市名称是否正确", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				
				pd.setMessage("死命为你加载中.....");
				pd.show();
				new Thread() {
					
					public void run() {
						try {
							String path = "http://wthrcdn.etouch.cn/weather_mini?city="
									+ URLEncoder.encode(cityname, "utf-8");
							URL url = new URL(path);
							HttpURLConnection conn = (HttpURLConnection) url
									.openConnection();
							conn.setRequestMethod("GET");
							conn.setConnectTimeout(3000);
							int code = conn.getResponseCode();
							list = new ArrayList<Weather>();
							if (code == 200) {
								InputStream is = conn.getInputStream();
								String json = StreamTools.readStream(is);
								JSONObject jsonObj = new JSONObject(json);

								String result = jsonObj.getString("desc");
								if ("OK".equals(result)) {
									JSONObject dataJSON = jsonObj
											.getJSONObject("data");
									String ganmao = dataJSON
											.getString("ganmao");
									JSONArray array = dataJSON
											.getJSONArray("forecast");
									Message msg = Message.obtain();
									msg.what = GANMAO;
									msg.obj = ganmao;
									handler.sendMessage(msg);

									for (int i = 0; i < array.length(); i++) {
										try {

											Weather weather = new Weather();
											String date = array
													.getJSONObject(i)
													.getString("date");
											String fengli = array
													.getJSONObject(i)
													.getString("fengli");
											String fengxiang = array
													.getJSONObject(i)
													.getString("fengxiang");
											//System.out.println(fengli + "===="
											//		+ fengxiang);
											String high = array
													.getJSONObject(i)
													.getString("high");
											String low = array.getJSONObject(i)
													.getString("low");
											String type = array
													.getJSONObject(i)
													.getString("type");
											weather.setDate(date);
											weather.setFengli(fengli);
											weather.setFengxiang(fengxiang);
											weather.setHight(high);
											weather.setLow(low);
											weather.setType(type);
											weather.setGanmao(ganmao);
											list.add(weather);
										
										} catch (Exception e) {
											e.printStackTrace();
										}
									}

								} else {
									Message msg = Message.obtain();
									msg.what = ERROR_CITY;
									handler.sendMessage(msg);
								}
								pd.dismiss();
								Message msg = Message.obtain();
								msg.obj = list;
								msg.what = SUCCESS;
								handler.sendMessage(msg);

								sp = getSharedPreferences("simple", 0);
								Editor et = sp.edit();
								et.putString("cityname", cityname);
								et.apply();
							}

						} catch (Exception e) {
							e.printStackTrace();
							Message msg = Message.obtain();
							msg.what = ERROR;
							handler.sendMessage(msg);
						}
					};

				}.start();

			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(WeatherActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}
}
