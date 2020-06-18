package com.infy.scheduler.dto;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDetail {
	@JsonProperty("request_url")
	private String requestUrl;

	@JsonProperty("http_method")
	private String httpMethod;

	private JSONObject body;

	private JSONObject headers;

	public String getRequestUrl() {
		return requestUrl;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public JSONObject getBody() {
		return body;
	}

	public JSONObject getHeaders() {
		return headers;
	}
}
