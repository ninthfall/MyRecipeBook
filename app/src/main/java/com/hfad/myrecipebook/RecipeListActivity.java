package com.hfad.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;
    private String catName = MainActivity.cName;
    private int cID;
    public static String rName;
    Button addNewRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ListView listRecipe = (ListView) findViewById(R.id.list_recipe);
        SQLiteOpenHelper recipeDatabaseHelper = new RecipeDatabaseHelper(this);

        TextView categoryName = (TextView) findViewById(R.id.categoryName);
        categoryName.setText(catName);

        try{
            db = recipeDatabaseHelper.getReadableDatabase();
            cursor = db.query("CATEGORY",
                    new String[] {"_id", "CATEGORY_NAME"},
                    "CATEGORY_NAME = ?", new String[]{catName}, null, null, null);
            if(cursor.moveToFirst()) {
                cID = cursor.getInt(0);
            }
            String cIDString = Integer.toString(cID);
            cursor = db.query("RECIPE",
                    new String[] {"_id", "RECIPE_NAME"},
                    "CATEGORY_ID = ?", new String[]{cIDString}, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"RECIPE_NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listRecipe.setAdapter(listAdapter);

        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listRecipe, View itemView, int pos, long id) {
                Cursor mycursor = (Cursor) listRecipe.getItemAtPosition(pos);
                rName = mycursor.getString(1);
                Intent intent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                startActivity(intent);
            }
        };

        listRecipe.setOnItemClickListener(itemClickListener);

        addNewRecipe =  findViewById(R.id.addNewRecipe);
        addNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeListActivity.this, NewRecipeActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}