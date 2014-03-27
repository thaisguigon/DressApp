package com.dressapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
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
	
	private enum e_SendDataMode
	{
		POST,
		PUT
	}

	/* ----- STANDARD REQUESTS ----- */
	
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
	
	private static boolean sendData (String url_str, JSONObject dataJSON, e_SendDataMode mode) throws ClientProtocolException, IOException
	{
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;
		HttpUriRequest req;
		StringEntity se;
		boolean result = true;
		String json_str = "";
		
		if (dataJSON != null)
		{
			json_str = dataJSON.toString();
		}
		
		se = new StringEntity(json_str);
		
		switch (mode)
		{
			case PUT :
			{
				req = new HttpPut(url_str);
				((HttpPut) req).setEntity(se);
				break;
			}
			default :
			{
				req = new HttpPost(url_str);
				((HttpPost) req).setEntity(se);
				break;
			}
		}
		        
        se.setContentType("application/json");
		
        req.setHeader("Accept", "application/json");
        req.setHeader("Content-type", "application/json");
        
		response = httpclient.execute(req);
		if (response.getStatusLine().getStatusCode() != 201)
		{
			result = false;
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
		}
		
		httpclient.getConnectionManager().shutdown();
		
		return result;
	}
		
	/* ----- USER ----- */
	
	public static Boolean authenticateUser (String username, String password)
	{
		//JSONObject json = null;
		String url_str = "http://dressapp.alwaysdata.net/user/login/"+username+"/"+password;
		int result = -1;
		boolean boolResult = false;
		
		try {
			result = Integer.parseInt(getURIContent(url_str));
			boolResult = result > 0 ? true : false;
			if (boolResult)
			{
				MainActivity.user.setConnected(true);
				MainActivity.user.setUserId(result);
				MainActivity.user.setUsername(username);
			}
			return boolResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public static Boolean createUser (String username, String email, String password)
	{
		String url_str = "http://dressapp.alwaysdata.net/user/register/"+username+"/"
				+email+"/"+password;
		
		try {
			return Boolean.valueOf(getURIContent(url_str));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/* ----- CLOTHES ----- */
	
	public static Void postOrUpdateClothData (String url_str, Cloth cloth, e_Mode mode)
	{
		if (cloth == null || mode == e_Mode.VIEW)
			return null;
		
		JSONObject json = cloth.toJSON();
		
		try
		{
			switch (mode)
			{
				case EDIT :
				{
					sendData (url_str, json, e_SendDataMode.PUT);
					break;
				}
				default :
				{
					sendData (url_str, json, e_SendDataMode.POST);
					break;
				}
			}
		} catch (Exception e)
		{
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
