package com.hsingla.assignment.abnamrorecipesbe.service;

import java.util.List;
import java.util.Optional;

import com.hsingla.assignment.abnamrorecipesbe.model.Recipe;

public interface RecipeService {
	public List<Recipe> getAllRecipes(String name);
	public Optional<Recipe> getRecipeById(String id);
	public Recipe createRecipe(Recipe recipe);
	public Recipe updateRecipe(String id,Recipe recipe);
	public boolean deleteRecipe(String id);
}
