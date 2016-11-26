package com.dev9.benchmark;

import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.service.RecipeService;
import com.dev9.ensor.util.RecipeTestUtil;
import com.google.common.collect.ImmutableList;
import generated.dev9.proto.Messages;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SerializationBenchmark {

    private RecipeService service;
    private Recipe recipe;

    @Setup(Level.Trial)
    public void setup() {
        IngredientUsed jalepenoUsed = new IngredientUsed(new Ingredient("Jalepeno", "Spicy Pepper"), MeasurementType.ITEM, 1);
        IngredientUsed cheeseUsed = new IngredientUsed(new Ingredient("Cheese", "Creamy Cheese"), MeasurementType.OUNCE, 4);

        recipe = RecipeTestUtil.createRecipe("My Recipe", "Some spicy recipe using a few items", ImmutableList.of(jalepenoUsed, cheeseUsed));
        service = new RecipeService();
    }

    @Benchmark
    public Messages.Recipe serializeToProtobufRecipe() {
        return service.getProtoRecipe(recipe);
    }

    @Benchmark
    public String serializeToJSONRecipe() {
        return service.recipeAsJSON(recipe);
    }

}
