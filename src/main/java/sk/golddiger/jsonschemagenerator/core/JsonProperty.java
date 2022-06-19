package sk.golddiger.jsonschemagenerator.core;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JsonProperty {

	private static int depth;

	private String propertyName;
	private String propertyType;
	private boolean isNotNull;
	private boolean isRequired;
	private String format;
	private Integer max;
	private Integer min;
	private Set<JsonProperty> jsonProperties;

	public List<JsonProperty> getJsonProperties() {
		return new ArrayList<>(jsonProperties);
	}

	public void addJsonProperties(List<JsonProperty> jsonProperties) {
		if (this.jsonProperties == null) {
			this.jsonProperties = new LinkedHashSet<>(jsonProperties);
		} else {
			this.jsonProperties.addAll(jsonProperties);
		}
	}

	public boolean addJsonProperty(JsonProperty jsonProperty) {
		return jsonProperties.add(jsonProperty);
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}

	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public String toString() {
		depth = 0;
		return this.toString(depth);
	}

	private String toString(int level) {
		StringBuilder sb = new StringBuilder();
		appendTab(sb, level);
		sb.append("type: " + this.propertyType + System.lineSeparator());
		appendTab(sb, level);
		sb.append("name: " + this.propertyName + System.lineSeparator());
		appendTab(sb, level);
		sb.append("nullable: " + !this.isNotNull + System.lineSeparator());
		appendTab(sb, level);
		sb.append("required: " + this.isNotNull + System.lineSeparator());
		if (jsonProperties != null && !jsonProperties.isEmpty()) {
			level += 1;
			for (JsonProperty prop : jsonProperties) {
				sb.append(prop.toString(level) + System.lineSeparator());
			}
		}
		return sb.toString();
	}

	private void appendTab(StringBuilder string, int howMany) {
		StringBuilder tabs = new StringBuilder();
		for (int i = 0; i < howMany; i++) {
			tabs.append('\t');
		}
		string.append(tabs);
	}
}
