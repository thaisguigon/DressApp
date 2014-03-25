package com.dressapp;

import java.sql.Date;
import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

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
	 * Image représentant l'habit
	 */
	private String img;
	
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
	 * @return String
	 * {@link Cloth#img}
	 */
	public String getImg ()
	{
		return img;
	}

	/**
	 * @param String
	 * {@link Cloth#img}
	 */
	public void setImg (String img)
	{
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
	public void edit (String img, String name, String color1, String color2,
			String occasion, String season, String category)
	{
		this.setImg(img);
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
			json.put("image", img);
			
			json.put("brand", "");
			json.put("wishlist", false);
			json.put("favori", false);
			json.put("user", "/api/v1/user/1/");
			json.put("slug", "some-slug");
			
			Timestamp time = new Timestamp (0);
			Date date = new Date (time.getTime());
			
			System.out.println(date);
			
			json.put("publication-date", date.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
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
	    b.putString("img", img);
	    b.putString("name", name);
	    b.putString("color1", color1);
	    b.putString("color2", color2);
	    b.putString("occasion", occasion);
	    b.putString("season", season);
	    b.putString("category", category);
	    
	    return b;
	}
	
	/**
	 * Retransformation d'un bundle en habit.
	 * @param Bundle Bundle contenant les données d'un habit.
	 */
	public void fromBundle (Bundle b)
	{
	    id = b.getInt("id");
	    img = b.getString("img");
	    name = b.getString("name");
	    color1 = b.getString("color1");
	    color2 = b.getString("color2");
	    occasion = b.getString("occasion");
	    season = b.getString("season");
	    category = b.getString("category");
	}
}
