package com.zuleicos.feagurth.assignment3;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	// Variables for storing the buttons
	ImageButton ibnOne;
	ImageButton ibnTwo;
	ImageButton ibnThree;
	ImageButton ibnFour;

	// Variables for storing the images of the result
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;

	TextView textTries;
	Button buttonTry;

	// Variable for storing the number of tries to win the game
	int numTries = 10;

	// Current color selected on the players' hand
	int colorButton1 = 0;
	int colorButton2 = 0;
	int colorButton3 = 0;
	int colorButton4 = 0;

	// Current color selected on the images to guess
	int colorImage1 = 6;
	int colorImage2 = 6;
	int colorImage3 = 6;
	int colorImage4 = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting the objects of the layout on their variables
		textTries = (TextView) findViewById(R.id.textTries);

		ibnOne = (ImageButton) findViewById(R.id.imageButton1);
		ibnTwo = (ImageButton) findViewById(R.id.imageButton2);
		ibnThree = (ImageButton) findViewById(R.id.imageButton3);
		ibnFour = (ImageButton) findViewById(R.id.imageButton4);

		image1 = (ImageView) findViewById(R.id.imageView1);
		image2 = (ImageView) findViewById(R.id.imageView2);
		image3 = (ImageView) findViewById(R.id.imageView3);
		image4 = (ImageView) findViewById(R.id.imageView4);

		buttonTry = (Button) findViewById(R.id.buttonTry);

		// Setting the listener for the first button
		ibnOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// Changing colors on every click
				if (colorButton1 < 5) {
					colorButton1++;
				} else {
					colorButton1 = 0;
				}

				ibnOne.setColorFilter(getButtonColor(colorButton1));
			}
		});

		// Setting the listener for the second button
		ibnTwo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Changing colors on every click
				if (colorButton2 < 5) {
					colorButton2++;
				} else {
					colorButton2 = 0;
				}

				ibnTwo.setColorFilter(getButtonColor(colorButton2));

			}
		});

		// Setting the listener for the third button
		ibnThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Changing colors on every click
				if (colorButton3 < 5) {
					colorButton3++;
				} else {
					colorButton3 = 0;
				}

				ibnThree.setColorFilter(getButtonColor(colorButton3));

			}
		});

		// Setting the listener for the Fourth button
		ibnFour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Changing colors on every click
				if (colorButton4 < 5) {
					colorButton4++;
				} else {
					colorButton4 = 0;
				}
				ibnFour.setColorFilter(getButtonColor(colorButton4));

			}
		});

		// Starting a new game
		generateNewGame();

	}

	/**
	 * Method to reset the game table and start a new game
	 */
	private void generateNewGame() {

		// We create random object
		Random rnd = new Random();

		// And randomize the values to guess
		colorImage1 = rnd.nextInt(6);
		colorImage2 = rnd.nextInt(6);
		colorImage3 = rnd.nextInt(6);
		colorImage4 = rnd.nextInt(6);

		// We reset the values of the color selected by the user
		colorButton1 = 0;
		colorButton2 = 0;
		colorButton3 = 0;
		colorButton4 = 0;

		// And hide the colors to guess
		image1.setColorFilter(Color.DKGRAY);
		image2.setColorFilter(Color.DKGRAY);
		image3.setColorFilter(Color.DKGRAY);
		image4.setColorFilter(Color.DKGRAY);

		// We set the color of the player's buttons
		ibnOne.setColorFilter(getButtonColor(colorButton1));
		ibnTwo.setColorFilter(getButtonColor(colorButton2));
		ibnThree.setColorFilter(getButtonColor(colorButton3));
		ibnFour.setColorFilter(getButtonColor(colorButton4));

		// And set the number of tries
		numTries = 10;

		// And the text
		textTries.setText("Tries: " + numTries);

		// We loop to reset all the ImageView with the previous answers hiding
		// it and applying a new color
		for (int i = 1; i <= numTries; i++) {
			findViewById(
					getIdByString(this.getApplicationContext(), "result" + i))
					.setVisibility(View.GONE);

			for (int j = 0; j < 4; j++) {
				ImageView tmpImageView = (ImageView) findViewById(getIdByString(
						getApplicationContext(), "Peg" + (j + 1) + "Res" + i));
				tmpImageView.setColorFilter(Color.LTGRAY);

				tmpImageView = (ImageView) findViewById(getIdByString(
						getApplicationContext(), "image" + (j + 1) + "result"
								+ i));
				tmpImageView.setColorFilter(Color.LTGRAY);
			}
		}
	}

	/**
	 * Method to be used when the user tries to guess a combination
	 * 
	 * @param view
	 *            View associated to the button
	 */
	public void TryToGuess(View view) {

		// Creating an string with the values to guess
		String code = String.valueOf(colorImage1) + String.valueOf(colorImage2)
				+ String.valueOf(colorImage3) + String.valueOf(colorImage4);

		// Creating an string with the values selected by the user
		String guess = String.valueOf(colorButton1)
				+ String.valueOf(colorButton2) + String.valueOf(colorButton3)
				+ String.valueOf(colorButton4);

		// Two boolean arrays to store if the code was used or guessed
		boolean[] codeUsed = new boolean[code.length()];
		boolean[] guessUsed = new boolean[guess.length()];

		// Variable initializing
		int correct = 0;
		int match = 0;

		// Compare correct color and position
		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == guess.charAt(i)) {
				correct++;
				codeUsed[i] = guessUsed[i] = true;
			}
		}

		// Compare matching colors for "pins" that were not used
		for (int i = 0; i < code.length(); i++) {
			for (int j = 0; j < guess.length(); j++) {
				if (!codeUsed[i] && !guessUsed[j]
						&& code.charAt(i) == guess.charAt(j)) {
					match++;
					codeUsed[i] = guessUsed[j] = true;
					break;
				}
			}
		}

		// If we had less than 4 correct colors we are losing..!
		if (correct < 4) {

			// We show the colors used on and the result
			putResults(correct, match);

			// We change the value of the TextView and decrement the number of
			// tries by one
			textTries.setText("Tries: " + --numTries);

			if (numTries == 0) {
				// Showing the code to guess
				showSolution();

				msgBox("Sorry...", "You lose...");
			}

		} else {
			// Showing the code to guess
			showSolution();

			// We show a message if we won the game
			msgBox("Congratulations...", "You Win!!!");
		}
	}

	/**
	 * Method to show the solution of the game
	 */
	private void showSolution() {

		ImageView temp;

		temp = (ImageView) findViewById(getIdByString(getApplicationContext(),
				"imageView1"));
		temp.setColorFilter(getButtonColor(colorImage1));

		temp = (ImageView) findViewById(getIdByString(getApplicationContext(),
				"imageView2"));
		temp.setColorFilter(getButtonColor(colorImage2));

		temp = (ImageView) findViewById(getIdByString(getApplicationContext(),
				"imageView3"));
		temp.setColorFilter(getButtonColor(colorImage3));

		temp = (ImageView) findViewById(getIdByString(getApplicationContext(),
				"imageView4"));
		temp.setColorFilter(getButtonColor(colorImage4));
	}

	/**
	 * Method to show the results of a player's guess
	 * 
	 * @param correct
	 *            Number of correct guesses
	 * @param match
	 *            Number of guesses that match on color but not in position
	 */
	private void putResults(int correct, int match) {

		// We made visible the LinearLayout that contains the Images to show
		findViewById(
				getIdByString(getApplicationContext(), "result" + numTries))
				.setVisibility(View.VISIBLE);

		// We get the first button
		ImageView result1 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image1result" + numTries));

		// And change it's color due to the color selected by the user
		result1.setColorFilter(getButtonColor(colorButton1));

		// We get the second button
		ImageView result2 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image2result" + numTries));

		// And change it's color due to the color selected by the user
		result2.setColorFilter(getButtonColor(colorButton2));

		// We get the third button
		ImageView result3 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image3result" + numTries));

		// And change it's color due to the color selected by the user
		result3.setColorFilter(getButtonColor(colorButton3));

		// We get the fourth button
		ImageView result4 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image4result" + numTries));

		// And change it's color due to the color selected by the user
		result4.setColorFilter(getButtonColor(colorButton4));

		// We loop to set the color of the correct pegs
		for (int i = 1; i <= correct; i++) {
			ImageView point = (ImageView) findViewById(getIdByString(
					getApplicationContext(), "Peg" + i + "Res" + numTries));
			point.setColorFilter(Color.BLACK);
		}

		// We loop to set the color of the matched pegs
		for (int i = 1; i <= match; i++) {
			ImageView point = (ImageView) findViewById(getIdByString(
					getApplicationContext(), "Peg" + (i + correct) + "Res"
							+ numTries));
			point.setColorFilter(Color.WHITE);
		}
	}

	/**
	 * Method to create an alert dialog to show the user some info
	 * 
	 * @param strTitle
	 *            Title of the dialog
	 * @param strMessage
	 *            Message of the dialog
	 */
	private void msgBox(String strTitle, String strMessage) {

		// We create a new alert dialog
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

		// We set the title and the message
		dlgAlert.setTitle(strTitle);
		dlgAlert.setMessage(strMessage);

		// Also we set the positive button to the dialog with a new listener
		// attached to the button
		dlgAlert.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						// We start a new game
						generateNewGame();

						// And dismiss the dialog
						dialog.dismiss();
					}
				});

		dlgAlert.setIcon(R.drawable.ic_launcher);

		// We set the dialog alert no cancelable
		dlgAlert.setCancelable(false);

		// And finally we create it and show it
		dlgAlert.create().show();
	}

	/**
	 * Method to return a color by their color value
	 * 
	 * @param buttonValueColor
	 *            Color value
	 * @return
	 */
	private int getButtonColor(int buttonValueColor) {
		switch (buttonValueColor) {
		case 0:
			return Color.RED;
		case 1:
			return Color.argb(255, 255, 165, 0);
		case 2:
			return Color.YELLOW;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.BLUE;
		case 5:
			return Color.MAGENTA;
		case 6:
			return Color.DKGRAY;
		default:
			return Color.TRANSPARENT;
		}
	}

	/**
	 * Method to generate an id number by an string
	 * 
	 * @param context
	 *            Context of the application
	 * @param id
	 *            String with the id to process
	 * @return Id number as an integer
	 */
	private int getIdByString(Context context, String id) {

		return context.getResources().getIdentifier(id, "id", getPackageName());
	}

}
