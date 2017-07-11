package vms.models.groups;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestParam {

	private String key;

	public String getKey() { return this.key; }

	public void setKey(String key) { this.key = key; }

	private String value;

	public String getValue() { return this.value; }

	public void setValue(String value) { this.value = value; }

}
