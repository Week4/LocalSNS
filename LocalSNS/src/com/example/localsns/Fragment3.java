package com.example.localsns;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment3 extends Fragment {
	private View view;
	
	
	
	
	public Fragment3() {
		// TODO Auto-generated constructor stub
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment3, container, false);
		
		return view;
	}
}
