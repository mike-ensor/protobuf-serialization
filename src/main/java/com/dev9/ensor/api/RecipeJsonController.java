package com.dev9.ensor.api;

import com.dev9.ensor.model.Recipe;
import com.dev9.ensor.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json")
public class RecipeJsonController {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeJsonController.class);

    private final RecipeRepository repository;

    @Autowired
    public RecipeJsonController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipe")
    public Recipe getRecipeById(@RequestParam("id") Integer id) {
        Recipe found = repository.getRecipeById(id);
        if (found != null) {
            LOG.debug("Found JSON Recipe [{}] with ID[{}]", found.getName(), id);
        } else {
            LOG.info("JSON Recipe [{}] was not found", id);
        }
        return found;
    }

    @PostMapping(value = "/add", consumes = "application/json; charset=UTF-8")
    public Integer saveRecipe(@RequestBody Recipe recipe) {
        Integer id = repository.save(recipe);
        LOG.debug("Storing new recipe [{}] with ID [{}]", recipe.getName(), id);
        return id;
    }

}
