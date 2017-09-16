package it.gspRiva.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class KeyValue<T> {
	
	
	
	private T key;
	private T value;
	
	public KeyValue() {}
	
	@JsonCreator
	public KeyValue(@JsonProperty("key") T key, @JsonProperty("value") T value) {
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	
	
}
