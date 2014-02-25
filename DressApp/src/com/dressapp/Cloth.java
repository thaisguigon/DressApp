package com.dressapp;

/**
 * @author Thaïs
 * @version Cloth.java, v0.1 2014-01-28
 */

public class Cloth {
	
	private int
		id;
	private String
		img,
		name,
		size,
		brand;
	private float
		price;
	private boolean
		wish = false,
		fav = false;
	
	/* ----- CONSTRUCTOR ----- */
	
	/**
	 * @param id
	 * @param img
	 * @param name
	 * @param size
	 * @param brand
	 * @param price
	 * @param wish
	 * @param fav
	 */
	public Cloth (int id, String img, String name, String size, String brand,
			float price, boolean wish, boolean fav)
	{
		super();
		this.id = id;
		this.img = img;
		this.name = name;
		this.size = size;
		this.brand = brand;
		this.price = price;
		this.wish = wish;
		this.fav = fav;
	}
	
	/* ----- GETTERS AND SETTERS ----- */
	
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

	public String getSize ()
	{
		return size;
	}

	public void setSize (String size)
	{
		this.size = size;
	}

	public String getBrand ()
	{
		return brand;
	}

	public void setBrand (String brand)
	{
		this.brand = brand;
	}

	public float getPrice ()
	{
		return price;
	}

	public void setPrice (float price)
	{
		this.price = price;
	}

	public boolean isWish ()
	{
		return wish;
	}

	public void setWish (boolean wish)
	{
		this.wish = wish;
	}

	public boolean isFav ()
	{
		return fav;
	}

	public void setFav (boolean fav)
	{
		this.fav = fav;
	}
	
	/* ----- METHODS ----- */

	/**
	 * Change les informations du vêtement.
	 * 
	 * @since v0.1 2014-01-28
	 * @param img
	 * @param name
	 * @param size
	 * @param brand
	 * @param price
	 */
	public void edit (String img, String name, String size, String brand, float price)
	{
		this.setImg (img);
		this.setName(name);
		this.setSize(size);
		this.setBrand(brand);
		this.setPrice(price);
	}
	
	/**
	 * Actionne l'appareil photo.
	 * 
	 * @since v0.1 2014-02-25
	 */
	public void takePicture ()
	{
		
	}
	
	/* ----- DATA BASE ----- */
	
	/**
	 * Edite le vêtement dans la base de données.
	 * 
	 * @since v0.1 2014-02-25
	 */
	public void editInDataBase ()
	{
		
	}
	
	/**
	 * Sauvegarde le vêtement dans la base de données.
	 * 
	 * @since v0.1 2014-01-28
	 */
	public void saveInDataBase ()
	{
		
	}
	
	/**
	 * Supprime le vêtement de la base de données.
	 * 
	 * @since v0.1 2014-02-25
	 */
	public void deleteInDataBase ()
	{
		
	}
	
}
