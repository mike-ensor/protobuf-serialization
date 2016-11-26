package com.dev9.ensor.service;

import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import generated.dev9.proto.Messages;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RecipeService {

    public Messages.Recipe getProtoRecipe(Recipe recipe) {
        Messages.Recipe.Builder recipeBuilder = Messages.Recipe.newBuilder();
        recipeBuilder.setName(recipe.getName());
        recipeBuilder.setDescription(recipe.getDescription());

        List<IngredientUsed> ingredientsWithQuantity = recipe.getIngredientsWithQuantity();

        for (IngredientUsed ingredientUsed : ingredientsWithQuantity) {
            Messages.IngredientUsed.Builder usedIngredientBuilder = Messages.IngredientUsed.newBuilder();

            usedIngredientBuilder.setIngredient(getIngredientBuilder(ingredientUsed));
            usedIngredientBuilder.setQuantity(ingredientUsed.getQuantity());
            // TODO: Get MeasurementType

            recipeBuilder.addIngredients(usedIngredientBuilder);
        }

        return recipeBuilder.build();
    }

    public Recipe recipeAsJSON(String recipeJSONForm) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(recipeJSONForm, Recipe.class);
    }

    public Recipe getRecipeFromJsonString(String jsonRecipe) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(jsonRecipe, Recipe.class);
    }

    private Messages.Ingredient.Builder getIngredientBuilder(IngredientUsed ingredientUsed) {
        Messages.Ingredient.Builder ingredientBuilder = Messages.Ingredient.newBuilder();
        ingredientBuilder.setDescription(ingredientUsed.getIngredient().getDescription());
        ingredientBuilder.setName(ingredientUsed.getIngredient().getName());
        return ingredientBuilder;
    }


    public String getMessage() throws InvalidProtocolBufferException {
        List<Messages.Person.PhoneNumber> phones = ImmutableList.of(Messages.Person.PhoneNumber.newBuilder().setNumber("303-867-5309").setType(Messages.Person.PhoneType.HOME).build());

        Messages.Person person = Messages.Person.newBuilder()
                .setName("Bob")
                .setAge(10)
                .setGender(Messages.Person.Gender.MALE)
                .addAllPhones(phones)
                .build();
        System.out.println("Original=[" + person + "]");

        String jsonView = com.google.protobuf.util.JsonFormat.printer().print(person);

        System.out.println("JSON='" + jsonView + "'");
        Messages.Person.Builder personBuilder = Messages.Person.newBuilder();

        JsonFormat.Parser parser = JsonFormat.parser();
        parser.merge(jsonView, personBuilder);

        Messages.Person person2 = personBuilder.build();
        System.out.println("Deserialized=[" + person2 + "]");
        System.out.println("Original.equals.Deserialized=" + person.equals(person2));

        return "";
    }
}
