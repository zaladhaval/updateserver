package com.eavira.wecore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class UpdateBuildApplication {
	
	 public static void main(String[] args) {
	        SpringApplicationBuilder builder = new SpringApplicationBuilder(UpdateBuildApplication.class);
	        builder.headless(false);
	        builder.run(args);
	    }
	 
	 @Component
	    public class MyRunner implements CommandLineRunner {
	    	
	    	public void run(String... args) throws Exception {
	        	new LabelInfoForm();
	    	}

	    }

}
