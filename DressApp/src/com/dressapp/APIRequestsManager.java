package com.dressapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.dressapp.ClothFormActivity.e_Mode;

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
	
	public static Void postOrUpdateClothData (String url_str, Cloth cloth, e_Mode mode)
	{
		if (cloth == null || mode == e_Mode.VIEW)
			return null;
		
		JSONObject json = cloth.toJSON();
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpUriRequest req;
		StringEntity se;
		
		try {
			se = new StringEntity(json.toString());
			
			if (mode == e_Mode.SAVE)
			{
		        req = new HttpPost(url_str);
		        ((HttpPost) req).setEntity(se);
			}
			else
			{
				req = new HttpPut(url_str);
				((HttpPut) req).setEntity(se);
			}
			
			se.setContentType("application/json");
			
	        req.setHeader("Accept", "application/json");
	        req.setHeader("Content-type", "application/json");
	        
			response = httpclient.execute(req);
			if ((mode == e_Mode.SAVE && response.getStatusLine().getStatusCode() != 201) ||
					(mode == e_Mode.EDIT && response.getStatusLine().getStatusCode() != 204))
			{
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			
			
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static Void deleteClothData (String url_str)
	{		
		HttpClient httpclient;
		HttpResponse response;
		HttpDelete req;
		StringEntity se;
		
		try {
			httpclient = new DefaultHttpClient();
			req = new HttpDelete(url_str);
			se = new StringEntity("");
			
			se.setContentType("application/json");
			
	        req.setHeader("Accept", "application/json");
	        req.setHeader("Content-type", "application/json");
	        
			response = httpclient.execute(req);
			if (response.getStatusLine().getStatusCode() != 204) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}
			
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
