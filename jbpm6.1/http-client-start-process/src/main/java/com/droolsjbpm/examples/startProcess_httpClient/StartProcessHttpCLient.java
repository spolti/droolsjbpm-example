package com.droolsjbpm.examples.startProcess_httpClient;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.kie.services.client.serialization.jaxb.impl.process.JaxbProcessInstanceResponse;

public class StartProcessHttpCLient {

	private static String DEPLOYMENT_ID = "deployment_id";
	private static String PROC_DEF_ID = "definition_id";
	private static String USERNAME = "user";
	private static String PASSWORD = "pass";
	private static HttpClient HTTP_CLIENT;
	private static BasicScheme SCHEME;
	private static org.apache.http.auth.Credentials CREDENTIALS;
	private static HttpPost HTTP_POST;
	private static Header AUTHORIZATION_HEADER;
	
	private static 	String restUrl = "http://localhost:8080/business-central/rest/runtime/"	+ DEPLOYMENT_ID + "/process/" + PROC_DEF_ID + "/start";

	public static <V> void main(String args[]) throws ClientProtocolException,	IOException {

		//Process_variable_name - Process_variable_value
		Map<String, Object> map = new HashMap<>();
		map.put("pVar", "MyTest");
		
		try {
			
			HTTP_CLIENT = new DefaultHttpClient();
			CREDENTIALS = new UsernamePasswordCredentials(USERNAME, PASSWORD);
			SCHEME = new BasicScheme();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//adding process variable
			String params = "?";
			Set<Entry<String, Object>> valueMap = map.entrySet();
			
			for (@SuppressWarnings("rawtypes") Entry value : valueMap){
				params += "map_" + value.getKey() + "=" + value.getValue() + "&";
			}
			if (!params.isEmpty()) {
				restUrl += params;
				System.out.println("RestURL " + restUrl);
			}
			
			
			HTTP_POST = new HttpPost(restUrl);
			AUTHORIZATION_HEADER = SCHEME.authenticate(CREDENTIALS, HTTP_POST);
			HTTP_POST.addHeader(AUTHORIZATION_HEADER);
			
			HttpResponse response = (HttpResponse) HTTP_CLIENT.execute(HTTP_POST);
			System.out.println("Response; " + response);
			
            HttpEntity entity = response.getEntity();            
            if (entity != null) {
                 String output = EntityUtils.toString(entity);
                 responseProcess(output);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void responseProcess(String output) {
	       try {
	            JAXBContext jaxbContext = JAXBContext.newInstance(JaxbProcessInstanceResponse.class);
	            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	            JaxbProcessInstanceResponse processResponse = (JaxbProcessInstanceResponse) unmarshaller.unmarshal(new StringReader(output));
	            System.out.println("processResponse: " + processResponse);
	            System.out.println("id: " + processResponse.getId());
	            System.out.println("process id: " + processResponse.getProcessId());
	            System.out.println("process state: " + processResponse.getState());
	       } catch (Exception e) {
	            e.printStackTrace();
	       }
	  }
	
}