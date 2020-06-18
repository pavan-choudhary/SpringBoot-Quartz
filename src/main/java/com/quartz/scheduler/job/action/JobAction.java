package com.infy.scheduler.job.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class JobAction implements Job {

	private static final String POST = "POST";
	private static final String GET = "GET";

	private final Logger LOG = Logger.getLogger(JobAction.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {		
		JobDataMap detail = context.getMergedJobDataMap();

		LOG.info("JobKey: " + context.getJobDetail().getKey());

		String method = detail.get("method").toString();
		JSONObject body = (JSONObject) detail.get("body");
		JSONObject headersJson = (JSONObject) detail.get("headers");

		HttpMethod httpMethod = null;
		if (method.equals(GET)) {
			httpMethod = HttpMethod.GET;
		} else if (method.equals(POST)) {
			httpMethod = HttpMethod.POST;
		}
		try {
			apiCall(detail.get("url").toString(), httpMethod, body, headersJson);
		} catch (JsonMappingException e) {
			LOG.warn("Json Mapping Exception occured");
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			LOG.warn("Json Parsing Exception occured");
			e.printStackTrace();
		} catch (Exception e) {
			LOG.warn("Cannot connect to Url");
		}
	}

	@SuppressWarnings("unchecked")
	public void apiCall(String url, HttpMethod method, JSONObject body, JSONObject headersJson)
											throws JsonMappingException, JsonProcessingException {
		Map<String, String> headers = new ObjectMapper().readValue(headersJson.toJSONString(), HashMap.class);
		WebClient.RequestHeadersSpec<?> requestSpec = 
				WebClient.create(url)
						.method(method)
						.headers(httpHeaders-> {
							httpHeaders.setAll(headers);
						})
						.body(BodyInserters.fromPublisher(Mono.just(body), JSONObject.class));
						
		String response = requestSpec.retrieve()
										.bodyToMono(String.class)
										.doOnSuccess(resp->{
											System.out.println("Success case");
										})
										.doOnError(Exception.class, err->{
											System.out.println("Error callback:"+err.getMessage());
										})
										.block();
		LOG.info("\nResponse: " + response);
	}
}
