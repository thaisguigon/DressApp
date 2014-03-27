package com.dressapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionFormActivity extends Activity {
	
	Button buttonSubmit, buttonCancel;
	EditText fieldUserName, fieldEmail, fieldPassword;
	
	private class CreateUserTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... identifiers)
		{
			return APIRequestsManager.createUser(identifiers[0], identifiers[1], identifiers[2]);
		}

		@Override
		protected void onPostExecute (Boolean result)
		{
			if (result)
			{
				// Affichage d'un Toast
				Toast toast = Toast.makeText(getApplicationContext(), "Your account was successfully created.",
						Toast.LENGTH_SHORT);
				toast.show();
				
				// Retour sur le formulaire de connexion
				Intent intent = new Intent (InscriptionFormActivity.this, LoginFormActivity.class);
				startActivity (intent);
			}
			else
			{
				// Affichage d'un Toast
				Toast toast = Toast.makeText(getApplicationContext(), "This username already exists. Please chose another one.",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
	
	@Override
    protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription_form);
        
        fieldUserName = (EditText) findViewById(R.id.fieldUserName);
        fieldEmail = (EditText) findViewById(R.id.fieldEmail);
		fieldPassword = (EditText) findViewById(R.id.fieldPassword);
        
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username, email, password;
				username = fieldUserName.getText().toString();
				email = fieldEmail.getText().toString();
				password = fieldPassword.getText().toString();
				
				// On lance la requête de création de compte.
				new CreateUserTask ().execute(username, email, password);
			}
		});
        
        buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent (InscriptionFormActivity.this, LoginFormActivity.class);
				startActivity (intent);
			}
		});
	}
}
