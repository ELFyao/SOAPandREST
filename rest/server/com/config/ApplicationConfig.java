package com.config;
import org.glassfish.jersey.server.ResourceConfig;
import com.controller.StudentController;

public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() 
    {
    	register(StudentController.class);
    }
}
