package com.hsingla.assignment.abnamrorecipesbe.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsingla.assignment.abnamrorecipesbe.model.Recipe;
import com.hsingla.assignment.abnamrorecipesbe.repository.RecipeRepository;
@Service
public class RecipeServiceImpl implements RecipeService{
	
	@Autowired
	RecipeRepository recipeRepo;
	
	
	
	public List<Recipe> getAllRecipes(String name) {
		List<Recipe> recipes = new ArrayList<Recipe>();

		if (name == null)
			recipeRepo.findAll().forEach(recipes::add);
		else
			recipeRepo.findByNameContaining(name).forEach(recipes::add);
		if (recipes.isEmpty()) {
			return recipes;
		}
		return recipes;
	}

	public Optional<Recipe> getRecipeById(String id) {
		Optional<Recipe> recipeData = recipeRepo.findById(id);
		return recipeData;
	}
	
	
	public Recipe createRecipe(Recipe recipe) {
		if(recipeRepo.findById(recipe.getId()).isEmpty())
		{
		    recipe.setCreated(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
		    recipe.setUpdated(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
			Recipe createRecipe = recipeRepo.save(recipe);
			return createRecipe;
		}else
		{
			return null;
		}
	}

	public Recipe updateRecipe(String id,Recipe recipe) {
		Optional<Recipe> recipeData = recipeRepo.findById(id);
		if (recipeData.isPresent()) {
			Recipe updateRecipe = recipeData.get();
			updateRecipe.setName(recipe.getName());
			updateRecipe.setCreated(recipeData.get().getCreated());
			updateRecipe.setUpdated(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date()));
			updateRecipe.setIngredients(recipe.getIngredients());
			updateRecipe.setInstructions(recipe.getInstructions());
			updateRecipe.setServings(recipe.getServings());
			recipeRepo.save(updateRecipe);
			return updateRecipe;
		} else {
			return null;
		}
	}
	
	public boolean deleteRecipe(String id) {
		Optional<Recipe> recipeData = recipeRepo.findById(id);
		if(recipeData.get().getId()!=null)
		{
			recipeRepo.deleteById(id);
			return true;
		}else
		{
			return false;
		}
		
	}
	
}


