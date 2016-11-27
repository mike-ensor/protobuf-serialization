package com.dev9.ensor.mapper;

import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.util.RecipeTestUtil;
import com.google.common.collect.ImmutableList;
import generated.dev9.proto.Messages;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RecipeProtoMapperTest implements MemoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeProtoMapperTest.class);

    private Recipe recipe;
    private String ingredientName = "Cheese";
    private String recipeName = "My Recipe";
    private int quantity = 4;

    @Before
    public void setup() {

        IngredientUsed cheeseUsed = new IngredientUsed(new Ingredient(ingredientName, "Creamy Cheese"), MeasurementType.OUNCE, quantity);

        recipe = RecipeTestUtil.createRecipe(recipeName, "Some spicy recipe using a few items", ImmutableList.of(cheeseUsed));
    }

    @Test
    public void parseAsProto() throws Exception {
        Messages.Recipe recipe = RecipeProtoMapper.parseAsProto(this.recipe);

        assertThat(recipe.getIngredientsCount(), is(1));
        Messages.IngredientUsed ingredient = recipe.getIngredients(0);

        assertThat(ingredient.getQuantity(), is(quantity));
        assertThat(ingredient.getIngredient().getName(), is(ingredientName));

        assertThat(ingredient.getType(), is(Messages.MeasurementType.OUNCE));
    }

    @Test
    public void getRecipe() throws Exception {
        Messages.Recipe protoRecipe = RecipeProtoMapper.parseAsProto(this.recipe);

        byte[] recipeAsBytes = protoRecipe.toByteArray();

        Recipe recipe = RecipeProtoMapper.getRecipe(recipeAsBytes);

        assertThat(recipe.getName(), is(recipeName));

        List<IngredientUsed> ingredientsWithQuantity = recipe.getIngredientsWithQuantity();
        assertThat(ingredientsWithQuantity.size(), is(1));

        IngredientUsed item = ingredientsWithQuantity.get(0);
        assertThat(item.getQuantity(), is(quantity));
    }

}