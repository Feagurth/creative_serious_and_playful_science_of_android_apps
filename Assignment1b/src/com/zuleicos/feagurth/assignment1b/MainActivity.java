package com.zuleicos.feagurth.assignment1b;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	// Declarating objects and variables
	private TextView textTimer;
	private TextView textPoints;
	private Button startStopButton;
	private long startTime = 0L;

	private Handler myHandler = new Handler();

	int milliseconds = 1;
	int points = 0;
	
	long timeInMillies = 0L;
	long timeSwap = 0L;
	long finalTime = 0L;

	boolean paused = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting the controls
		textPoints = (TextView) findViewById(R.id.txtPoints);
		textTimer = (TextView) findViewById(R.id.textTimer);
		startStopButton = (Button) findViewById(R.id.btnStart);
		
		// Adding listener to the button to control the click
		startStopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				// We check if the chrono is paused
				if (paused) {

					// Updating the time stored between clicks
					timeSwap += timeInMillies;
					myHandler.removeCallbacks(updateTimerMethod);
					
					// Setting the pause to false
					paused = false;
					
					// Change the value of the text in the button
					startStopButton.setText(getString(R.string.Start));
					
					// If milliseconds are equal to zero when the timer is paused we have scored!
					if(milliseconds == 0)
					{
						// Add 1 to the points value
						points++;
						
						// Update the score
						textPoints.setText(getString(R.string.Points) + points);						
					}					
					
				} else {
					
					// We set the value of in milliseconds of the start time 
					startTime = SystemClock.uptimeMillis();
					
					// Updating the handler
					myHandler.postDelayed(updateTimerMethod, 0);
					
					// Setting the pause to true
					paused = true;
					
					// Change the value of the text in the button					
					startStopButton.setText(getString(R.string.Stop));						
				}

			}
		});

		// Setting the initial score
		textPoints.setText(getString(R.string.Points) + points);
		
	}

	private Runnable updateTimerMethod = new Runnable() {

		public void run() {
			
			// The time in milliseconds is the actual time in milliseconds minus the start time
			timeInMillies = SystemClock.uptimeMillis() - startTime;
			
			// The actual time is the time in millisecons plus the time beetween clicks
			finalTime = timeSwap + timeInMillies;

			// Converting the actual time to seconds dividing between 1000
			int seconds = (int) (finalTime / 1000);
			
			// Converting the seconds to minutes dividing between 60
			int minutes = seconds / 60;
			
			// Getting the resting seconds with mod  
			seconds = seconds % 60;
			
			// Getting milliseconds rounded with 2 decimals with mod and dividing 
			milliseconds = (int) (finalTime % 1000) /10;
			
			// Setting the value of the chrono in a string
			textTimer.setText("" + minutes + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%02d", milliseconds));								
			
			myHandler.postDelayed(this, 0);
		}

	};

}
