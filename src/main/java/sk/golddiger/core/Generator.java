package sk.golddiger.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Generator {
	
	private static final Set<String> SUPPORTED_DATA_TYPES = Set.of(
			"integer",
			"string",
			"object",
			"array",
			"boolean",
			"date");

	public static void main(String[] args) {
		/*
	"ziadost_id": {
        "type": [
          "integer",
          "null"
        ]
      },
		 */
		
		// TODO: construct this better...line words one by one
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/sample_descriptors/person"))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				// for now to test the types
				if (line.contains("object") || line.contains("array")) {
					continue;
				}
				String[] lineParts = line.split(" ");
				String jsonPart = "";
				switch(lineParts.length) {
				case 2:
					jsonPart = getJsonNamedTypeDesc(lineParts[0], lineParts[1], false, false, "");
					break;
				case 3:
					jsonPart = getJsonNamedTypeDesc(lineParts[0], lineParts[1], false, false, lineParts[3]);
					break;
				case 4:
					if (lineParts[2].equals("required")) {
						jsonPart = getJsonNamedTypeDesc(lineParts[0], lineParts[1], true, false, lineParts[3]);
					} else {
						jsonPart = getJsonNamedTypeDesc(lineParts[0], lineParts[1], false, true, lineParts[3]);
					}
					break;
				case 5:
					jsonPart = getJsonNamedTypeDesc(lineParts[0], lineParts[1], true, true, lineParts[3]);
				}
				sb.append(jsonPart);
			}		
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println(sb);
	}

	// TODO: create jsonNamedArrayType
	
	// TODO: take level of tabs
	private static String getJsonNamedTypeDesc(String type, String name, boolean required, boolean notNull, String lineEnding) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"" + name + "\": {\n");
		appendTab(1, sb);
		sb.append("\"type\": [\n");
		appendTab(1, sb);
		sb.append("  \"" + type + "\"");
		if (!notNull) {
			sb.append(",\n");
			appendTab(1, sb);
			sb.append("  \"null\"\n");
			appendTab(1, sb);
			sb.append("]\n");
		} else {
			sb.append("\n");
			appendTab(1, sb);
			sb.append("]\n");
		}
		if (lineEnding.contains("\\")) {
			sb.append("},\n");
		} else {
			sb.append("}\n");
		}
		return sb.toString();
	}

	/**
	 * pass zero for no level
	 */
	private static void appendTab(int level, StringBuilder schema) {
		for (int i = 1; i <= level; i++) {
			schema.append('\t');
		}
	}

	private static void validateDataType(String type) {
		if (!SUPPORTED_DATA_TYPES.contains(type)) {
			throw new IllegalArgumentException("Illegal type declaration for data type " 
					+ type + ". Expected one of " + SUPPORTED_DATA_TYPES);
		}
	}
}
