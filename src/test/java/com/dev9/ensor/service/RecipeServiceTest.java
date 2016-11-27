package com.dev9.ensor.service;

import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.util.RecipeTestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.InvalidProtocolBufferException;
import generated.dev9.proto.Messages;
import org.junit.Before;
import org.junit.Test;

import static com.dev9.ensor.util.RecipeTestUtil.getMockRecipeJSONString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RecipeServiceTest {

    private RecipeService service;

    @Before
    public void setUp() throws Exception {
        service = new RecipeService(new ObjectMapper());
    }

    @Test
    public void addRecipe() throws Exception {
        Recipe recipe = createRecipe();

        assertThat(recipe.getIngredientsWithQuantity().size(), is(2));
    }

    @Test
    public void tryProtobufSerialization() throws InvalidProtocolBufferException {

        Recipe recipe = createRecipe();

        Messages.Recipe protoRecipe = service.recipeAsProto(recipe);
        assertThat(protoRecipe.getDescription(), is(recipe.getDescription()));
        assertThat(protoRecipe.getIngredientsCount(), is(2));
    }

    @Test
    public void fromJSON() {
        String description = "Some spicy recipe using a few items";
        String json = getMockRecipeJSONString(description);

        Recipe recipe = service.getRecipe(json);
        assertThat(recipe.getDescription(), is(description));
        assertThat(recipe.getIngredientsWithQuantity().size(), is(2));
    }

    @Test
    public void protoSerializationToFile() throws InvalidProtocolBufferException {

        Recipe recipe = createRecipe();

        Messages.Recipe protoRecipe = service.recipeAsProto(recipe);

        service.writeRecipeToFile("target/myrecipe.bin.proto", protoRecipe);


    }

    private Recipe createRecipe() {
        String name = "My Recipe";
        String description = "Some spicy recipe using a few items";

        Ingredient jalepeno = new Ingredient("Jalepeno", "Spicy Pepper");
        Ingredient cheese = new Ingredient("Cheese", "Creamy Cheese");

        IngredientUsed jalepenoUsed = new IngredientUsed(jalepeno, MeasurementType.ITEM, 1);
        IngredientUsed cheeseUsed = new IngredientUsed(cheese, MeasurementType.OUNCE, 4);

        // one part Jalepeno, one part Cheese
        return RecipeTestUtil.createRecipe(name, description, ImmutableList.of(jalepenoUsed, cheeseUsed));
    }
}