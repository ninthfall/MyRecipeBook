package com.hfad.myrecipebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeActivity extends AppCompatActivity {

    private String reName = RecipeListActivity.rName;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        SQLiteOpenHelper recipeDatebaseHelper = new RecipeDatabaseHelper(this);
        try{
            db = recipeDatebaseHelper.getReadableDatabase();
            cursor = db.query("RECIPE",
                    new String[] {"RECIPE_NAME", "INGREDIENTS", "DIRECTIONS"},
                    "RECIPE_NAME =  ?",
                    new String[] {reName},
                    null, null, null);

            if(cursor.moveToFirst()) {
                String recipeName = cursor.getString(0);
                String ingredients = cursor.getString(1);
                String directions = cursor.getString(2);

                TextView name = (TextView) findViewById(R.id.recipe_name);
                name.setText(recipeName);

                TextView itext = (TextView) findViewById(R.id.ingrdients_text);
                itext.setText(ingredients);

                TextView dtext = (TextView) findViewById(R.id.directions_text);
                dtext.setText(directions);
            }

            cursor.close();
            db.close();

        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}