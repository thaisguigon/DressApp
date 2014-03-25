package com.dressapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class APIRequestsManager {

	public static String getURIContent (String url_str)
	{
		// Objet URL qui permettra d'accéder au contenu de l'url.
		URL url = null;
		
		// Permet la lecture d'un flux en entrée.
		BufferedReader in = null;
		
		// Chaîne temporaire : contient une ligne du fichier à la fois.
		String inputLine,
		
		// Chaîne obtenue à la fin de la lecture.
			result = "";

		try
		{
			/*
			 * On récupère la chaîne de caractères passée en paramètres (adresse),
			 * et on crée une nouvelle URL avec cette chaîne.
			 */
			url = new URL(url_str);
			
			// Ouverture d'un flux pour lire le contenu de la page
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// On lit ligne par ligne
			while ((inputLine = in.readLine()) != null)
			{
				// On ajoute la ligne courante à la chaîne finale
				result += inputLine;
			}
			
			// Une fois la lecture accomplie, on referme le flux.
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Retour de la chaîne result, remplie avec le contenu de l'URL.
		return result;
	}
	
	public static Void postClothData (String url_str, Cloth cloth)
	{
		if (cloth == null)
			return null;
		
		JSONObject json = cloth.toJSON();
		
		HttpClient httpclient = new DefaultHttpClient();  
        HttpPost httpost = new HttpPost(url_str);
        HttpResponse responsePOST;
        StringEntity se;
        
		try {
			se = new StringEntity(json.toString());
			se.setContentType("application/json");
	        
	        httpost.setEntity(se);
	        httpost.setHeader("Accept", "application/json");
	        httpost.setHeader("Content-type", "application/json");
	        
			responsePOST = httpclient.execute(httpost);
			if (responsePOST.getStatusLine().getStatusCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ responsePOST.getStatusLine().getStatusCode());
			}
			
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
