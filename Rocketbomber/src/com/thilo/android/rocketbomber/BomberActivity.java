/*
 * Copyright (C) Kees Huiberts <itissohardtothinkofagoodemail@gmail.com> 2012
 * 
 * Licensed under the GPL v3
 * 
 * Distributed without any warranty, including those about merchantability
 * or fitness for a particular purpose.
 * If you did not receive the license along with this file, you can find it
 * at <http://www.gnu.org/licenses/>.
 */

package com.thilo.android.rocketbomber;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableRow;

public class BomberActivity extends Activity {
	
	private static final int MENU_RESTART = 1;
	private static final int MENU_SETTINGS = 2;
	
	private BomberView view;
	private BomberThread thread;
	private AdView adView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout myLinear = new LinearLayout(this);
		view = new BomberView(getApplicationContext());

		 android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
		            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 9f);
		view.setLayoutParams(params);
		myLinear.setOrientation(LinearLayout.VERTICAL);
        
		// Place the ad view.
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1f);

		
		View addview = getLayoutInflater().inflate(R.layout.main, myLinear,false);
		
		myLinear.addView(view);
		myLinear.addView(addview);
		setContentView(myLinear);

		thread = view.getThread();
		 AdView mAdView = (AdView) findViewById(R.id.adView);
	        AdRequest adRequest = new AdRequest.Builder().build();
	        mAdView.loadAd(adRequest);
	
	}
	
	public void onStart() {
		super.onStart();
		view.setFocusable(true);
	}
	
	public void onStop() {
		super.onStop();
		thread.setPaused(true);
	}
	
	public boolean onSearchRequested() {
		
		thread.click();
		
		return true;
	}
	
	public void onOptionsMenuClosed(Menu menu) {
		thread.setPaused(false);
		view.setFocusable(true);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		menu.add(0, MENU_RESTART, 1, R.string.menu_restart);
		//menu.add(0, MENU_SETTINGS, 1, R.string.menu_settings);
		
		return true;
	}
	
	public boolean onMenuOpened(int featureId, Menu menu) {
		thread.setPaused(true);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case MENU_RESTART:
				thread.restart();
				thread.setPaused(false);
				break;
			case MENU_SETTINGS:
				startActivity(new Intent(this, BomberPreferences.class));
		}
		return true;
	}
}
