package com.esame.project;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws IOException, ParseException {
		DownloadDataset Download =  new DownloadDataset(); //scarica i file
		SpringApplication.run(ProjectApplication.class, args);
	} 
}
