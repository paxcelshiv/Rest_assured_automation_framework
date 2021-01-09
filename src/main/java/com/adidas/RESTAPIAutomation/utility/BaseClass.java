package com.adidas.RESTAPIAutomation.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.xml.DOMConfigurator;

import com.adidas.RESTAPIAutomation.model.Pet;
import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseClass {
	public static BigInteger id;
	public static String name;
	public static String file_path;

	public BaseClass() {
		super();
		new ReadTestProperties();
		DOMConfigurator.configure("log4j.xml");
		RestAssured.baseURI = ReadTestProperties.BaseUrl;
		RestAssured.basePath = ReadTestProperties.ContextPath;
	}

	static {
		Faker faker = new Faker();
		Random rand = new Random();
		int l = rand.nextInt(1000000000);
		id = BigInteger.valueOf(l);
		name = faker.animal().name();
	}

	public static RequestSpecification getRequest() {
		Log.info("------ Start request building  --------------------");
		RequestSpecification req = RestAssured.given();
		Map<String, String> apiheaders = new HashMap<String, String>();
		apiheaders.put("Content-Type", "application/json");
		req.headers(apiheaders);
		Log.info("build request :" + req);
		Log.info("------ End request building  --------------------");
		return req;
	}

	public static String readFileAsString(String file) throws Exception {
		Log.info("------ Start file reading  ------------");
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	public static Pet createpetPOJO(String file) throws Exception {
		Log.info("------ Start post request String to pojo  --------------------");
		Pet petObject = null;
		String jsonFilePath = System.getProperty("user.dir") + "/src/test/resources/jsons/" + file + ".json";
		String json = readFileAsString(jsonFilePath);
		petObject = JSONHandler.convertJSONStringtoPOJO(json, Pet.class);
		Log.info("------ End post request String to pojo  --------------------");
		return petObject;
	}

	public static void file_Write(String textStr, String fileName) throws IOException {
		Log.info("------ Start Writing file  --------------------");
		file_path = System.getProperty(("user.dir")) + "/src/test/resources/jsons";
		FileWriter fw = new FileWriter(file_path + "/" + fileName);
		for (int i = 0; i < textStr.length(); i++) {
			fw.write(textStr.charAt(i));
		}
		Log.info("File has been wrriten and saved in: " + file_path + "folder with name " + fileName);
		fw.close();
	}

}
