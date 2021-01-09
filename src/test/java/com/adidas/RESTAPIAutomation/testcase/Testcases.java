package com.adidas.RESTAPIAutomation.testcase;

import java.math.BigInteger;
import java.util.ArrayList;

import org.testng.annotations.Test;

import com.adidas.RESTAPIAutomation.assertions.Assertions;
import com.adidas.RESTAPIAutomation.model.Delete;
import com.adidas.RESTAPIAutomation.model.ListofPets;
import com.adidas.RESTAPIAutomation.model.Pet;
import com.adidas.RESTAPIAutomation.utility.BaseClass;
import com.adidas.RESTAPIAutomation.utility.ExecuteRestRequest;
import com.adidas.RESTAPIAutomation.utility.Log;
import com.google.gson.Gson;

import io.restassured.response.Response;

public class Testcases extends BaseClass {
	public Response response;
	static String url = "/pet/findByStatus?status=available";

	@Test(priority = 0)
	public void get_available_pet() throws Exception {
		Log.info("---------------------- Start test execution get available pet --------------------");
		response = ExecuteRestRequest.executeRequest("", url, "GET");
		String responsebody = response.getBody().asString();
		@SuppressWarnings("unchecked")
		ArrayList<ListofPets> pets = ExecuteRestRequest.convertJSONStringtoPOJO(responsebody, ArrayList.class);
		Assertions.validatea_available_pets(pets, response);
		Log.info("---------------------- End test execution get available pet --------------------");
	}

	@Test(priority = 1)
	public void create_Pet() throws Exception {
		Log.info("------------ Start test execution new available pet to the store --------------");
		Pet requestPetObject;
		requestPetObject = createpetPOJO("pet");
		requestPetObject.setId(id);
		requestPetObject.setStatus("available");
		requestPetObject.setName(name);
		String json = new Gson().toJson(requestPetObject);
		response = ExecuteRestRequest.executeRequest(json, "/pet", "POST");
		String responsebody = response.getBody().asString();
		file_Write(responsebody, "createpetreponse.json");
		Pet pets = ExecuteRestRequest.convertJSONStringtoPOJO1(responsebody, Pet.class);
		Assertions.validatea_create_pets(pets, response);
		Log.info("------------ End test execution new available pet to the store --------------");
	}

	@Test(priority = 2)
	public void update_pet_status() throws Exception {
		Log.info("------------ Start test execution update pet status to sold --------------");
		Pet requestPetObject;
		requestPetObject = createpetPOJO("createpetreponse");
		requestPetObject.setStatus("sold");
		String json = new Gson().toJson(requestPetObject);
		response = ExecuteRestRequest.executeRequest(json, "/pet", "PUT");
		String responsebody = response.getBody().asString();
		Pet pets = ExecuteRestRequest.convertJSONStringtoPOJO1(responsebody, Pet.class);
		Assertions.validatea_update_pets_status(pets, response);
		Log.info("------------ End test execution update pet status to sold --------------");
	}

	@Test(priority = 3)
	public void delete_pet() throws Exception {
		Log.info("------------ Start test execution delete pet --------------");
		Pet requestPetObject;
		requestPetObject = createpetPOJO("createpetreponse");
		BigInteger id = requestPetObject.getId();
		String petid = id.toString();
		String deleteUrl = "/pet/" + petid;
		response = ExecuteRestRequest.executeRequest("", deleteUrl, "DELETE");
		String responsebody = response.getBody().asString();
		Delete delete = ExecuteRestRequest.convertJSONStringtoPOJO1(responsebody, Delete.class);
		Assertions.validatea_delete_pet(delete, response, petid);
		Log.info("------------ Start test execution delete pet --------------");
	}
}
