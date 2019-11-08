package com.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class Restclient {
	private WebTarget target;
	public Restclient() 
	{
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
	    target = client.target(getBaseURI());
	}
	public boolean SendEmail(String address, String msg) 
	{
		String plainAnswer = target.path("rest").
                path("Services").
                path("SendEmail").
                path(address).
                path(msg).
                request().
                accept(MediaType.TEXT_PLAIN).
                get(Response.class).
                toString();
		if (plainAnswer.equals("Y")) 
		{
			return true;
		}
		return false;
	}
	public boolean ValidateEmailAddress(String address) 
	{
		String plainAnswer = target.path("rest").
                path("Services").
                path("valid").
                path(address).
                request().
                accept(MediaType.TEXT_PLAIN).
                get(Response.class).
                toString();
		if (plainAnswer.equals("N")) 
		{
			return false;
		}
		return true;
	}
	public boolean SendEmailBatch(List<String> list, String msg)
	{
		StringBuffer stb= new StringBuffer();
	    stb.append("?addrs="+list.get(0));
	    for(int i = 1; i < list.size(); i++ ) 
	    {
	    	stb.append(",");
	    	stb.append(list.get(i));
	    }
		String plainAnswer = target.path("rest").
                path("Services").
                path("valid").
                path(msg+stb.toString()).
                request().
                accept(MediaType.TEXT_PLAIN).
                get(Response.class).
                toString();
		if (plainAnswer.equals("N")) 
		{
			return false;
		}
		return true;
	}
	 private static URI getBaseURI() {
	        return UriBuilder.fromUri("http://localhost:8080/REST3").build();
	    }
}
