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

}
