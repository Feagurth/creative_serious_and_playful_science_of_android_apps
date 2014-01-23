package com.zuleicos.feagurth.assignment2b;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public enum gameState {
		WIN, LOSE, CONTINUE
	};

	int intTries;
	int gamesPlayed = 0;
	int gamesWon = 0;

	gameState state = gameState.CONTINUE;

	ArrayList<String> lettersPlayed;

	String wordToGuess;
	String guessedWord;

	TextView tvSolution;
	TextView tvPoints;
	Button btnInputButon;
	EditText txtInputLetter;
	ImageView imgGallows;
	ListView lstLetras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// "http://www.randomhouse.com/features/rhwebsters/words.pperl"

		tvSolution = (TextView) findViewById(R.id.textSolution);
		tvPoints = (TextView) findViewById(R.id.textPoints);
		btnInputButon = (Button) findViewById(R.id.inputButton);
		txtInputLetter = (EditText) findViewById(R.id.editLetra);
		imgGallows = (ImageView) findViewById(R.id.cadalso);
		lstLetras = (ListView) findViewById(R.id.lstLetrasIntroducidas);

		lettersPlayed = new ArrayList<String>();

		lstLetras.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lettersPlayed));

		tvPoints.setText(gamesWon + " / " + gamesPlayed);

		new RetrieveSiteData()
				.execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");
	}

	private int validateLetter(String strValue) {

		if (!"ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".contains(strValue)) {
			return 1;
		}

		if (strValue.equals("")) {
			return 2;
		}

		if (lettersPlayed.contains(strValue)) {
			return 3;
		}

		return 0;
	}

	private Boolean validateWord(String strValue) {

		if (!strValue.equals("") && strValue != null) {
			for (char value : strValue.toCharArray()) {
				if ("abcdefghijklmnñopqrstuvwxyz ".indexOf(value) == -1)
					return false;
			}
		} else {

			return false;
		}

		return true;
	}

	public void inputLetter(View v) {

		String strLetter = txtInputLetter.getText().toString()
				.toLowerCase(Locale.getDefault());

		switch (validateLetter(strLetter.toUpperCase(Locale.getDefault()))) {

		case 0: {

			lettersPlayed.add(strLetter.toUpperCase(Locale.getDefault()));

			if (wordToGuess.indexOf(strLetter) != -1) {

				char[] apoyo = guessedWord.toCharArray();

				guessedWord = "";

				String strTemp = "";

				for (int i = 0; i < wordToGuess.length(); i++) {

					if (wordToGuess.toCharArray()[i] == strLetter.charAt(0)) {
						apoyo[i] = strLetter.charAt(0);
					}

					if (wordToGuess.toCharArray()[i] == " ".charAt(0)) {
						apoyo[i] = " ".charAt(0);
					}

					guessedWord += apoyo[i];
					strTemp += apoyo[i] + " ";
				}

				tvSolution.setText(strTemp.toUpperCase(Locale.getDefault()));
				txtInputLetter.setText("");

				if (guessedWord.equals(wordToGuess)) {
					state = gameState.WIN;
				}

			} else {
				intTries++;

				txtInputLetter.setText("");

				switch (intTries) {
				case 1:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic01));
					break;
				case 2:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic02));
					break;
				case 3:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic03));
					break;
				case 4:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic04));
					break;
				case 5:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic05));
					break;
				case 6:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic06));
					state = gameState.LOSE;
					break;
				default:
					imgGallows.setImageDrawable(getResources().getDrawable(
							R.drawable.pic00));
					break;
				}
			}
			break;
		}
		case 1:
		case 2:
			txtInputLetter.setText("");
			msgBox("Wrong character!", "Please, input a letter...");
			break;
		case 3:
			txtInputLetter.setText("");
			msgBox("Wrong character!", "You already played that letter!\nPlese choose another one...");
			break;
		}

		if (state != gameState.CONTINUE) {
			txtInputLetter.setEnabled(false);
			btnInputButon.setEnabled(false);
			lstLetras.setEnabled(false);
			gamesPlayed++;

			if (state == gameState.WIN) {
				msgBox("Good Work", "You Win!");
				gamesWon++;
			} else {
				msgBox("Oooohhh", "You Lose!\nAnswer: " + wordToGuess);			
				}

			tvPoints.setText(gamesWon + "/ " + gamesPlayed);
			new RetrieveSiteData()
					.execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");
		}

	}

	private void msgBox(String strTitle, String strMessage) {
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
		
		dlgAlert.setTitle(strTitle);
		dlgAlert.setMessage(strMessage);
		
		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
			}
		});

		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	public class RetrieveSiteData extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			StringBuilder builder = new StringBuilder(10000);

			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						builder.append(s);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return builder.toString();
		}

		@Override
		protected void onPostExecute(String result) {

			String html = result;
			String pointer1 = "<H2>";
			String pointer2 = "</H2>";

			int pos1 = html.indexOf(pointer1);
			int pos2 = html.indexOf(pointer2);

			wordToGuess = html.substring(pos1 + 4, pos2 - 1).toLowerCase(
					Locale.getDefault());

			if (validateWord(wordToGuess)) {

				guessedWord = "";
				tvSolution.setText("");

				imgGallows.setImageDrawable(getResources().getDrawable(
						R.drawable.pic00));

				intTries = 0;
				state = gameState.CONTINUE;

				for (int i = 0; i < wordToGuess.length(); i++) {
					if (wordToGuess.toCharArray()[i] == " ".charAt(0)) {
						guessedWord += " ";
						tvSolution.setText(tvSolution.getText() + "  ");
					} else {
						guessedWord += "_";
						tvSolution.setText(tvSolution.getText() + "_ ");
					}

				}

				txtInputLetter.setEnabled(true);
				btnInputButon.setEnabled(true);
				lstLetras.setEnabled(true);
				lettersPlayed.clear();
			} else {
				new RetrieveSiteData()
						.execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");
				try {
					this.finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
