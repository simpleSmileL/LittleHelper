package com.example.lookworld.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.example.lookworld.R;

/**
 * Created by simpleSmile on 2017/3/5.
 */

public class PhotoShowActivity extends Activity {
  private ViewPager vp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    vp = (ViewPager) findViewById(R.id.vp);
    vp.setAdapter(new MyAdapter());
  }

  private class MyAdapter extends PagerAdapter {
    @Override public int getCount() {
      return 3;
    }

    @Override public int getItemPosition(Object object) {
      return super.getItemPosition(object);
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      super.destroyItem(container, position, object);
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return false;
    }

    @Override public CharSequence getPageTitle(int position) {
      return super.getPageTitle(position);
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      return super.instantiateItem(container, position);
    }


  }
}
