package com.zuleicos.feagurth.assignment2b;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	String wordToGuess;
	String guessedWord;
	TextView tvSolution;
	Button btnInputButon;
	EditText txtInputLetter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// "http://www.randomhouse.com/features/rhwebsters/words.pperl"

		tvSolution = (TextView) findViewById(R.id.textSolution);
		btnInputButon = (Button) findViewById(R.id.inputButton);
		txtInputLetter = (EditText) findViewById(R.id.editLetra);
		
		new RetrieveSiteData().execute("http://www.randomhouse.com/features/rhwebsters/words.pperl");

	}

	public void inputLetter(View v)
	{
		
		System.out.print("GetText: " + txtInputLetter.getText());
		System.out.print("toString: " + txtInputLetter.toString());
		
		
		if(wordToGuess.indexOf(txtInputLetter.getText().toString().toLowerCase(Locale.getDefault())) != -1)
		{
			Toast.makeText(this, "Acierto", Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();			
		}
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
			String pointer2= "</H2>";
			
			System.out.print("1" + html + "\n");
			
			int pos1 = html.indexOf(pointer1);
			int pos2 = html.indexOf(pointer2);
			
			wordToGuess = html.substring(pos1 + 4, pos2 - 1).toLowerCase(Locale.getDefault());
			
			
			guessedWord = "";
			
			for (int i = 0; i < wordToGuess.length(); i++) {
				guessedWord += "_ ";
			}
			
			tvSolution.setText(guessedWord);
			
		}
	}

	
	
	
	
}
