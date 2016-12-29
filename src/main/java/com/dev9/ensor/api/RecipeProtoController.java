package com.dev9.ensor.api;

import com.dev9.ensor.repository.RecipeRepository;
import generated.dev9.proto.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proto")
public class RecipeProtoController {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeProtoController.class);

    private final RecipeRepository repository;

    @Autowired
    public RecipeProtoController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipe")
    public Messages.Recipe getRecipeById(@RequestParam("id") Integer id) {
        Messages.Recipe found = repository.getProtoRecipeById(id);

        if (found != null) {
            LOG.debug("Found Protobuf Recipe [{}] with ID[{}]", found.getName(), id);
        } else {
            LOG.info("Protobuf Recipe [{}] was not found", id);
        }
        return found;
    }

    @PostMapping(value = "/add", consumes = "application/x-protobuf")
    public String saveRecipe(@RequestBody Messages.Recipe recipe) {
        Integer id = repository.save(recipe);
        LOG.info("Storing new recipe [{}] with ID [{}]", recipe.getName(), id);
        return String.format("%d", id);
    }
}
