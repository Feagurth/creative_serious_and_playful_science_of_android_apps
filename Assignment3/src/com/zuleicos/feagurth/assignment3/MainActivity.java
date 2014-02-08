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

	ImageButton ibnOne;
	ImageButton ibnTwo;
	ImageButton ibnThree;
	ImageButton ibnFour;

	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;

	TextView textTries;
	Button buttonTry;

	int numTries = 10;

	int colorButton1 = 0;
	int colorButton2 = 0;
	int colorButton3 = 0;
	int colorButton4 = 0;

	int colorImage1 = 6;
	int colorImage2 = 6;
	int colorImage3 = 6;
	int colorImage4 = 6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		ibnOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (colorButton1 < 5) {
					colorButton1++;
				} else {
					colorButton1 = 0;
				}

				ibnOne.setColorFilter(getButtonColor(colorButton1));
			}
		});

		ibnTwo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (colorButton2 < 5) {
					colorButton2++;
				} else {
					colorButton2 = 0;
				}

				ibnTwo.setColorFilter(getButtonColor(colorButton2));

			}
		});

		ibnThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (colorButton3 < 5) {
					colorButton3++;
				} else {
					colorButton3 = 0;
				}

				ibnThree.setColorFilter(getButtonColor(colorButton3));

			}
		});

		ibnFour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (colorButton4 < 5) {
					colorButton4++;
				} else {
					colorButton4 = 0;
				}
				ibnFour.setColorFilter(getButtonColor(colorButton4));

			}
		});

		generateNewGame();

	}

	public void TryToGuess(View view) {

		String code = String.valueOf(colorImage1) + String.valueOf(colorImage2)
				+ String.valueOf(colorImage3) + String.valueOf(colorImage4);
		String guess = String.valueOf(colorButton1)
				+ String.valueOf(colorButton2) + String.valueOf(colorButton3)
				+ String.valueOf(colorButton4);

		boolean[] codeUsed = new boolean[code.length()];
		boolean[] guessUsed = new boolean[guess.length()];

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

		if (correct < 4) {
			putResults(correct, match);
			textTries.setText("Tries: " + --numTries);
		} else {
			msgBox("Congratulations...", "You Win!!!");
			
			generateNewGame();
		}
	}

	private void putResults(int correct, int match) {

		findViewById(
				getIdByString(getApplicationContext(), "result" + numTries))
				.setVisibility(View.VISIBLE);

		ImageView result1 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image1result" + numTries));

		result1.setColorFilter(getButtonColor(colorButton1));

		ImageView result2 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image2result" + numTries));

		result2.setColorFilter(getButtonColor(colorButton2));

		ImageView result3 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image3result" + numTries));

		result3.setColorFilter(getButtonColor(colorButton3));

		ImageView result4 = (ImageView) findViewById(getIdByString(
				getApplicationContext(), "image4result" + numTries));

		result4.setColorFilter(getButtonColor(colorButton4));

		for (int i = 1; i <= correct; i++) {
			ImageView point = (ImageView) findViewById(getIdByString(
					getApplicationContext(), "Peg" + i + "Res" + numTries));
			point.setColorFilter(Color.BLACK);
		}

		for (int i = 1; i <= match; i++) {
			ImageView point = (ImageView) findViewById(getIdByString(
					getApplicationContext(), "Peg" + (i + correct) + "Res"
							+ numTries));
			point.setColorFilter(Color.WHITE);
		}

	}

	private void generateNewGame() {
		Random rnd = new Random();

		colorImage1 = rnd.nextInt(6);
		colorImage2 = rnd.nextInt(6);
		colorImage3 = rnd.nextInt(6);
		colorImage4 = rnd.nextInt(6);

		colorButton1 = 0;
		colorButton2 = 0;
		colorButton3 = 0;
		colorButton4 = 0;

		image1.setColorFilter(Color.DKGRAY);
		image2.setColorFilter(Color.DKGRAY);
		image3.setColorFilter(Color.DKGRAY);
		image4.setColorFilter(Color.DKGRAY);

		ibnOne.setColorFilter(getButtonColor(colorButton1));
		ibnTwo.setColorFilter(getButtonColor(colorButton2));
		ibnThree.setColorFilter(getButtonColor(colorButton3));
		ibnFour.setColorFilter(getButtonColor(colorButton4));

		numTries = 10;

		textTries.setText("Tries: " + numTries);

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
						dialog.dismiss();
					}
				});

		// We set the dialog alert cancelable
		dlgAlert.setCancelable(true);

		// And finally we create it and show it
		dlgAlert.create().show();
	}	

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

	private int getIdByString(Context context, String id) {

		return context.getResources().getIdentifier(id, "id", getPackageName());
	}

}
