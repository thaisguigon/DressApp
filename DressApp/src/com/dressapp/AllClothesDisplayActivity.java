package com.dressapp;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AllClothesDisplayActivity extends Activity {
	
	private JSONObject json;
	
	private String jsonString = "{'objects':[{'id':1,'image':'','name':'Ballerinesrayees','color':'Yellow','season':'Fall','style':'Party','material':'Shoes'}, {'id':2,'image':'','name':'Ballerinespasrayees','color':'Red','season':'Summer','style':'Formal','material':'Shoes'}]}";

	private ArrayList<Cloth> clothes = new ArrayList<Cloth>();
	
	private TableLayout tableContainer;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.all_clothes_display);
        
        try {
			json = new JSONObject (jsonString);
			clothes = JSONParser.parseClothes(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        if (!clothes.isEmpty())
        {
        	tableContainer = (TableLayout) findViewById(R.id.tableContainer);
        	int i = 0;
        	
        	for (final Cloth currentCloth : clothes)
        	{
        		final TableRow row = new TableRow(getApplicationContext());
        		
        		if (i%2 != 0)
        		{
        			row.setBackgroundColor(Color.LTGRAY);
        		}
                row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,1));
        		
        		TextView labelImage = new TextView(getApplicationContext());
        		TextView labelName = new TextView(getApplicationContext());
        		
        		labelImage.setText(currentCloth.getColor1());
        		labelName.setText(currentCloth.getName());
        		
        		labelImage.setPadding(30, 5, 30, 5);
        		labelName.setPadding(20, 20, 20, 20);
        		
        		labelImage.setTextColor(Color.BLACK);
        		labelName.setTextColor(Color.BLACK);
        		
        		row.addView(labelImage);
        		row.addView(labelName);
        		
        		row.setOnClickListener(new View.OnClickListener() {
        			@Override
        			public void onClick(View v) {
        				row.setBackgroundColor(Color.GRAY);
        				
        				// Retour au menu
        				Intent intent = new Intent (AllClothesDisplayActivity.this, ClothFormActivity.class);
        				intent.putExtra("mode", ClothFormActivity.e_Mode.VIEW);
        				intent.putExtra("cloth", currentCloth.toBundle());
        				startActivity (intent);
        			}
        		});
        		
        		tableContainer.addView(row);
        		
        		++i;
        	}
        }
	}
	
}
