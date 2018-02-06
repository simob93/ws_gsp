package it.gspRiva.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class KeyValue<T1, T2, T> {
	
	
	
	private T1 key;
	private T2 value;
	private T extra;
	
	public KeyValue() {}
	
//	@JsonCreator
//	public KeyValue(@JsonProperty("key") T1 key, @JsonProperty("value") T2 value, @JsonProperty("extra") T extra) {
//		this.key = key;
//		this.value = value;
//		this.extra = extra;
//	}
	
	public KeyValue(T1 key, T2 value, T extra) {
		this.key = key;
		this.value = value;
		this.extra = extra;
	}

	public T1 getKey() {
		return key;
	}

	public void setKey(T1 key) {
		this.key = key;
	}

	public T2 getValue() {
		return value;
	}

	public void setValue(T2 value) {
		this.value = value;
	}

	public T getExtra() {
		return extra;
	}

	public void setExtra(T extra) {
		this.extra = extra;
	}

	
	
}
