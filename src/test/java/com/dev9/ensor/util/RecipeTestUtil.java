package com.dev9.ensor.util;

import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.Recipe;

import java.util.List;

public class RecipeTestUtil {

    public static Recipe createRecipe(String name, String description, List<IngredientUsed> ingredients) {
        Recipe recipe = new Recipe(name, description);

        if (!ingredients.isEmpty()) {
            recipe.addItems(ingredients);
        }

        return recipe;
    }


    public static String getMockRecipeJSONString(String description) {
        return "{\"name\":\"My Recipe\",\"description\":\"" + description + "\",\"ingredientsWithQuantity\":[{\"type\":\"ITEM\",\"ingredient\":{\"name\":\"Jalepeno\",\"description\":\"Spicy Pepper\"},\"quantity\":1},{\"type\":\"OUNCE\",\"ingredient\":{\"name\":\"Cheese\",\"description\":\"Creamy Cheese\"},\"quantity\":4}]}";
    }
}
