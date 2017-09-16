package it.gspRiva.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
/**
 * 
 * @author Administrator 
 *
 * @param <T>
 */
public class JsonResponse<T> {
	/**
	 *  definisco la mia classe da ritornare all' interfaccia
	 */
	private Boolean success;
	private  List<String> message;
	private T data;

	public JsonResponse() {}

	@JsonCreator
	public JsonResponse(@JsonProperty("success") Boolean success, @JsonProperty("message") List<String> message,
			@JsonProperty("data") T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public JsonResponse(Boolean success,  List<String> message) {
		this.success = success;
		this.message = message;
	}

	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public  List<String> getMessage() {
		return this.message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}



}
