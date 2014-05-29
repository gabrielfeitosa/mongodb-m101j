package com.gabrielfeitosa;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class SparkFormHandling {

	public static void main(String[] args) {
		Spark.get(new Route("/") {
			
			@Override
			public Object handle(Request arg0, Response arg1) {
				Configuration configuration = new Configuration();
				configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");
				try {
					Template template = configuration.getTemplate("fruitPicker.ftl");
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("fruits", Arrays.asList("apple", "orange", "banana", "peach"));
					StringWriter writer = new StringWriter();
					template.process(map, writer );
					return writer;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return null;
			}
		});
		
		Spark.post(new Route("/favorite_fruit") {
            @Override
            public Object handle(final Request request, final Response response) {
                final String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why don't you pick one?";
                }
                else {
                    return "Your favorite fruit is " + fruit;
                }
            }
        });
	}
}
