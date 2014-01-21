package com.zuleicos.feagurth.assignment2a;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Nasa extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nasa);
		
		// Attaching the WebView
		WebView nasa = (WebView) findViewById(R.id.webNasa);
		
		// Loading the URL to get the content shown on the WebView
		nasa.loadUrl("file:///android_asset/uofi-at-nasa.html");		
	}
}
