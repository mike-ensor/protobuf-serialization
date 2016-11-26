package com.dev9.benchmark;

import com.dev9.ensor.mapper.RecipeProtoMapper;
import com.dev9.ensor.model.Ingredient;
import com.dev9.ensor.model.IngredientUsed;
import com.dev9.ensor.model.MeasurementType;
import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.util.RecipeTestUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import generated.dev9.proto.Messages;
import org.openjdk.jmh.annotations.*;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@Warmup(iterations = 1)
@Measurement(iterations = 5)
public class RESTBenchmark {

    private static final String JSON_URL = "http://localhost:8080/json/add";
    private static final String PROTO_URL = "http://localhost:8080/proto/add";

    private RestTemplate template;
    private HttpHeaders jsonHeaders;
    private HttpHeaders protoHeaders;
    private Recipe recipe;
    private Messages.Recipe protoRecipe;

    public RESTBenchmark() {
        List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
        messageConverters.add(new ProtobufHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        template = new RestTemplate(messageConverters);

        jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);

        protoHeaders = new HttpHeaders();
        protoHeaders.setContentType(new MediaType("application", "x-protobuf"));
    }

    @Setup
    public void setup() {
        IngredientUsed jalepenoUsed = new IngredientUsed(new Ingredient("Jalepeno", "Spicy Pepper"), MeasurementType.ITEM, 1);
        IngredientUsed cheeseUsed = new IngredientUsed(new Ingredient("Cheese", "Creamy Cheese"), MeasurementType.OUNCE, 4);

        recipe = RecipeTestUtil.createRecipe("My Recipe", "Some spicy recipe using a few items", ImmutableList.of(jalepenoUsed, cheeseUsed));
        protoRecipe = RecipeProtoMapper.parseAsProto(recipe);
    }

    @Benchmark
    public Integer store_receipt_via_json() {
        ResponseEntity<Integer> response = template.exchange(JSON_URL, HttpMethod.POST, new HttpEntity<>(recipe, jsonHeaders), Integer.class);

        return response.getBody();
    }

    @Benchmark
    public Integer store_receipt_via_proto() {
        ResponseEntity<Integer> response = template.exchange(PROTO_URL, HttpMethod.POST, new HttpEntity<>(protoRecipe, protoHeaders), Integer.class);

        return response.getBody();
    }

}
