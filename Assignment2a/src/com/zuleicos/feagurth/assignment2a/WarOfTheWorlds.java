package com.zuleicos.feagurth.assignment2a;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WarOfTheWorlds extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_war_of_the_worlds);
		
		// Attaching the WebView
		WebView warOfTheWorlds = (WebView) findViewById(R.id.webWarOfTheWorlds);
		
		// Loading the URL to get the content shown on the WebView
		warOfTheWorlds.loadUrl("file:///android_asset/waroftheworlds.html");
	}
}
