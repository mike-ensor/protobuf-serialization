package com.dev9.ensor.service;

import com.dev9.ensor.model.BaseQuantity;
import com.dev9.ensor.model.Item;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RecipeServiceTest {

    private RecipeService service;

    @Before
    public void setUp() throws Exception {
        service = new RecipeService();
    }

    @Test
    public void addRecipe() throws Exception {

        String name = "My Recipe";
        String description = "Some spicy recipe using a few items";

        Item jalepeno = new Item("Jalepeno", "Spicy Pepper", BaseQuantity.SINGLE_ITEM);
        Item cheese = new Item("Cheese", "Creamy Cheese", new BaseQuantity(MeasurementType.GRAMS, 28));

        // one part Jalepeno, one part Cheese
        Recipe recipe = service.createRecipe(name, description, ImmutableMap.of(jalepeno, 1, cheese, 2));

        assertThat(recipe.getItemQuantity().size(), is(2));
    }
}