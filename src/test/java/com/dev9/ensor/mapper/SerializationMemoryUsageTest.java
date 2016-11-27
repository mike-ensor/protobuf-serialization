package com.dev9.ensor.mapper;

import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.service.RecipeService;
import com.dev9.ensor.util.RecipeTestUtil;
import com.google.common.collect.ImmutableList;
import generated.dev9.proto.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SerializationMemoryUsageTest extends AbstractMemoryOprationCaptureTemplate {

    private static final Logger LOG = LoggerFactory.getLogger(SerializationMemoryUsageTest.class);

    @Autowired
    private RecipeService service;

    private Recipe recipe;
    private String recipeAsJSON;

    @Before
    public void setup() {

        IngredientUsed cheeseUsed = new IngredientUsed(new Ingredient("Cheese", "Creamy Cheese"), MeasurementType.OUNCE, 4);

        recipe = RecipeTestUtil.createRecipe("My Cheese Recipe", "Some spicy recipe using a few items", ImmutableList.of(cheeseUsed));
        recipeAsJSON = service.recipeAsJSON(recipe);
    }


    @Test
    public void memoryUsedDuringSerialization() {
        long memory = getInitialMemory();

        String jsonResult = service.recipeAsJSON(recipe);

        long after = getMemoryAfterOperation();

        logMemoryUsage(memory, after, "JSON: Serialization Memory Total");

        assertThat(jsonResult, is(notNullValue()));
    }

    @Test
    public void memoryUsedDuringDesrialization() {
        long memory = getInitialMemory();

        Recipe jsonResult = service.getRecipe(recipeAsJSON);

        long after = getMemoryAfterOperation();

        logMemoryUsage(memory, after, "JSON: Deserialization Memory Total");

        assertThat(jsonResult, is(notNullValue()));

    }

    @Test
    public void memoryUsageForDeserialization() throws Exception {
        Messages.Recipe protoRecipe = RecipeProtoMapper.parseAsProto(this.recipe);

        byte[] recipeAsBytes = protoRecipe.toByteArray();

        long memory = getInitialMemory();
        // activity
        Recipe normalRecipe = RecipeProtoMapper.getRecipe(recipeAsBytes);

        long after = getMemoryAfterOperation();

        logMemoryUsage(memory, after, "Protobuf: Deerialization Memory Total");

        assertThat(normalRecipe, is(notNullValue()));
    }

    @Test
    public void memoryUsageSerialization() throws Exception {
        long memory = getInitialMemory();

        Messages.Recipe protoRecipe = RecipeProtoMapper.parseAsProto(this.recipe);

        long after = getMemoryAfterOperation();

        logMemoryUsage(memory, after, "Protobuf: Serialization Memory Total");

        assertThat(protoRecipe, is(notNullValue()));
    }

}
