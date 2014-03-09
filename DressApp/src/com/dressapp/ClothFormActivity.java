package com.dressapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ClothFormActivity extends Activity {

	private Bitmap takenPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloth_saving_form);
        
        // Récupération de la photo prise
        Bundle extra = getIntent().getExtras();
        
        if (extra != null)
        {
        	byte[] picture_from_Camera = (byte[]) extra.get("bitmapPicture");
        	
        	if (picture_from_Camera != null)
        	{
        		ImageView imageTakenPicture = (ImageView) findViewById (R.id.imageView_taken_picture);
        		BitmapFactory.Options options = new BitmapFactory.Options();
		        takenPicture = BitmapFactory.decodeByteArray(picture_from_Camera, 0, picture_from_Camera.length, options);
        		imageTakenPicture.setImageBitmap(takenPicture);
        	}
        }
    }
	
}
