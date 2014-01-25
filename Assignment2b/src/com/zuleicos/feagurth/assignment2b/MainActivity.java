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
	String meaning;

	TextView tvSolution;
	TextView tvPoints;
	Button btnInputButon;
	EditText txtInputLetter;
	ImageView imgGallows;
	ListView lstLetters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Getting the objects from the activity
		tvSolution = (TextView) findViewById(R.id.textSolution);
		tvPoints = (TextView) findViewById(R.id.textPoints);
		btnInputButon = (Button) findViewById(R.id.inputButton);
		txtInputLetter = (EditText) findViewById(R.id.editLetter);
		imgGallows = (ImageView) findViewById(R.id.gallows);
		lstLetters = (ListView) findViewById(R.id.lstLettersPlayed);

		// Creating the array list to track the letters played
		lettersPlayed = new ArrayList<String>();

		// Setting the array list to the listview on the activity
		lstLetters.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, lettersPlayed));

		// Setting the score on the textview
		tvPoints.setText(gamesWon + " / " + gamesPlayed);

		// Using an async task to get the random word parsing a web page
		new RetrieveNewWord()
				.execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");
	}

	/**
	 * Function to validate the characters the user can input
	 * @param strValue Character to validate
	 * @return 
	 * 0 if the character is valid (is a letter)
	 * 1 if the character is not a letter
	 * 2 if the character is empty
	 * 3 if the character is a letter already played 
	 */
	private int validateCharacter(String strValue) {

		// We check if the letter is not on the list below
		if (!"ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".contains(strValue)) {
			// If is not a letter, we return code 1
			return 1;
		}

		// We check if the character is empty
		if (strValue.equals("")) {
			// If it's empty, we return code 2
			return 2;
		}

		// We check if the character is a letter already played
		if (lettersPlayed.contains(strValue)) {
			// If it's already played, we return code 3
			return 3;
		}
		
		// If the character is a letter not played, we return code 0
		return 0;
	}

	/**
	 * Function to validate word searching for non valid characters
	 * @param strValue word to validate
	 * @return True if the word is valid, else if it's not
	 */
	private Boolean validateWord(String strValue) {

		// We check if the value is not null and not empty
		if (!strValue.equals("") && strValue != null) {
			
			// We iterate for the chracters of the word
			for (char value : strValue.toCharArray()) {
				
				// We check if the character is on the valid list string
				if ("abcdefghijklmnñopqrstuvwxyz ".indexOf(value) == -1)
					
					// If it's not, we return false (invalid character)
					return false;
			}
		} else {

			// We return false if the word is empty or null
			return false;
		}

		// All correct, we return true
		return true;
	}

	/**
	 * Method to control the input of a character on the game
	 * @param v
	 */
	public void inputCharacter(View v) {

		// We store the character in a variable and transform it in the lowercase version
		String strLetter = txtInputLetter.getText().toString()
				.toLowerCase(Locale.getDefault());

		// We validate the character and do different things by the result of the validation
		switch (validateCharacter(strLetter.toUpperCase(Locale.getDefault()))) {

		// Validation correct, the character input is a valid letter
		case 0: {

			// We add the letter to the letters played list in uppercase
			lettersPlayed.add(strLetter.toUpperCase(Locale.getDefault()));

			// Now, we check if the word to guess contains the character input of the player
			if (wordToGuess.indexOf(strLetter) != -1) {

				// We transform the string with the guessed word so far into an array
				char[] temp = guessedWord.toCharArray();

				// We reset the variables to empty before the 
				// reconstruction of the strings
				guessedWord = "";
				String strTemp = "";
				

				// We iterate word to guess by characters to locate where 
				// the character input by the user is
				for (int i = 0; i < wordToGuess.length(); i++) {
					
					// Checking where the player's character is
					if (wordToGuess.toCharArray()[i] == strLetter.charAt(0)) {
						// If the find one character that matches, we put it on the guessed word
						temp [i] = strLetter.charAt(0);
					}

					// Checking for spaces
					if (wordToGuess.toCharArray()[i] == " ".charAt(0)) {
						// Putting the spaces on the guessed word
						temp [i] = " ".charAt(0);
					}

					// If the player's input is not in this character, we just put the actual 
					// value of temp array on it 
					guessedWord += temp [i];
					
					// And do the same with the temp string where we are storing the string 
					// showed to the user but with a blank space between characters
					strTemp += temp [i] + " ";
				}

				// Finally, we set the masked string to the textview on the activity
				tvSolution.setText(strTemp.toUpperCase(Locale.getDefault()));
				
				// And reset the value of the textbox
				txtInputLetter.setText("");

				// If the guessed word is equal to the word to guess
				// we won the game
				if (guessedWord.equals(wordToGuess)) {
					
					// We change the game state
					state = gameState.WIN;
				}

			} else {
				
				// If the letter is not on the word to guess we have a miss
				// We increment the number of tries
				intTries++;

				// We reset the text box
				txtInputLetter.setText("");

				// We change the pic of the imageview by the number of tries we
				// actually have
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
			// Break for the validate character swicth
			break;
		}
		case 1:
		case 2:
			// Case 1 and 2 for the validate character switch get the same response
			// Input is not a letter
			// Resetting the textbox
			txtInputLetter.setText("");
			
			// Showing an alert box with message for the user.
			msgBox(getString(R.string.wrong_character_),
					getString(R.string.please_input_a_letter_));
			break;
		case 3:
			// Case 3 for the validate character switch.
			// Letter already played
			/// Resetting the textbox
			txtInputLetter.setText("");
			
			// Showing an alert box with message for the user.			
			msgBox(getString(R.string.wrong_character_),
					getString(R.string.you_already_played_that_letter_plese_choose_another_one_));
			break;
		}

		// Now we check the game estate and see if the game is CONTINUE
		if (state != gameState.CONTINUE) {
			
			// If not the game is either a WIN or a LOSE game
			// We disabled the controls
			txtInputLetter.setEnabled(false);
			btnInputButon.setEnabled(false);
			lstLetters.setEnabled(false);
			
			// And we increment the number of games player
			gamesPlayed++;

			// If the game estate is WIN
			if (state == gameState.WIN) {
				
				// Showing an alert box to the user with a message
				// and the meaning of the word guessed
				msgBox(getString(R.string.good_work),
						getString(R.string.you_win_) + "\n"
								+ getString(R.string.meaning_) + meaning);
				
				// We increment the number of won games 
				gamesWon++;
			} else {
				
				// If the game state is not WIN then is LOSE
				// We show an alert box to the user with a message, the word to guess
				// and it's meaning
				msgBox(getString(R.string.oooohhh),
						getString(R.string.you_lose_) + "\n"
								+ getString(R.string.answer_) + wordToGuess
								+ "\n" + getString(R.string.meaning_) + meaning);
			}

			// We update the score
			tvPoints.setText(gamesWon + "/ " + gamesPlayed);
			
			// And finally we retrieve a new random word from the internet
			new RetrieveNewWord()
					.execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");
		}

	}

	/**
	 * Method to create an alert dialog to show the user some info
	 * @param strTitle Title of the dialog
	 * @param strMessage Message of the dialog
	 */
	private void msgBox(String strTitle, String strMessage) {
		
		// We create a new alert dialog 
		AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

		// We set the title and the message
		dlgAlert.setTitle(strTitle);
		dlgAlert.setMessage(strMessage);

		// Also we set the positive button to the dialog with a new listener attached to the button
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

	/**
	 * 
	 * Class for retrieving new random words from internet
	 * On the onPostExecute method parser is done to retrieve a word
	 * and a meaning from http://www.randomhouse.com/features/rhwebsters/words.pperl
	 * web page
	 * 
	 */
	public class RetrieveNewWord extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			
			// We create a new string builder to add the info of the webpage
			StringBuilder builder = new StringBuilder(10000);

			// For each url passed as parameter
			for (String url : urls) {
				
				// We create the http client
				DefaultHttpClient client = new DefaultHttpClient();
				
				// and a http get order with the url
				HttpGet httpGet = new HttpGet(url);
								
				try {
					
					// Now we try to execute the get order on the http client
					HttpResponse execute = client.execute(httpGet);
					
					// And pass the content to an input stram
					InputStream content = execute.getEntity().getContent();

					// And then to a buffered reader
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					
					// We create a string to store the lines of the buffer 
					String s = "";
					
					// We iterate through the buffer reading it's lines
					while ((s = buffer.readLine()) != null) {
						
						// And adding the lines to the string builder to recreate
						// the web page into a string
						builder.append(s);
					}

				} catch (Exception e) {
					// Error catching
					e.printStackTrace();
				}
			}

			// Returning the string with the content of the webpage
			return builder.toString();
		}

		@Override
		protected void onPostExecute(String result) {

			// After the getting of the html from the web page,
			// we store the result on a variable to work with it
			String html = result;
			
			// We create two pointers, one for the html code before where the word to parse is
			String pointer1 = "<H2>";
			
			// And the second one with the html code we had after the word we want to parse
			String pointer2 = "</H2>";

			// With that pointers we can search their possitions on the html code of the web page
			int pos1 = html.indexOf(pointer1);
			int pos2 = html.indexOf(pointer2);

			// With that values we now know where is the word we want to parse, and we get it
			// as a substring of the html string
			wordToGuess = html.substring(pos1 + 4, pos2 - 1).toLowerCase(
					Locale.getDefault());

			// Now we validate the word to search for invalidate characters
			if (validateWord(wordToGuess)) {

				// If the word is valid, we use the same procedure to get the word
				// to get the meaning of the word
				// We set the pointers
				pointer1 = "<blockquote>";
				pointer2 = "</blockquote>";

				// Locate the pointers on the html
				pos1 = html.indexOf(pointer1, pos2);
				pos2 = html.indexOf(pointer2, pos2);

				// Getting the meaning
				meaning = html.substring(pos1 + 12, pos2);

				// Transforming it to proper case
				meaning = String.valueOf(meaning.charAt(0)).toUpperCase(
						Locale.getDefault())
						+ meaning.substring(1, meaning.length());

				// Reseting the varialbe to store the guessed word
				guessedWord = "";
				
				// Reseting textview with the word to show to the player
				tvSolution.setText("");

				// Reseting the image of the gallows
				imgGallows.setImageDrawable(getResources().getDrawable(
						R.drawable.pic00));

				// Reseting the number of tries
				intTries = 0;
				
				// We clear the list of letters played
				lettersPlayed.clear();
				
				// Setting the game state to CONTINUE
				state = gameState.CONTINUE;

				// Iterating the length of the word to guess to create the masked strings
				// to store the guessed word and to show to the player
				for (int i = 0; i < wordToGuess.length(); i++) {
					
					// We check for blank spaces to add it ti the masks
					if (wordToGuess.toCharArray()[i] == " ".charAt(0)) {
						guessedWord += " ";
						tvSolution.setText(tvSolution.getText() + "  ");
					} else {
						guessedWord += "_";
						tvSolution.setText(tvSolution.getText() + "_ ");
					}
				}

				// We enabled the controls to allow the player play
				txtInputLetter.setEnabled(true);
				btnInputButon.setEnabled(true);
				lstLetters.setEnabled(true);
				
			} else {
				
				// If the word has non valid characters, we ask another one
				new RetrieveNewWord()
						.execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");
				try {
					
					// We try to finalize the actual thread 
					this.finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

}
