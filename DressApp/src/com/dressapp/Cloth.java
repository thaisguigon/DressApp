package com.dressapp;

/**
 * @author Thaïs
 */

public class Cloth {
	
	private int
		id;
	private String
		color1,
		color2,
		img,
		name,
		occasion,
		season,
		type;
	
	/* ----- CONSTRUCTOR ----- */
	
	/**
	 * @param id
	 * @param img
	 * @param name
	 */
	public Cloth (int id, String img, String name)
	{
		super();
		this.id = id;
		this.img = img;
		this.name = name;
	}
	
	/* ----- GETTERS AND SETTERS ----- */
	
	public String getColor1 ()
	{
		return color1;
	}

	public void setColor1 (String color1)
	{
		this.color1 = color1;
	}
	
	public String getColor2 ()
	{
		return color2;
	}

	public void setColor2 (String color2)
	{
		this.color2 = color2;
	}

	public int getId ()
	{
		return id;
	}

	public void setId (int id)
	{
		this.id = id;
	}

	public String getImg ()
	{
		return img;
	}

	public void setImg (String img)
	{
		this.img = img;
	}

	public String getName ()
	{
		return name;
	}
	
	public void setName (String name)
	{
		this.name = name;
	}
	
	public String getOccasion ()
	{
		return occasion;
	}

	public void setOccasion (String occasion)
	{
		this.occasion = occasion;
	}

	public String getSeason ()
	{
		return season;
	}

	public void setSeason (String season)
	{
		this.season = season;
	}

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
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
			String occasion, String season, String type)
	{
		this.setImg(img);
		this.setName(name);
		
		this.setColor1(color1);
		this.setColor2(color2);
		this.setOccasion(occasion);
		this.setSeason(season);
		this.setType(type);
	}
	
	/* ----- DATA BASE ----- */
	
	/**
	 * Edite le vêtement dans la base de données.
	 */
	public void editInDataBase ()
	{
		
	}
	
	/**
	 * Sauvegarde le vêtement dans la base de données.
	 */
	public void saveInDataBase ()
	{
		
	}
	
	/**
	 * Supprime le vêtement de la base de données.
	 */
	public void deleteInDataBase ()
	{
		
	}
	
}
