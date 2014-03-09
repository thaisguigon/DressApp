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
 * @author Thaïs
 *
 */
public class CameraPreviewActivity extends Activity implements SurfaceHolder.Callback {
	
	private Camera camera;
	private SurfaceView surfaceCamera;
	private Button buttonTakePicture;
	private Boolean isPreview;
	private View.OnClickListener takePictureListener = new View.OnClickListener ()
    {

    	// Callback pour prise de photo.
	    Camera.PictureCallback pictureCallback = new Camera.PictureCallback ()
	    {

	    	public void onPictureTaken(byte[] data, Camera camera)
	    	{
				if (data != null)
				{					
				    // On récupère les données.			        
			        Intent intent = new Intent (CameraPreviewActivity.this, ClothFormActivity.class);
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
	 * Création de l'activité
	 * 
	 * @since v1.0 2014-02-25
	 */
	@Override
	public void onCreate (Bundle savedInstanceState)
	{
	    super.onCreate (savedInstanceState);

	    getWindow().setFormat (PixelFormat.TRANSLUCENT);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);

	    isPreview = false;
	    
	    setContentView (R.layout.camera_display);

	    // Récupération de la surface.
	    surfaceCamera = (SurfaceView) findViewById (R.id.surfaceViewCamera);
	    
	    // Récupération du bouton "take a picture"
	    buttonTakePicture = (Button) findViewById (R.id.buttonTakeAPicture);
	    
	    // Initialisation de la camera.
	    surfaceCamera.getHolder().addCallback (this);
        
	    // Ajout du listener sur la surface et le bouton
        surfaceCamera.setOnClickListener(takePictureListener);
        buttonTakePicture.setOnClickListener(takePictureListener);
        
	}

	@Override
	public void onResume ()
	{
	    super.onResume();
	    camera = Camera.open();
	}
	
	@Override
	public void onPause ()
	{
	    super.onPause();

	    if (camera != null)
	    {
	        camera.release();
	        camera = null;
	    }
	}
	
	/**
	 * Indique ce qu'il se passe lorsque la surface est modifiée.
	 * 
	 * @since v1.0 2014-02-25
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
	 * 
	 * @since v1.0 2014-02-25
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
	 * 
	 * @since v1.0 2014-02-25
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
