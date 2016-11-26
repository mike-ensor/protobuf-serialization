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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class SerializationBenchmark {

    private static final Logger LOG = LoggerFactory.getLogger(SerializationBenchmark.class);
    private RecipeService service;
    private Recipe recipe;
    private byte[] protoRecipe;
    private String recipeAsJSON;


    @Setup(Level.Trial)
    public void setup() {
        IngredientUsed jalepenoUsed = new IngredientUsed(new Ingredient("Jalepeno", "Spicy Pepper"), MeasurementType.ITEM, 1);
        IngredientUsed cheeseUsed = new IngredientUsed(new Ingredient("Cheese", "Creamy Cheese"), MeasurementType.OUNCE, 4);

        recipe = RecipeTestUtil.createRecipe("My Recipe", "Some spicy recipe using a few items", ImmutableList.of(jalepenoUsed, cheeseUsed));
        service = new RecipeService();

        protoRecipe = service.recipeAsProto(recipe).toByteArray();
        recipeAsJSON = service.recipeAsJSON(recipe);

    }

    @Benchmark
    public Messages.Recipe serialize_recipe_object_to_protobuf() {
        return service.recipeAsProto(recipe);
    }

    @Benchmark
    public String serialize_recipe_object_to_JSON() {
        return service.recipeAsJSON(recipe);
    }

    @Benchmark
    public Recipe deserialize_protobuf_to_recipe_object() {
        return service.getRecipe(protoRecipe);
    }

    @Benchmark
    public Recipe deserialize_json_to_recipe_object() {
        return service.getRecipe(recipeAsJSON);
    }

}
