package com.dressapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.os.AsyncTask;

public class ReadURLTask extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... url_str)
	{
			URL url = null;
			BufferedReader in = null;
			String result = "", inputLine;

			try {
				url = new URL(url_str[0]);
				in = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((inputLine = in.readLine()) != null)
				{
					System.out.println(inputLine);
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return result;
	}

}
