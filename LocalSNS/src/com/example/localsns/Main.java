package com.example.localsns;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Main extends FragmentActivity implements OnClickListener  {

	private final long	FINSH_INTERVAL_TIME    = 2000;
	private long		backPressedTime        = 0;
	
	private int NUM_PAGES = 4;
	
	private final static int FRAGMENT1 = 0;
	private final static int FRAGMENT2 = 1;
	private final static int FRAGMENT3 = 2;
	private final static int FRAGMENT4 = 3;
	private PagerAdapter TabPager;
	private CustomViewPager pager;
	private Button tabPage1, tabPage2, tabPage3, tabPage4;
	
	public static FragmentManager fragmentManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기 (setcontentview 전에 불러줘야함)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        fragmentManager = getSupportFragmentManager();
		pager = (CustomViewPager)findViewById(R.id.pager);
		TabPager = new PagerAdapter(getSupportFragmentManager());
		pager.setAdapter(TabPager);
		
		tabPage1 = (Button)findViewById(R.id.tabPage1);
		tabPage2 = (Button)findViewById(R.id.tabPage2);
		tabPage3=  (Button)findViewById(R.id.tabPage3);
		tabPage4=  (Button)findViewById(R.id.tabPage4);
		
		tabPage1.setTextColor(Color.parseColor("#eeeeee"));
		
		pager.setAdapter(TabPager);
		pager.setCurrentItem(FRAGMENT1);
		
		tabPage1.setOnClickListener(this);
		tabPage1.setSelected(true);
		tabPage2.setOnClickListener(this);
		tabPage3.setOnClickListener(this);
		tabPage4.setOnClickListener(this);
                               
    }
    
	private class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
 
				switch (position) {
				case FRAGMENT1:
					return new Fragment1();
				case FRAGMENT2:
					return new Fragment2();
				case FRAGMENT3:
					return new Fragment3();
				case FRAGMENT4:
					return new Fragment4();
				}
			
			return null;
			
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
		
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tabPage1:
			setSelected(tabPage1);
			pager.setCurrentItem(FRAGMENT1);
			TabPager.notifyDataSetChanged();
			break;
		case R.id.tabPage2:
			setSelected(tabPage2);
			pager.setCurrentItem(FRAGMENT2);
			TabPager.notifyDataSetChanged();
			break;
		case R.id.tabPage3:
			setSelected(tabPage3);
			pager.setCurrentItem(FRAGMENT3);
			TabPager.notifyDataSetChanged();
			break;
		case R.id.tabPage4:
			setSelected(tabPage4);
			pager.setCurrentItem(FRAGMENT4);
			TabPager.notifyDataSetChanged();
			break;
		}
		
	}
	
	public void setSelected(Button btn){
		tabPage1.setSelected(false);
		tabPage2.setSelected(false);
		tabPage3.setSelected(false);
		tabPage4.setSelected(false);
		
		tabPage1.setTextColor(Color.parseColor("#000000"));
		tabPage2.setTextColor(Color.parseColor("#000000"));
		tabPage3.setTextColor(Color.parseColor("#000000"));
		tabPage4.setTextColor(Color.parseColor("#000000"));

		
		btn.setSelected(true);
		btn.setTextColor(Color.parseColor("#eeeeee"));
	}
	

}

