package com.adidas.RESTAPIAutomation.utility;

import java.io.IOException;
import java.nio.file.FileSystemException;

import org.apache.log4j.Logger;

import com.adidas.RESTAPIAutomation.model.Pet;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHandler {
	private static final Logger LOGGER = Logger.getLogger(JSONHandler.class);

	private JSONHandler() {
	}

	public static String convertToJSONString(final Object userDetails, Class<Pet> class1) throws FileSystemException {
		LOGGER.trace("Converting UserDetails POJO to JSON string started:::");
		final ObjectMapper jsonMapper = new ObjectMapper();

		String jsonString = null;
		try {
			jsonString = jsonMapper.writeValueAsString(userDetails);
			final JsonNode jsonUserDetailsNode = jsonMapper.readTree(jsonString);
			jsonString = jsonMapper.writeValueAsString(jsonUserDetailsNode);
			LOGGER.debug("Final UserDetail JSON string: " + jsonString);
		} catch (IOException ioExp) {
			LOGGER.error("UserDetails POJO to JSON string failed.\n" + ioExp);
		}
		LOGGER.trace("Converting UserDetails POJO to JSON string completed:::");
		return jsonString;
	}

	public static <T> T convertJSONStringtoPOJO(final String jsonString, final Class<T> pojoClass)
			throws FileSystemException {
		LOGGER.trace("Converting JSON string to POJO started:::");
		LOGGER.debug("Converting JSON string to POJO: " + jsonString);
		final ObjectMapper jsonMapper = new ObjectMapper();
		T responseData = null;
		try {
			responseData = jsonMapper.readValue(jsonString, pojoClass);
			LOGGER.debug("JSON string converted to POJO");
		} catch (IOException ioExp) {
			LOGGER.error("JSON string conversion to POJO failed.\n" + ioExp);
		}
		LOGGER.trace("Converting JSON string to POJO completed:::");
		return responseData;
	}
}
