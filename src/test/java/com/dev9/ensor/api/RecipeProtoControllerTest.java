package com.dev9.ensor.api;

import com.dev9.ensor.ProtobufSerializationApplication;
import com.dev9.ensor.mapper.RecipeProtoMapper;
import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.util.RecipeTestUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import generated.dev9.proto.Messages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@SpringBootTest(classes = ProtobufSerializationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RecipeProtoControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getRecipeById() throws Exception {

        String recipeName = "My Recipe Here";
        ResponseEntity<Integer> response = createRecipeMessage(recipeName);

        Integer createdID = response.getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(ProtobufHttpMessageConverter.PROTOBUF);

        ResponseEntity<Messages.Recipe> foundRecipe = template.exchange("/proto/recipe?id={id}", HttpMethod.GET, new HttpEntity<String>(headers), Messages.Recipe.class, ImmutableMap.of("id", createdID));

        assertThat(foundRecipe, is(notNullValue()));

        Messages.Recipe recipe = foundRecipe.getBody();

        assertThat(recipe.getName(), is(recipeName));
    }

    @Test
    public void saveRecipe() throws Exception {
        ResponseEntity<Integer> response = createRecipeMessage("My Proto Recipe");

        assertThat(response, is(notNullValue()));
        assertThat(response.getBody(), is(greaterThanOrEqualTo(0)));
    }

    private ResponseEntity<Integer> createRecipeMessage(String recipeName) {
        String url = "/proto/add";

        Messages.Recipe recipe = RecipeProtoMapper.parseAsProto(getNewRecipe(recipeName));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(ProtobufHttpMessageConverter.PROTOBUF);

        ResponseEntity<Integer> response = template.exchange(url, HttpMethod.POST, new HttpEntity<>(recipe, headers), Integer.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        return response;
    }

    private Recipe getNewRecipe(String recipeName) {
        IngredientUsed cheeseUsed = new IngredientUsed(new Ingredient("Cheese", "Creamy Cheese"), MeasurementType.OUNCE, 4);

        return RecipeTestUtil.createRecipe(recipeName, "Some spicy recipe using a few items", ImmutableList.of(cheeseUsed));
    }

}