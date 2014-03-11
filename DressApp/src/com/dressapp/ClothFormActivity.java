package com.dressapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ClothFormActivity extends Activity {

	private enum e_Mode
	{
		VIEW,
		EDIT,
		SAVE;
	}
	
	private e_Mode mode = e_Mode.SAVE;
	private Bitmap takenPicture;
	private Cloth cloth = null;
	private Button submitButton, cancelButton, editButton, deleteButton;
	private ImageView imageTakenPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloth_saving_form);
        
        // Récupération des données envoyées par les pages précédentes
        Bundle extra = getIntent().getExtras();
        
        if (extra != null)
        {
        	// Récupération de la photo prise
        	byte[] picture_from_Camera = (byte[]) extra.get("bitmapPicture");
        	
        	if (picture_from_Camera != null)
        	{
        		imageTakenPicture = (ImageView) findViewById (R.id.imageView_taken_picture);
        		BitmapFactory.Options options = new BitmapFactory.Options();
		        takenPicture = BitmapFactory.decodeByteArray(picture_from_Camera, 0, picture_from_Camera.length, options);
        		imageTakenPicture.setImageBitmap(takenPicture);
        	}
        }
        
        submitButton = (Button) findViewById (R.id.button_cloth_form_submit);
        cancelButton = (Button) findViewById (R.id.button_cloth_form_cancel);
        editButton = (Button) findViewById (R.id.button_cloth_form_edit);
        deleteButton = (Button) findViewById (R.id.button_cloth_form_delete);
        
        setViewMode ();
        
        submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Afficher un Toast
				
				// Retour au menu
				Intent intent = new Intent (ClothFormActivity.this, MainActivity.class);
				startActivity (intent);
			}
		});
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Retour au menu
				Intent intent = new Intent (ClothFormActivity.this, MainActivity.class);
				startActivity (intent);
			}
		});
        
        editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Passage au mode édition
				setEditMode ();
			}
		});
    }
    
    public void updateForm ()
    {
    	switch (mode)
    	{
			case VIEW:
			{
				setViewMode ();
				break;
			}
			case EDIT:
			{
				setEditMode ();
				break;
			}
			default:
				break;
    	}
    }
    
    public int setViewMode ()
    {
    	// Le mode visualisation n'est disponible que lorsqu'un habit est défini.
    	/*if (cloth == null)
    	{
    		return -1;
    	}*/
    	
    	this.mode = e_Mode.VIEW;
    	
    	/* 
    	 * Actualiser les valeurs des champs de manière à ce qu'ils contiennent
    	 * les infos de l'habit
    	 */
    	this.updateFieldsWithClothInfo ();
    	
    	// Désactiver les champs
    	
    	findViewById(R.id.field_name).setEnabled(false);
    	findViewById(R.id.spinner_type).setEnabled(false);
    	findViewById(R.id.spinner_occasion).setEnabled(false);
    	findViewById(R.id.spinner_colors1).setEnabled(false);
    	findViewById(R.id.spinner_colors2).setEnabled(false);
    	findViewById(R.id.spinner_seasons).setEnabled(false);
    	
    	// Changer les boutons "Save" & "Cancel" en "Edit" & "Delete"
    	submitButton.setVisibility(View.GONE);
    	editButton.setVisibility(View.VISIBLE);
    	cancelButton.setVisibility(View.GONE);
    	deleteButton.setVisibility(View.VISIBLE);
    	
    	return 0;
    }
    
    public int setEditMode ()
    {
    	// Le mode édition n'est disponible que lorsqu'un habit est défini.
    	/*if (cloth == null)
    	{
    		return -1;
    	}*/
    	
    	this.mode = e_Mode.EDIT;
    	
    	// Activer les champs
    	
    	findViewById(R.id.field_name).setEnabled(true);
    	findViewById(R.id.spinner_type).setEnabled(true);
    	findViewById(R.id.spinner_occasion).setEnabled(true);
    	findViewById(R.id.spinner_colors1).setEnabled(true);
    	findViewById(R.id.spinner_colors2).setEnabled(true);
    	findViewById(R.id.spinner_seasons).setEnabled(true);
    	
    	// Changer les boutons "Edit" & "Delete" en "Save" & "Cancel"
    	submitButton.setVisibility(View.VISIBLE);
    	editButton.setVisibility(View.GONE);
    	cancelButton.setVisibility(View.VISIBLE);
    	deleteButton.setVisibility(View.GONE);
    	
    	return 0;
    }
    
    public void updateFieldsWithClothInfo ()
    {
    	if (cloth == null)
    		return;
    	
    	
    }
	
}
