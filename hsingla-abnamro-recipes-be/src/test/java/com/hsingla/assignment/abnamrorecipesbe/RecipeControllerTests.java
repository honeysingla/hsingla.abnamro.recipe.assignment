package com.hsingla.assignment.abnamrorecipesbe;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsingla.assignment.abnamrorecipesbe.controllers.RecipeController;
import com.hsingla.assignment.abnamrorecipesbe.model.Recipe;
import com.hsingla.assignment.abnamrorecipesbe.service.RecipeService;
import net.minidev.json.JSONObject;

@WebMvcTest(value = RecipeController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@RunWith(SpringRunner.class)
public class RecipeControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objMapper;
	
	@MockBean
	private RecipeService recipeService;
	
	List<String> arrayList = Arrays.asList("Ingre-1", "Ingre-2", "Ingre-3");
	
	Optional<Recipe> mockRecipe = Optional.ofNullable(new Recipe("100","Aloo Jira","07-09-2021 16:30",null,true,arrayList,29,"Water with All the ingredients"));
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void retrievRecipeDetails_200() throws Exception
	{
		Mockito.when(recipeService.getRecipeById(Mockito.anyString())).thenReturn(mockRecipe);
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.get("/api/recipe/100").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result=mockMvc.perform(ReqBuider).andReturn();
		
		System.out.println("Get Recipe Details for ID: 10 -> "+result.getResponse().getContentAsString());
		
		JSONObject obj=new JSONObject();    
		obj.put("id","100");    
		obj.put("name","Aloo Jira");    
		obj.put("created","07-09-2021 16:30");
		obj.put("updated",null);    
		obj.put("vegatrian",true);
		obj.put("ingredients",arrayList); 
		obj.put("servings",29);    
		obj.put("instructions","Water with All the ingredients");
		JSONAssert.assertEquals(obj.toString(), result.getResponse().getContentAsString(), true);
		
	}
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void retrievRecipeDetails_404() throws Exception
	{
		Mockito.when(recipeService.getRecipeById(Mockito.matches("10"))).thenReturn(mockRecipe);
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.get("/api/recipe/101").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result=mockMvc.perform(ReqBuider).andReturn();
		
		System.out.println("Recipe Not Found ->"+result.getResponse().getStatus());
		
			
		assertEquals(404, result.getResponse().getStatus());
		
	}
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void createRecipeDetails_201() throws Exception
	{
		JSONObject obj=new JSONObject();    
		obj.put("id","11");    
		obj.put("name","Malai Kofta");    
		obj.put("created","07-09-2021 16:30");
		obj.put("updated",null);    
		obj.put("vegatrian",true);
		obj.put("ingredients",arrayList); 
		obj.put("servings",29);    
		obj.put("instructions","Water with All the ingredients");
		Recipe dummyRecipe = new Recipe("11","Malai Kofta","07-09-2021 16:30",null,true,arrayList,29,"Water with All the ingredients");
		
		Mockito.when(recipeService.createRecipe(Mockito.any(Recipe.class))).thenReturn(dummyRecipe);
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.post("/api/recipe").accept(MediaType.APPLICATION_JSON).content(obj.toString()).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result=mockMvc.perform(ReqBuider).andReturn();
		
		System.out.println("Recipe Created with ID 11 -> "+result.getResponse().getContentAsString());
		
		JSONAssert.assertEquals(obj.toString(), result.getResponse().getContentAsString(), true);
		
	}
	
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void createRecipeDetails_400() throws Exception
	{
		
		Recipe dummyRecipe = new Recipe("");
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.post("/api/recipe").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(null));
		
		MvcResult result=mockMvc.perform(ReqBuider).andExpect(status().isBadRequest()).andReturn();
		
		System.out.println("Body Empty ->"+result.getResponse().getStatus());
		
		assertEquals(400, result.getResponse().getStatus());
		
	}
	
	
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void updateRecipeDetails() throws Exception
	{
		// Here Update the Name of Recipe
		JSONObject obj=new JSONObject();    
		obj.put("id","10");    
		obj.put("name","Aloo Kofta");    
		obj.put("created","07-09-2021 16:30");
		obj.put("updated",null);    
		obj.put("vegatrian",true);
		obj.put("ingredients",arrayList); 
		obj.put("servings",29);    
		obj.put("instructions","Water with All the ingredients");
		Recipe dummyRecipe = new Recipe("10","Aloo Kofta","07-09-2021 16:30",null,true,arrayList,29,"Water with All the ingredients");
		
		Mockito.when(recipeService.updateRecipe(Mockito.anyString(),Mockito.any(Recipe.class))).thenReturn(dummyRecipe);
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.put("/api/recipe/10").accept(MediaType.APPLICATION_JSON).content(obj.toString()).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result=mockMvc.perform(ReqBuider).andReturn();
		
		System.out.println("Update the Data -> "+result.getResponse().getContentAsString());
		
		JSONAssert.assertEquals(obj.toString(), result.getResponse().getContentAsString(), true);
		
	}
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void deleteRecipeDetails_200() throws Exception
	{
		
		Mockito.when(recipeService.deleteRecipe("10")).thenReturn(true);
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.delete("/api/recipe/10");
		
		MvcResult result=mockMvc.perform(ReqBuider).andReturn();
		
		System.out.println("Delete the Recipe with Id: 10 "+result.getResponse().getStatus());
		
		assertEquals(200, result.getResponse().getStatus());
		
	}
	
	@WithMockUser(username = "user", authorities = {"USER"})
	@Test
	public void deleteRecipeDetails_404() throws Exception
	{
		
		Mockito.when(recipeService.deleteRecipe("10")).thenReturn(true);
		
		RequestBuilder  ReqBuider= MockMvcRequestBuilders.delete("/api/recipe/1");
		
		MvcResult result=mockMvc.perform(ReqBuider).andReturn();
		
		System.out.println("Delete the Recipe with Id: 10 "+result.getResponse().getStatus());
		
		assertEquals(404, result.getResponse().getStatus());
		
	}
	
}
