package com.dressapp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Base64;

/**
 * Stocke les données concernant un habit.
 */
public class Cloth {
	
	/**
	 * Identifiant de l'habit en base de données.
	 */
	private int
		id = -1;
	
	/**
	 * Première couleur de l'habit
	 */
	private String color1;
	
	/**
	 * Deuxième couleur de l'habit
	 */
	private String color2;
	
	/**
	 * Image représentant l'habit.
	 */
	private byte[] img;

	/**
	 * Nom donné par l'utilisateur à l'habit
	 */
	private String name;
	
	/**
	 * Occasion pour laquelle l'habit se porte
	 */
	private String occasion;
	
	/**
	 * Saison pendant laquelle l'habit se porte
	 */
	private String season;

	/**
	 * Type (Catégorie) d'habit
	 */
	private String category;
	
	/* ----- CONSTRUCTOR ----- */
	
	public Cloth ()
	{
		super ();
	}
	
	/* ----- GETTERS AND SETTERS ----- */
	
	/**
	 * @return String
	 * {@link Cloth#color1}
	 */
	public String getColor1 ()
	{
		return color1;
	}

	/**
	 * @param String
	 * {@link Cloth#color1}
	 */
	public void setColor1 (String color1)
	{
		this.color1 = color1;
	}
	
	/**
	 * @return String
	 * {@link Cloth#color2}
	 */
	public String getColor2 ()
	{
		return color2;
	}

	/**
	 * @param String
	 * {@link Cloth#color2}
	 */
	public void setColor2 (String color2)
	{
		this.color2 = color2;
	}

	/**
	 * @return Int
	 * {@link Cloth#id}
	 */
	public int getId ()
	{
		return id;
	}

	/**
	 * @param Int
	 * {@link Cloth#id}
	 */
	public void setId (int id)
	{
		this.id = id;
	}
	
	/**
	 * @return byte[]
	 * {@link Cloth#img}
	 */
	public byte[] getImg() {
		return img;
	}

	/**
	 * @param byte[]
	 * {@link Cloth#img}
	 */
	public void setImg(byte[] img) {
		this.img = img;
	}

	/**
	 * @return String
	 * {@link Cloth#name}
	 */
	public String getName ()
	{
		return name;
	}
	
	/**
	 * @param String
	 * {@link Cloth#name}
	 */
	public void setName (String name)
	{
		this.name = name;
	}
	
	/**
	 * @return String
	 * {@link Cloth#occasion}
	 */
	public String getOccasion ()
	{
		return occasion;
	}

	/**
	 * @param String
	 * {@link Cloth#occasion}
	 */
	public void setOccasion (String occasion)
	{
		this.occasion = occasion;
	}

	/**
	 * @return String
	 * {@link Cloth#season}
	 */
	public String getSeason ()
	{
		return season;
	}

	/**
	 * @param String
	 * {@link Cloth#season}
	 */
	public void setSeason (String season)
	{
		this.season = season;
	}

	/**
	 * @return String
	 * {@link Cloth#category}
	 */
	public String getCategory ()
	{
		return category;
	}

	/**
	 * @param String
	 * {@link Cloth#category}
	 */
	public void setCategory (String type)
	{
		this.category = type;
	}
	
	/* ----- METHODS ----- */

	/**
	 * Change les informations du vêtement.
	 * 
	 * @param img
	 * @param name
	 * @param color1
	 * @param color2
	 * @param occasion
	 * @param season
	 * @param type
	 */
	public void edit (String name, String color1, String color2,
			String occasion, String season, String category)
	{
		this.setName(name);
		this.setColor1(color1);
		this.setColor2(color2);
		this.setOccasion(occasion);
		this.setSeason(season);
		this.setCategory(category);
	}
	
	public JSONObject toJSON ()
	{
		JSONObject json = new JSONObject ();
		
		try {			
			json.put("style", occasion);
			json.put("name", name);
			json.put("color1", color1);
			json.put("color2", color2);
			json.put("season", season);
			json.put("category", category);
			//json.put("image", img_str);
			
			json.put("brand", "");
			json.put("wishlist", false);
			json.put("favori", false);
			json.put("user", MainActivity.user.getUserId());
			json.put("slug", slugify());
			
			Timestamp time = new Timestamp (0);
			Date date = new Date (time.getTime());
			
			json.put("publication-date", date.toString());
			
			if (img.length > 0)
			{
				String encodedImg = Base64.encodeToString(img, Base64.DEFAULT);
				json.put("image", encodedImg);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * Crée un slug pour l'habit à partir de son nom.
	 * @return String Le slug de l'habit, composé de caractères minuscules et de tirets (-).
	 */
	public String slugify ()
	{
		return name.toLowerCase(Locale.FRANCE).replaceAll("[^a-z0-9-]", "-");
	}
	
	/* ----- BUNDLE ----- */
	
	/**
	 * Conversion de l'habit en Bundle afin de mieux le passer en tant que donnée entre les
	 * différentes activités.
	 * @return Bundle Données de l'habit mises en place dans le Bundle
	 */
	public Bundle toBundle ()
	{
		Bundle b = new Bundle();
	    b.putInt("id", id);
	    b.putString("name", name);
	    b.putString("color1", color1);
	    b.putString("color2", color2);
	    b.putString("occasion", occasion);
	    b.putString("season", season);
	    b.putString("category", category);
	    b.putByteArray("img", img);
	    
	    return b;
	}
	
	/**
	 * Retransformation d'un bundle en habit.
	 * @param Bundle Bundle contenant les données d'un habit.
	 */
	public void fromBundle (Bundle b)
	{
	    id = b.getInt("id");
	    name = b.getString("name");
	    color1 = b.getString("color1");
	    color2 = b.getString("color2");
	    occasion = b.getString("occasion");
	    season = b.getString("season");
	    category = b.getString("category");
	    img = b.getByteArray("img");
	}
}
