package com.hsingla.assignment.abnamrorecipesbe.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsingla.assignment.abnamrorecipesbe.model.Recipe;
import com.hsingla.assignment.abnamrorecipesbe.service.RecipeService;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@GetMapping("/allRecipes")
	public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) String name) {
		try {
			
			List<Recipe> recipes = recipeService.getAllRecipes(name);
			return new ResponseEntity<>(recipes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/recipe/{id}")
	public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") String id) {
		Optional<Recipe> recipeData = recipeService.getRecipeById(id);
		if (recipeData.isPresent()) {
			return new ResponseEntity<>(recipeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/recipe")
	public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
		try {
				Recipe createRecipe = recipeService.createRecipe(recipe);
				if(createRecipe != null)
					return new ResponseEntity<>(createRecipe, HttpStatus.CREATED);
				else
					return new ResponseEntity<>(null, HttpStatus.FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/recipe/{id}")
	public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") String id, @RequestBody Recipe recipe) {
		
		Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
		
		if(updatedRecipe != null)
			return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/recipe/{id}")
	public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") String id) {
		boolean isDeleteed=recipeService.deleteRecipe(id); 
		try {
			if(isDeleteed)
			{
				return new ResponseEntity<>(HttpStatus.OK);
			}else
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
