package com.zuleicos.feagurth.assignment2a;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class Roundball extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_roundball);

		// Attaching the WebView
		WebView roundBall = (WebView) findViewById(R.id.webRoundball);

		// Loading the URL to get the content shown on the WebView
		roundBall.loadUrl("file:///android_asset/roundball/roundball.html");

		// Setting the JavaScript and the DomStorage enabled
		roundBall.getSettings().setJavaScriptEnabled(true);
		roundBall.getSettings().setDomStorageEnabled(true);
	}
}
