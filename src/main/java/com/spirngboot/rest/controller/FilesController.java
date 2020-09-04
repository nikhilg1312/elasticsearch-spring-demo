package com.spirngboot.rest.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.spirngboot.rest.entity.Files;
import com.spirngboot.rest.service.FilesService;

@RestController
@RequestMapping(value = "/filesinfo")
public class FilesController {
	
	@Autowired
	private FilesService filesService;

	@PostMapping(value = "/savefile")
	public String saveFile() throws IOException {
		Files newFile = new Files();
		newFile.setName("file1");
		newFile.setTag("survey-q1");
		newFile.setType("csv");
		return filesService.uploadFiles(newFile);
	}

	@GetMapping(value ="/allfiles", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Files> getAllFiles(){
		return filesService.getAllFilesInfo();
	}
	
	@GetMapping(value ="/allfiles/{fileName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Files> getFileByName(@PathVariable String fileName){
		return filesService.getFilesDataByName(fileName);
	}
	
	@GetMapping(value ="/allfiles/{fileName}/{content}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Files> getFileByNameAndContent(@PathVariable String fileName, @PathVariable String content){
		return filesService.getFilesDataByNameAndContent(fileName, content);
	}
	
}
