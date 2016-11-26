package com.dev9.ensor.repository;

import com.dev9.ensor.model.Recipe;
import generated.dev9.proto.Messages;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecipeRepositoryImpl implements RecipeRepository {

    private final Map<Integer, Messages.Recipe> buffRecipes;
    private final Map<Integer, Recipe> recipes;

    public RecipeRepositoryImpl() {
        buffRecipes = new ConcurrentHashMap<>();
        recipes = new ConcurrentHashMap<>();
    }

    @Override
    public Messages.Recipe getProtoRecipeById(int id) {

        return buffRecipes.get(id);
    }

    @Override
    public Integer save(Messages.Recipe recipe) {
        int size = buffRecipes.size();

        buffRecipes.put(size, recipe);

        return size;
    }

    @Override
    public Recipe getRecipeById(int id) {

        return recipes.get(id);
    }

    @Override
    public Integer save(Recipe recipe) {
        int size = recipes.size();

        recipes.put(size, recipe);

        return size;
    }
}
