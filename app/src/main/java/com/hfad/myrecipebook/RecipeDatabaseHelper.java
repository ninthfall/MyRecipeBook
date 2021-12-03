package com.hfad.myrecipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "recipe";
    private static final int DB_VERSION = 1;

    RecipeDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, DB_VERSION);
    }

    private static void insertRecipe(SQLiteDatabase db, int categoryId, String name, String ingredients, String directions){
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("CATEGORY_ID", categoryId);
        recipeValues.put("RECIPE_NAME", name);
        recipeValues.put("INGREDIENTS", ingredients);
        recipeValues.put("DIRECTIONS", directions);
        db.insert("RECIPE", null, recipeValues);
    }

    private static void insertCategory(SQLiteDatabase db, String categoryName){
        ContentValues categoryValues = new ContentValues();
        categoryValues.put("CATEGORY_NAME", categoryName);
        db.insert("CATEGORY", null, categoryValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE RECIPE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CATEGORY_ID INTEGER,"
                    + "RECIPE_NAME TEXT, "
                    + "INGREDIENTS TEXT,"
                    + "DIRECTIONS TEXT, "
                    + "FOREIGN KEY(CATEGORY_ID) REFERENCES CATEGORY(id));");

            db.execSQL("CREATE TABLE CATEGORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CATEGORY_NAME TEXT);");

            insertCategory(db, "Soup");
            insertCategory(db, "Breakfast");
            insertCategory(db, "Dessert");

            insertRecipe(db, 3, "Bread Pudding", "6 slices bread, 2 tablespoons butter, 4 eggs, 2 cups milk, 3/4 cup sugar, 1 teaspoon cinnamon, 1 teaspoon vanilla extract", "Preheat oven to 350 degrees F (175 degrees C). Break bread into small pieces into an 8 inch square baking pan. Drizzle melted butter or margarine over bread. In a medium mixing bowl, combine eggs, milk, sugar, cinnamon, and vanilla. Beat until well mixed. Pour over bread, and lightly push down with a fork until bread is covered and soaking up the egg mixture. Bake in the preheated oven for 45 minutes, or until the top springs back when lightly tapped. ");
            insertRecipe(db, 3, "Vanilla Pudding", "2 cups milk, 1/2 cup sugar, 3 tablespoons cornstarch, 1/4 teaspoon salt, 1 teaspoon vanilla extract, 1 tablespoon butter", "In medium saucepan over medium heat, heat milk until bubbles form at edges. In a bowl, combine sugar, cornstarch and salt. Pour into hot milk, a little at a time, stirring to dissolve. Continue to cook and stir until mixture thickens enough to coat the back of a metal spoon. Do not boil. Remove from heat, stir in vanilla and butter. Pour into serving dishes. Chill before serving.");
            insertRecipe(db, 2, "Old Fashioned Pancake", "1 1/2 cup flour, 3 1/2 teaspoons baking powder, 1 teaspoon salt, 1 1/4 cups milk, 1 egg, 3 tablespoons butter", "In a large bowl, sift together the flour, baking powder, salt and sugar. Make a well in the center and pour in the milk, egg and melted butter; mix until smooth. Heat a lightly oiled griddle or frying pan over medium-high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake. Brown on both sides and serve hot.");
            insertRecipe(db, 1, "Miso Soup", "1 tablespoon wakame, 4 cups water, 2 teaspoons dashi  granules, 3 tablespoons miso paste, 4 ounces tofu, 2 green onions", "Place wakame in a fine-mesh sieve and soak in some cold water for 10 minutes. Combine 4 cups water and dashi granules in a saucepan and bring to a boil over medium heat. Add miso paste and whisk to dissolve. Add wakame and simmer for 3 minutes. Divide tofu between 4 serving bowls. Ladle miso soup on top and garnish with green onions. ");
        }
    }

}
