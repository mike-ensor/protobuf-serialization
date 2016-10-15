package com.dev9.ensor.service;

import com.dev9.ensor.model.Item;
import com.dev9.ensor.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RecipeService {

    public Recipe createRecipe(String name, String description, Map<Item, Integer> itemQuantity) {
        Recipe recipe = new Recipe(name, description);
        if(!itemQuantity.isEmpty()) {
            recipe.addItems(itemQuantity);
        }

        return recipe;
    }
}
