package com.dressapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFormActivity extends Activity {

	Button buttonSignIn, buttonRegister;
	EditText fieldUserName, fieldPassword;
	
	private class AuthenticateUserTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... identifiers)
		{
			return APIRequestsManager.authenticateUser(identifiers[0], identifiers[1]);
		}

		@Override
		protected void onPostExecute (Boolean result)
		{
			if (result)
			{
				// Connexion à l'appli si réussi
				Intent intent = new Intent (LoginFormActivity.this, MainActivity.class);
				//intent.putExtra("userId", 1);
				startActivity (intent);
			}
			else
			{
				// Affichage d'un Toast sinon
				Toast toast = Toast.makeText(getApplicationContext(), "The username or the password provided are incorrect.\nPlease try again.",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	
	@Override
    protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);
        
		fieldUserName = (EditText) findViewById(R.id.fieldUserName);
		fieldPassword = (EditText) findViewById(R.id.fieldPassword);
		
		buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
		buttonRegister = (Button) findViewById(R.id.buttonRegister);
		
		buttonRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent (LoginFormActivity.this, InscriptionFormActivity.class);
				startActivity (intent);
			}
		});
		
		buttonSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username, password;
				username = fieldUserName.getText().toString();
				password = fieldPassword.getText().toString();
				
				// Authentification - Validation ou refus des identifiants donnés
				new AuthenticateUserTask ().execute(username, password);
				
				//Intent intent = new Intent (MainActivity.this, MenuActivity.class);
				//startActivity (intent);
			}
		});
	}
}
