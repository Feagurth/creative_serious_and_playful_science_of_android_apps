package com.zuleicos.feagurth.assignment2a;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;

public class Jabberwocky extends Activity {
	
	private WebView jabberwocky;
	private MediaPlayer poem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jabberwocky);
		
		// Attaching the WebView
		jabberwocky = (WebView) findViewById(R.id.webJabberwocky);
		
		// Loading the URL to get the jabberwocky content
		jabberwocky.loadUrl("file:///android_asset/jabberwocky.html");
	}

	@Override
	protected void onPause() {
		// Stoping the MediaPlayer
		poem.stop();
		
		super.onPause();
	}

	public void openPic(View v)
	{
		// Opening the picture
		jabberwocky.loadUrl("file:///android_asset/jabberwocky.jpg");
	}
	
	public void openWikipedia(View v)
	{
		
		// Stoping the MediaPlayer
		poem.stop();
		
		// Creating the intent to load the wikipedia page on an external program
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("http://en.wikipedia.org/wiki/Jabberwocky"));
		startActivity(i);		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Creating a MediaPlayer for playing the mp3
		poem = MediaPlayer.create(this, R.raw.jabberwocky);
		
		// Setting the looping
		poem.setLooping(true);
		
		// Starting the playback
		poem.start();		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		// Checking if the WebView can go back when we push the back button
		if((keyCode == KeyEvent.KEYCODE_BACK) && jabberwocky.canGoBack())
		{
			// If so, we go back and return true
			jabberwocky.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
