package com.example.lookworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lookworld.R;
import com.example.lookworld.util.AddressDBDao;

public class QueryNumberActivity extends Activity {
	private EditText et_phoenum;
	private TextView tv_location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_number_activity);
		et_phoenum = (EditText) findViewById(R.id.et_phonenum);
		tv_location = (TextView) findViewById(R.id.tv_location);
		et_phoenum.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 10) {
					String location = AddressDBDao.findLocation(
							QueryNumberActivity.this, s.toString());
					tv_location.setText("号码归属地为：" + location);
				}
				if(s.length() >11){
					Toast.makeText(QueryNumberActivity.this, "请输入11位数字的合法号码哦", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				//Toast.makeText(QueryNumberActivity.this,"改变之后。。。",Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void query(View view) {
		String number = et_phoenum.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(this, "你还没填入查询的号码哦", Toast.LENGTH_SHORT).show();
			return;
		}

		String location = AddressDBDao.findLocation(
				QueryNumberActivity.this, number);
		tv_location.setText("号码归属地为：" + location);
	}
	public void reset(View view){
		et_phoenum.setText("");
		tv_location.setText("");
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(QueryNumberActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}
}
