package sk.golddiger.jsonschemagenerator.core;

public class JsonProperty {
	private String propertyName;
	private String propertyType;
	private boolean isNotNull;
	private boolean isRequired;
	private String format;
	private Integer max;
	private Integer min;
	private JsonProperty jsonProperty;

	public JsonProperty getJsonProperty() {
		return jsonProperty;
	}

	public void setJsonProperty(JsonProperty jsonProperty) {
		this.jsonProperty = jsonProperty;
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

	@Override
	public String toString() {
		return this.propertyName + ": [" + this.propertyType + ", " + this.isNotNull + ", " + this.isRequired + "]";
	}

}
