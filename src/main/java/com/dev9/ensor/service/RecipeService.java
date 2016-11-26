package com.dev9.ensor.service;

import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import generated.dev9.proto.Messages;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RecipeService {

    private ObjectMapper mapper;

    public RecipeService() {
        mapper = new ObjectMapper();
    }

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

    public Recipe getRecipe(byte[] byteRecipe) {
        Recipe recipe;

        try {
            Messages.Recipe protoRecipe = Messages.Recipe.parseFrom(byteRecipe);

            recipe = new Recipe(protoRecipe.getName(), protoRecipe.getDescription());

            List<Messages.IngredientUsed> ingredientsList = protoRecipe.getIngredientsList();
            ImmutableList.Builder<IngredientUsed> builder = ImmutableList.builder();
            for (Messages.IngredientUsed ingredientUsed : ingredientsList) {
                Ingredient ingredient = getIngredientFromProtoIngredient(ingredientUsed.getIngredient());
                MeasurementType type = getMeasurementTypeFromProto(ingredientUsed.getType());

                builder.add(new IngredientUsed(ingredient, type, ingredientUsed.getQuantity()));
            }

            recipe.addItems(builder.build());

        } catch (InvalidProtocolBufferException e) {
            throw Throwables.propagate(e);
        }

        return recipe;
    }

    private MeasurementType getMeasurementTypeFromProto(Messages.MeasurementType type) {
        switch (type) {
            case OUNCE:
                return MeasurementType.OUNCE;
            case GALLON:
                return MeasurementType.GALLON;
            case GRAMS:
                return MeasurementType.GRAMS;
            case CUP:
                return MeasurementType.CUP;
            case TABLESPOON:
                return MeasurementType.TABLESPOON;
            case TEASPOON:
                return MeasurementType.TEASPOON;
            case POUND:
                return MeasurementType.POUND;
            case ITEM:
                return MeasurementType.ITEM;
            default:
                throw new RuntimeException("Cannot figure out the type: " + type.toString());
        }
    }


    private Ingredient getIngredientFromProtoIngredient(Messages.Ingredient in) {
        return new Ingredient(in.getName(), in.getDescription());
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
