package com.hfad.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewRecipeActivity extends AppCompatActivity {

    private String recipe, ingredients, directions;
    private SQLiteDatabase db;
    private Cursor cursor;
    Button addRecipe;
    private String catName = MainActivity.cName;
    private int cID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        EditText recipeText = findViewById(R.id.recipeText);
        recipe = recipeText.getText().toString();
        EditText ingredientText = findViewById(R.id.ingredientText);
        ingredients = ingredientText.getText().toString();
        EditText directionText = findViewById(R.id.directionText);
        directions = directionText.getText().toString();

        final SQLiteOpenHelper recipeDatabaseHelper = new RecipeDatabaseHelper(this);
        try {
            db = recipeDatabaseHelper.getReadableDatabase();
            cursor = db.query("CATEGORY",
                    new String[] {"_id", "CATEGORY_NAME"},
                    "CATEGORY_NAME = ?", new String[]{catName}, null, null, null);
            if(cursor.moveToFirst()) {
                cID = cursor.getInt(0);
            }
        } catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG);
            toast.show();
        }

        addRecipe =  findViewById(R.id.addrecipebtn);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db = recipeDatabaseHelper.getWritableDatabase();
                    ContentValues recipeValues = new ContentValues();
                    recipeValues.put("CATEGORY_ID", cID);
                    recipeValues.put("RECIPE_NAME", recipe);
                    recipeValues.put("INGREDIENTS", ingredients);
                    recipeValues.put("DIRECTIONS", directions);
                    db.insert("RECIPE", null, recipeValues);
                } catch (SQLiteException e){
                    Toast.makeText(NewRecipeActivity.this, "Database unavailable", Toast.LENGTH_SHORT).show();
                }
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