package com.dev9.ensor.service;

import com.dev9.ensor.mapper.RecipeProtoMapper;
import com.dev9.ensor.model.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import generated.dev9.proto.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class RecipeService {

    private ObjectMapper mapper;

    @Autowired
    public RecipeService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Messages.Recipe recipeAsProto(Recipe recipe) {
        return RecipeProtoMapper.parseAsProto(recipe);
    }

    public String recipeAsJSON(Recipe recipeJSONForm) {
        try {
            return mapper.writeValueAsString(recipeJSONForm);

        } catch (JsonProcessingException e) {
            throw Throwables.propagate(e);
        }
    }

    public Recipe getRecipe(String jsonRecipe) {
        try {
            return mapper.readValue(jsonRecipe.getBytes(), Recipe.class);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public Recipe getRecipe(byte[] protoRecipe) {
        return RecipeProtoMapper.getRecipe(protoRecipe);
    }

    public void writeRecipeToFile(String filename, Messages.Recipe recipe) {
        try (FileOutputStream output = new FileOutputStream(filename)) {

            Messages.Recipe.Builder builder = Messages.Recipe.newBuilder(recipe);
            builder.build().writeTo(output);
            output.close();
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }
}
