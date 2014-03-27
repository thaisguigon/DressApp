package com.dressapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ImageButton takePicture, viewAllClothes;
	public static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (user == null)
        {
        	// Début de l'application, pas d'utilisateur existant.
        	// On crée un nouvel utilisateur
        	user = new User (-1, false, null);
        	// On retourne au formulaire de login.
        	Intent intent = new Intent (MainActivity.this, LoginFormActivity.class);
			startActivity (intent);
        }
        else if (user == null || !user.isConnected())
	    {
	    	// User déconnecté : on le renvoie au formulaire de connexion.
	    	Intent intent = new Intent (MainActivity.this, LoginFormActivity.class);
			startActivity (intent);
	    }
        
        setContentView(R.layout.menu);

        takePicture = (ImageButton) findViewById (R.id.buttonTakePicture);
        viewAllClothes = (ImageButton) findViewById (R.id.buttonViewAllClothes);
        
        takePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent (MainActivity.this, CameraPreviewActivity.class);
				startActivity (intent);
			}
		});
        
        viewAllClothes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {				
				Intent intent = new Intent (MainActivity.this, AllClothesDisplayActivity.class);
				startActivity (intent);
			}
		});
    }
    
    @Override
	protected void onResume ()
	{
    	super.onResume();
	    if (user == null || !user.isConnected())
	    {
	    	// User déconnecté : on le renvoie au formulaire de connexion.
	    	Intent intent = new Intent (MainActivity.this, LoginFormActivity.class);
			startActivity (intent);
	    }
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
