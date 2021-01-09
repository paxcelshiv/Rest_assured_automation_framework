package com.adidas.RESTAPIAutomation.assertions;

import java.util.ArrayList;

import org.json.JSONObject;
import org.testng.Assert;

import com.adidas.RESTAPIAutomation.model.Delete;
import com.adidas.RESTAPIAutomation.model.ListofPets;
import com.adidas.RESTAPIAutomation.model.Pet;
import com.adidas.RESTAPIAutomation.utility.BaseClass;
import com.adidas.RESTAPIAutomation.utility.Log;
import com.google.gson.Gson;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Assertions {
	public static void validatea_available_pets(ArrayList<ListofPets> pets, Response res) {
		Log.info("------ Start validation for available pets -------------");
		String json = new Gson().toJson(pets.get(0));
		JSONObject obj = new JSONObject(json);
		Headers header = res.getHeaders();
		Assert.assertEquals(header.getValue("Content-Type"), "application/json");
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(obj.getString("status"), "available");
		Assert.assertNotEquals(obj.getBigInteger("id"), "9222968140496916780");
		Log.info("------ End validation for available pets -------------");
	}

	public static void validatea_create_pets(Pet pets, Response res) {
		Log.info("------ Start validation for Create pets -------------");
		Headers header = res.getHeaders();
		Assert.assertEquals(header.getValue("Content-Type"), "application/json");
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(pets.getStatus(), "available");
		Assert.assertEquals(pets.getId(), BaseClass.id);
		Assert.assertEquals(pets.getName(), BaseClass.name);
		Log.info("------ End validation for Create pets -------------");
	}

	public static void validatea_update_pets_status(Pet pets, Response res) {
		Log.info("------ Start validation for update pets -------------");
		Headers header = res.getHeaders();
		Assert.assertEquals(header.getValue("Content-Type"), "application/json");
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(pets.getStatus(), "sold");
		Log.info("------ End validation for update pets -------------");
	}

	public static void validatea_delete_pet(Delete delete, Response res, String petId) {
		Log.info("------ Start validation for delete pets -------------");
		Headers header = res.getHeaders();
		Assert.assertEquals(header.getValue("Content-Type"), "application/json");
		Assert.assertEquals(res.getStatusCode(), 200);
		Assert.assertEquals(delete.code, 200);
		Assert.assertEquals(delete.message, petId);
		Assert.assertEquals(delete.type, "unknown");
		Log.info("------ End validation for delete pets -------------");
	}
}
