package com.adidas.RESTAPIAutomation.utility;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import com.adidas.RESTAPIAutomation.model.Pet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ExecuteRestRequest extends BaseClass {
	public static Response response;

	public static Response executeRequest(String payload, String urlString, String apiMethodType) {
		RequestSpecification req = getRequest();
		req.body(payload);
		Log.info("Request payload :" + payload);
		if (apiMethodType.equalsIgnoreCase("POST")) {
			try {
				response = req.post(urlString);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			Log.debug("Response of post request :" + response);
		} else if (apiMethodType.equalsIgnoreCase("PUT")) {
			response = req.put(urlString);
			Log.info("Response of put request :" + response);
		} else if (apiMethodType.equalsIgnoreCase("DELETE")) {
			response = req.delete(urlString);
			Log.info("Response of delete request :" + response);
		} else {
			response = req.get(urlString);
			Log.info("Response of get request :" + response);
		}
		return response;
	}

	public static <T> T convertJSONStringtoPOJO(final String jsonString, final Class<T> pojoClass)
			throws FileSystemException {
		final ObjectMapper jsonMapper = new ObjectMapper();
		T responseData = null;
		try {
			responseData = jsonMapper.readValue(jsonString, new TypeReference<List<Pet>>() {
			});
			Log.info("Parse response data :" + responseData);
		} catch (Exception Exp) {
			Log.debug("Exception message" + Exp.getMessage());
		}
		return responseData;
	}

	public static <T> T convertJSONStringtoPOJO1(final String jsonString, final Class<T> pojoClass)
            throws FileSystemException {
		Log.info("Converting JSON string to POJO started:::");
		Log.debug("Converting JSON string to POJO: " + jsonString);
        final ObjectMapper jsonMapper = new ObjectMapper();
        T responseData = null;
        try {
            responseData = jsonMapper.readValue(jsonString, pojoClass);
            Log.debug("JSON string converted to POJO");
        } catch (IOException ioExp) {
        	Log.error("JSON string conversion to POJO failed.\n" + ioExp);
        }
        Log.debug("Converting JSON string to POJO completed:::");
        return responseData;
    }
}
