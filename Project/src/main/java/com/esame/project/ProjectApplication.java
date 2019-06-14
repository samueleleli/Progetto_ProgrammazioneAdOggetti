package com.esame.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws IOException, ParseException {

		SpringApplication.run(ProjectApplication.class, args);
		DownloadDataset classe =  new DownloadDataset();
	} 
}
