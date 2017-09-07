package com.example.lookworld.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.example.lookworld.R;

public class PhotoActivity extends Activity {

	private ImageView per;
	private Bitmap alterBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		per = (ImageView) findViewById(R.id.per);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.pre);
		alterBitmap = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), bitmap.getConfig());
		Canvas canvas = new Canvas(alterBitmap);
		Paint paint = new Paint();
		canvas.drawBitmap(bitmap, new Matrix(), paint);
		per.setImageBitmap(alterBitmap);

		per.setOnTouchListener(new OnTouchListener() {

			int x;
			int y;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					x = (int) event.getX();
					y = (int) event.getY();
					for (int i = -6; i < 7; i++) {
						for (int j = -6; j < 7; j++) {

							try {
								alterBitmap.setPixel(x + i, y + j,
										Color.TRANSPARENT);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
					per.setImageBitmap(alterBitmap);
					break;

				case MotionEvent.ACTION_UP:
					break;

				case MotionEvent.ACTION_MOVE:
					x = (int) event.getX();
					y = (int) event.getY();
					for (int i = -6; i < 7; i++) {
						for (int j = -6; j < 7; j++) {

							try {
								alterBitmap.setPixel(x + i, y + j,
										Color.TRANSPARENT);
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
					per.setImageBitmap(alterBitmap);

					break;
				}
				return true;
			}
		});
	}

	public void home(View view) {
		Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
		startActivity(intent);
		finish();

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
