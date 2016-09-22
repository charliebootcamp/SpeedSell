package com.bootcamp.portal.mytests;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import junit.framework.Assert;
import junit.framework.TestResult;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import com.bootcamp.portal.domain.Category;
import com.bootcamp.portal.web.controller.CategoryController;


public class TestCategoryCtrl extends TestResult{
	CategoryController categoryCtrl = new CategoryController();
	ResponseEntity<Category> category;
	Long Id = 5L;
	Long parentId = 1L;
	//CategoryDto categoryDto = new CategoryDto();
	//CategoryDAO categoryDAO = new CategoryDAO();
	
	//test getCategories()
	@Test
	public void testGetCategories() 
			throws InterruptedException, ClientProtocolException, IOException, JSONException{
		//String json = "[{\"id\":1,\"name\":\"electronics\",\"subcategories\":[{\"id\":5,\"name\":\"smartphones\",\"pid\":5},{\"id\":6,\"name\":\"computers\",\"pid\":6},{\"id\":7,\"name\":\"tvs\",\"pid\":7},{\"id\":136,\"name\":\"camera and video\",\"pid\":136}],\"pid\":1},{\"id\":2,\"name\":\"pets\",\"subcategories\":[{\"id\":12,\"name\":\"agricultural animals\",\"pid\":12},{\"id\":22,\"name\":\"dogs\",\"pid\":22},{\"id\":30,\"name\":\"cats\",\"pid\":30},{\"id\":138,\"name\":\"birds\",\"pid\":138}],\"pid\":2},{\"id\":3,\"name\":\"sport\",\"subcategories\":[{\"id\":13,\"name\":\"bicycles\",\"pid\":13},{\"id\":14,\"name\":\"skis and snowboards\",\"pid\":14},{\"id\":15,\"name\":\"football\",\"pid\":15},{\"id\":16,\"name\":\"tourism\",\"pid\":16}],\"pid\":3},{\"id\":4,\"name\":\"transport\",\"subcategories\":[{\"id\":17,\"name\":\"cars\",\"pid\":17},{\"id\":18,\"name\":\"motorcycles\",\"pid\":18},{\"id\":19,\"name\":\"buses\",\"pid\":19},{\"id\":20,\"name\":\"parts and accessories\",\"pid\":20}],\"pid\":4}]";
		String response = getCategories();
		Assert.assertTrue(response.startsWith("[{\"") && response.endsWith("}]") && response.contains(":")
				&& response.contains("id") && response.contains("name") && response.contains("subcategories")
				);
		//Assert.assertTrue(response.replace(" ", "").equals(json.replace(" ", "")));
		System.out.println("DEBUG1: "+ response);
	}

	private static String getCategories() throws IOException{
		String url = "http://localhost:8092/categories";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.addRequestProperty("Authorization", "Basic key");
		System.out.println("\nSending 'GET' request to URL : " + url);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine())!=null) {
			response.append(inputLine);
		}
		in.close();
		String list = response.toString();
		System.out.println(list);
		return list;
	}
	
	
}
