package com.dev9.ensor.repository;

import com.dev9.ensor.model.Recipe;
import generated.dev9.proto.Messages;

public interface RecipeRepository {

    Messages.Recipe getProtoRecipeById(int id);

    Integer save(Messages.Recipe recipe);

    Recipe getRecipeById(int id);

    Integer save(Recipe recipe);
}
