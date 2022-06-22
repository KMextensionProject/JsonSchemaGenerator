package sk.golddiger.jsonschemagenerator.core;

import java.util.ArrayList;
import java.util.List;

import sk.golddiger.jsonschemagenerator.exceptions.InvalidJsonType;

// DONE: parsing one line from property descriptor
// TODO: if the object has just been parsed, start appending its properties
// TODO: add another descriptor syntax to define start and end of complex property
// TODO: make possibility of default values?
// TODO: make CammelCase names from descriptor names? do it as argument option to main method
public class SchemaDescriptorLoader {

	private static final int MIN_DESCRIPTOR_COMMAND_LENGTH = 2;

	// for testing purpose just inserting lines to parse
	public static JsonProperty parse(List<String> lines) {
		// make a list of json objects or arrays...
		// then make a list of list of properties...
		// and at last merge them
		// but careful with depth...make a map f.e. and store depths for even top list and the list of list
		// then you can safely merge them
		

		return null;
	}

	// do not use this
//	public static List<JsonProperty> parseDescriptor(String fileLocation) throws IOException {
//		List<JsonProperty> properties = new ArrayList<>();		
//		boolean possibleRoot = true;
//		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
//			String mediator;
//			while ((mediator = br.readLine()) != null) {
////				JsonProperty jsonProperty = parseJsonProperty(mediator, possibleRoot);
////				properties.add(jsonProperty);
//			}
//		}
//		return properties;
//	}

	private static void validateDescriptorCommandLength(int size) {
		if (size < MIN_DESCRIPTOR_COMMAND_LENGTH) {
			throw new IllegalArgumentException("At least 2 elements: data type and name are required");
		}
	}

	private static JsonProperty parseJsonProperty(String descriptorLine) {
		String[] propertyDef = descriptorLine.split(" ");
		validateDescriptorCommandLength(propertyDef.length);

		JsonProperty jsonProperty = new JsonProperty();

		for (int i = 0; i < propertyDef.length; i++) {
			String element = propertyDef[i];
			switch (i) {
			case 0:
				validateJsonType(element);
				jsonProperty.setPropertyType(element);

				// check above plan and probably change this
				if (JsonType.OBJECT.equals(jsonProperty.getPropertyType())
				 || JsonType.ARRAY.equals(jsonProperty.getPropertyType())) {
					jsonProperty.addJsonProperties(new ArrayList<>());
					return jsonProperty;
				}

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
		default:
			if (element.contains("max")) {
				jsonProperty.setMax(parseMinMaxConstraint(element));
			} else if (element.contains("min")) {
				jsonProperty.setMin(parseMinMaxConstraint(element));
			}
		}
	}

	private static Integer parseMinMaxConstraint(String descriptorElement) {
		String value = descriptorElement.substring(
				descriptorElement.indexOf('(') + 1, descriptorElement.lastIndexOf(')'));
		return Integer.valueOf(value);
	}
}
