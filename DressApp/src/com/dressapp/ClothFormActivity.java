package com.dressapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ClothFormActivity extends Activity {

	public enum e_Mode
	{
		VIEW,
		EDIT,
		SAVE;
	}
	
	private e_Mode mode = e_Mode.SAVE;
	private Bitmap takenPicture;
	private Cloth cloth = null;
	private Button submitButton, cancelButton, editButton, deleteButton;
	private ImageView imageViewPicture;
	private TextView fieldName;
	private Spinner spinnerType, spinnerOccasion, spinnerColors1, spinnerColors2,
		spinnerSeasons;
	private byte[] picture;
	int userId = -1;
	
	private class PostOrUpdateDataTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... url_str) {
			APIRequestsManager.postOrUpdateClothData(url_str[0], cloth, mode);
			return null;
		}
		
		@Override
		protected void onPostExecute (Void result)
		{
			String message = "Cloth successfully created !";
			
			if (mode == e_Mode.EDIT)
			{
				message = "Cloth successfully updated !";
			}
			
			Toast toast = Toast.makeText(getApplicationContext(), message,
					Toast.LENGTH_SHORT);
			toast.show();
			setViewMode();
		}
	}
	
	private class DeleteDataTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... url_str) {
			APIRequestsManager.deleteClothData(url_str[0]);
			return null;
		}
		
		@Override
		protected void onPostExecute (Void result)
		{
			// Toast
			Toast toast = Toast.makeText(getApplicationContext(), "Cloth successfully deleted !",
					Toast.LENGTH_SHORT);
			toast.show();
			
			// Retour au menu
			Intent intent = new Intent (ClothFormActivity.this, MainActivity.class);
			startActivity (intent);
		}
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloth_saving_form);
        
        // Récupération des données envoyées par les pages précédentes
        Bundle extra = getIntent().getExtras();
        
        if (extra == null)
        {
        	Toast toast = Toast.makeText(getApplicationContext(), "You were logged out, please wait.",
					Toast.LENGTH_SHORT);
			toast.show();
        	Intent intent = new Intent (ClothFormActivity.this, LoginFormActivity.class);
			startActivity (intent);
        }
        
        if (extra != null)
        {
        	//userId = extra.getInt("userId", 0);
        	if (extra.get("mode") != null && extra.get("mode") instanceof e_Mode)
        		mode = (e_Mode) extra.get("mode");
        	
    		switch (mode)
    		{
    			case SAVE :
    			{
		        	// Récupération de la photo prise
		        	picture = (byte[]) extra.get("bitmapPicture");
		        	break;
    			}
    			case VIEW :
    			{
    				// Récupération de l'habit visualisé
    				cloth = new Cloth ();
    				cloth.fromBundle((Bundle) extra.get("cloth"));
    				picture = cloth.getImg();
    				
    				break;
    			}
				default:
					break;
    		}
        }
        
        if (picture != null)
    	{
        	System.out.println(picture.length);
    		imageViewPicture = (ImageView) findViewById (R.id.imageView_taken_picture);
    		BitmapFactory.Options options = new BitmapFactory.Options();
	        takenPicture = BitmapFactory.decodeByteArray(picture, 0, picture.length, options);
    		imageViewPicture.setImageBitmap(takenPicture);
    	}
        
        submitButton = (Button) findViewById (R.id.button_cloth_form_submit);
        cancelButton = (Button) findViewById (R.id.button_cloth_form_cancel);
        editButton = (Button) findViewById (R.id.button_cloth_form_edit);
        deleteButton = (Button) findViewById (R.id.button_cloth_form_delete);
        
        fieldName = (TextView) findViewById(R.id.field_name);
    	spinnerType = (Spinner) findViewById(R.id.spinner_type);
    	spinnerOccasion = (Spinner) findViewById(R.id.spinner_occasion);
    	spinnerColors1 = (Spinner)findViewById(R.id.spinner_colors1);
    	spinnerColors2 = (Spinner) findViewById(R.id.spinner_colors2);
    	spinnerSeasons = (Spinner) findViewById(R.id.spinner_seasons);
        
        updateForm ();
        
        submitButton.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				String url_str = "http://dressapp.alwaysdata.net/api/v1/clothes/";
				
				if (cloth == null)
				{
					cloth = new Cloth ();
				}
				
				cloth.edit (
						// Name
						fieldName.getText().toString(),
						// Color 1
						spinnerColors1.getSelectedItem().toString(),
						// Color 2
						spinnerColors2.getSelectedItem().toString(),
						// Occasion
						spinnerOccasion.getSelectedItem().toString(),
						// Season
						spinnerSeasons.getSelectedItem().toString(),
						// Category
						spinnerType.getSelectedItem().toString());
				
				if (picture.length > 0)
				{
					cloth.setImg(picture);
				}
				
				cloth.setUserId(userId);
				
				if (mode == e_Mode.EDIT)
					url_str += Integer.toString(cloth.getId()) + "/";
					
				new PostOrUpdateDataTask().execute(url_str);
			}
		});
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mode == e_Mode.EDIT)
				{
					// Retour sur le mode visualisation
					setViewMode ();
				}
				else
				{
					// Retour au menu
					Intent intent = new Intent (ClothFormActivity.this, MainActivity.class);
					startActivity (intent);
				}
			}
		});
        
        editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Passage au mode édition
				setEditMode ();
			}
		});
        
        deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cloth == null)
					return;
				
				String url_str = "http://dressapp.alwaysdata.net/api/v1/clothes/" +
						Integer.toString(cloth.getId()) + "/";
				
				new DeleteDataTask().execute(url_str);
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
				updateFieldsWithClothInfo();
				setEditMode ();
				break;
			}
			default:
			{
				break;
			}
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
    	
    	fieldName.setEnabled(false);
    	spinnerType.setEnabled(false);
    	spinnerOccasion.setEnabled(false);
    	spinnerColors1.setEnabled(false);
    	spinnerColors2.setEnabled(false);
    	spinnerSeasons.setEnabled(false);
    	
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
    	
    	fieldName.setEnabled(true);
    	spinnerType.setEnabled(true);
    	spinnerOccasion.setEnabled(true);
    	spinnerColors1.setEnabled(true);
    	spinnerColors2.setEnabled(true);
    	spinnerSeasons.setEnabled(true);
    	
    	// Changer les boutons "Edit" & "Delete" en "Save" & "Cancel"
    	submitButton.setVisibility(View.VISIBLE);
    	editButton.setVisibility(View.GONE);
    	cancelButton.setVisibility(View.VISIBLE);
    	deleteButton.setVisibility(View.GONE);
    	
    	return 0;
    }
    
    /// TODO - A FAIRE
    public void updateFieldsWithClothInfo ()
    {
    	if (cloth == null)
    		return;
    	
    	if (!(fieldName.getText().toString()).equals(cloth.getName()))
    		fieldName.append(cloth.getName());
    	
		updateSpinnersToValue(spinnerType, cloth.getCategory());
		updateSpinnersToValue(spinnerOccasion, cloth.getOccasion());
		updateSpinnersToValue(spinnerColors1, cloth.getColor1());
		updateSpinnersToValue(spinnerColors2, cloth.getColor2());
		updateSpinnersToValue(spinnerSeasons, cloth.getSeason());
    }
    
    public void updateSpinnersToValue (Spinner spinner, String value)
    {
    	if (spinner.getSelectedItem().toString().equals(value))
    		return;
    	
    	ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
    	int spinnerPosition = adapter.getPosition(value);
    	spinner.setSelection(spinnerPosition);
    }
	
}
