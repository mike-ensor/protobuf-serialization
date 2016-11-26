package com.dev9.ensor.mapper;

import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.InvalidProtocolBufferException;
import generated.dev9.proto.Messages;

import java.util.List;

public class RecipeProtoMapper {

    /**
     * Takes in a byte array (Protobuf message) and converts to a Recipe object
     *
     * @param byteRecipe byte[]
     * @return {@link Recipe}
     */
    public static Recipe getRecipe(byte[] byteRecipe) {
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

    public static Messages.Recipe parseAsProto(Recipe recipe) {
        Messages.Recipe.Builder recipeBuilder = Messages.Recipe.newBuilder();
        recipeBuilder.setName(recipe.getName());
        recipeBuilder.setDescription(recipe.getDescription());

        List<IngredientUsed> ingredientsWithQuantity = recipe.getIngredientsWithQuantity();

        for (IngredientUsed ingredientUsed : ingredientsWithQuantity) {
            Messages.IngredientUsed.Builder usedIngredientBuilder = Messages.IngredientUsed.newBuilder();

            usedIngredientBuilder.setIngredient(getIngredientBuilder(ingredientUsed));
            usedIngredientBuilder.setQuantity(ingredientUsed.getQuantity());

            usedIngredientBuilder.setType(Messages.MeasurementType.valueOf(ingredientUsed.getType().name()));

            recipeBuilder.addIngredients(usedIngredientBuilder);
        }

        return recipeBuilder.build();
    }


    private static MeasurementType getMeasurementTypeFromProto(Messages.MeasurementType type) {
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

    private static Ingredient getIngredientFromProtoIngredient(Messages.Ingredient in) {
        return new Ingredient(in.getName(), in.getDescription());
    }

    private static Messages.Ingredient.Builder getIngredientBuilder(IngredientUsed ingredientUsed) {
        Messages.Ingredient.Builder ingredientBuilder = Messages.Ingredient.newBuilder();
        ingredientBuilder.setDescription(ingredientUsed.getIngredient().getDescription());
        ingredientBuilder.setName(ingredientUsed.getIngredient().getName());
        return ingredientBuilder;
    }

}
