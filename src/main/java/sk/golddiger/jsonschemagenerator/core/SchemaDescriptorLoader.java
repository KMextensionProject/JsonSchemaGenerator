package sk.golddiger.jsonschemagenerator.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sk.golddiger.jsonschemagenerator.exceptions.InvalidJsonType;

public class SchemaDescriptorLoader {

	private static final int MAX_DESCRIPTOR_COMMAND_LENGTH = 2;

	public static List<JsonProperty> parseDescriptor(String fileLocation) throws IOException {
		List<JsonProperty> properties = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
			
			String mediator;
			while ((mediator = br.readLine()) != null) {
				String[] propertyDef = mediator.split(" ");
				validateDescriptorCommandLength(propertyDef.length);
				
				JsonProperty jsonProperty = parseJsonProperty(propertyDef);
				properties.add(jsonProperty);
			}
		}
		return properties;
	}

	private static void validateDescriptorCommandLength(int size) {
		if (size < MAX_DESCRIPTOR_COMMAND_LENGTH) {
			throw new IllegalArgumentException("At least 2 elements: data type and name are required bro");
		}
	}

	private static JsonProperty parseJsonProperty(String[] propertyDef) {
		JsonProperty jsonProperty = new JsonProperty();
		for (int i = 0; i < propertyDef.length; i++) {
			String element = propertyDef[i];
			switch (i) {
			case 0:
				validateJsonType(element);
				jsonProperty.setPropertyType(element);
				break;
			case 1:
				jsonProperty.setPropertyName(element);
				break;
			default:
				setOptionalProperties(jsonProperty, element);
			}
		}
		return jsonProperty;
	}

	private static void validateJsonType(String type) {
		switch (type) {
		case JsonType.STRING:
		case JsonType.OBJECT:
		case JsonType.ARRAY:
		case JsonType.BOOLEAN:
		case JsonType.DATE:
		case JsonType.FLOAT:
		case JsonType.INTEGER:
			return;
		default:
			throw new InvalidJsonType("Invalid data type");

		}
	}

	private static void setOptionalProperties (JsonProperty jsonProperty, String element) {
		switch (element) {
		case "required":
			jsonProperty.setRequired(true);
			break;
		case "not_null":
			jsonProperty.setNotNull(true);
			break;
		}
	}
}
