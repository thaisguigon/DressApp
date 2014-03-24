package com.dressapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button takePicture, viewAllClothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        takePicture = (Button) findViewById (R.id.buttonTakePicture);
        viewAllClothes = (Button) findViewById (R.id.buttonViewAllClothes);
        
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
