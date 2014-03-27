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
        setContentView(R.layout.menu);
        
        if (user == null)
        {
        	user = new User (-1, false, null);
        	Intent intent = new Intent (MainActivity.this, LoginFormActivity.class);
			startActivity (intent);
        }
        
        // Récupération des données envoyées par les pages précédentes
        //Bundle extra = getIntent().getExtras();
        //int userId = -1;
        
        /*if (extra == null)
        {
        	Toast toast = Toast.makeText(getApplicationContext(), "You were logged out, please wait.",
					Toast.LENGTH_SHORT);
			toast.show();
        	Intent intent = new Intent (MenuActivity.this, MainActivity.class);
			startActivity (intent);
        }
        else
        {*/
        	//userId = extra.getInt("userId", 0);
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
       // }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
