package com.dressapp;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Cette activité permet l'affichage de la caméra et la prise de photo.
 */
public class CameraPreviewActivity extends Activity implements SurfaceHolder.Callback {
	
	/**
	 * Définit si l'on est en mode prévisualisation ou non.
	 */
	private Boolean isPreview;
	
	/**
	 * Bouton clickable pour prendre une photo.
	 */
	private Button buttonTakePicture;
	
	/**
	 * Caméra utilisée pour prendre la photo.
	 */
	private Camera camera;
	
	/**
	 * Surface sur laquelle est affichée la prévisualisation de la caméra.
	 */
	private SurfaceView surfaceCamera;
	
	/**
	 * Ecouteur de click : prend une photo lorsque l'on clique sur l'élément.
	 */
	private View.OnClickListener takePictureListener = new View.OnClickListener ()
    {
    	/**
    	 * Callback pour prise de photo.
    	 */
	    Camera.PictureCallback pictureCallback = new Camera.PictureCallback ()
	    {
	    	/**
	    	 * Actions à exécuter dès que la photo a été prise.
	    	 * @param byte[] Données de la photo prise (données d'image).
	    	 */
	    	public void onPictureTaken (byte[] data, Camera camera)
	    	{
				if (data != null)
				{					
				    /*
				     * On initialise un nouvel intent : une fois la photo prise,
				     * on est dirigé vers le formulaire de sauvegarde d'habit.	        
				     */
			        Intent intent = new Intent (CameraPreviewActivity.this, ClothFormActivity.class);
			        
			        /**
			         * On passe des données à la prochaine activité :
			         * - Le mode : en mode SAVE car le formulaire de sauvegarde d'habit
			         * doit s'afficher.
			         * @see {@link ClothForm#e_Mode}
			         * - Les données de l'image.
			         */
			        intent.putExtra("mode", ClothFormActivity.e_Mode.SAVE);
			        intent.putExtra("bitmapPicture", data);
					startActivity (intent);
				}
	    	}
	    	
	    };
	    
        public void onClick(View v)
        {
            // Prendre une photo
            if (camera != null)
            {
            	// Dès que l'image est prête, on appelle le callback.
            	camera.takePicture (null, null, null, pictureCallback);
            }
        }
    };
	
    /**
     * Actions à exécuter dès la création de l'activité.
     */
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
	    super.onCreate (savedInstanceState);
	    
	    if (MainActivity.user == null || !MainActivity.user.isConnected())
        {
        	// User déconnecté : on le renvoie au formulaire de connexion.
        	Intent intent = new Intent (CameraPreviewActivity.this, LoginFormActivity.class);
			startActivity (intent);
        }

	    // Définition des paramètres de la fenêtre.
	    getWindow().setFormat (PixelFormat.TRANSLUCENT);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    // On n'est pas encore en mode prévisualisation.
	    isPreview = false;
	    
	    // On associe la vue correspondante à l'activité.
	    setContentView (R.layout.camera_display);

	    // Récupération de la surface qui affichera la caméra.
	    surfaceCamera = (SurfaceView) findViewById (R.id.surfaceViewCamera);
	    
	    // Récupération du bouton "take a picture"
	    buttonTakePicture = (Button) findViewById (R.id.buttonTakeAPicture);
	    
	    // Initialisation de la caméra.
	    surfaceCamera.getHolder().addCallback (this);
        
	    /*
	     * Ajout du listener sur la surface et sur le bouton :
	     * on pourra prendre des photos en cliquant n'importe où sur l'écran.
	     */
        surfaceCamera.setOnClickListener(takePictureListener);
        buttonTakePicture.setOnClickListener(takePictureListener);
        
	}

	/**
	 * Définit les actions lorsque l'utilisateur reprend l'activité depuis un état "en pause".
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	public void onResume ()
	{
	    super.onResume();

	    if (MainActivity.user == null || !MainActivity.user.isConnected())
        {
        	// User déconnecté : on le renvoie au formulaire de connexion.
        	Intent intent = new Intent (CameraPreviewActivity.this, LoginFormActivity.class);
			startActivity (intent);
        }
	    
	    // On ouvre à nouveau la caméra.
	    camera = Camera.open();
	}
	
	/**
	 * Définit les actions à exécuter lorsque l'application est mise en pause.
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause ()
	{
	    super.onPause();

	    // Si la caméra existe,
	    if (camera != null)
	    {
	    	// On stoppe la caméra.
	        camera.release();
	        camera = null;
	    }
	}
	
	/**
	 * Indique ce qui se passe lorsque la surface d'affichage est modifiée.
	 */
	@Override
	public void surfaceChanged (SurfaceHolder holder, int format, int width, int height)
	{
		if (camera == null)
		{
			return;
		}
		
		// On arrête la prévisualisation si elle était en cours.
	    if (isPreview)
	    {
	        camera.stopPreview ();
	        
	        // L'état de prévisualisation est enlevé.
	        isPreview = false;
	    }
	    
	    // Récupération des paramètres actuels
	    Camera.Parameters parameters = camera.getParameters ();
	    
	    if (parameters != null)
	    {
	    	// Mise à jour de la taille
		    parameters.setPreviewSize (width, height);

		    // Mise à jour des paramètres
		    camera.setParameters (parameters);
	    }

	    try
	    {
	        // La prévisualisation est remise en place ;
	    	// On la rattache à la surface.
	        camera.setPreviewDisplay (surfaceCamera.getHolder());
	    } catch (IOException e)
	    {
	    }

	    // La prévisualisation est relancée.
	    camera.startPreview ();

	    // L'état de prévisualisation est remis à true.
	    isPreview = true;
	}

	/**
	 * Indique ce qu'il se passe lors de la création de la surface.
	 */
	@Override
	public void surfaceCreated (SurfaceHolder holder)
	{
		// Mise en marche du périphérique
		if (camera == null)
		{
	        camera = Camera.open();
		}
	}

	/**
	 * Indique ce qu'il se passe lorsque la surface est supprimée.
	 */
	@Override
	public void surfaceDestroyed (SurfaceHolder holder)
	{
		// Si la caméra existe,
		if (camera != null)
		{
			// Arrêt de la previsualisation
	        camera.stopPreview();
	        
	        // On signale qu'on n'est plus "en prévisualisation"
	        isPreview = false;
	        
	        // Arrêt du périphérique (caméra)
	        camera.release();
	    }
	}

}
