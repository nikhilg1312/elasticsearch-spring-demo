package com.spirngboot.rest.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spirngboot.rest.entity.Files;
import com.spirngboot.rest.repository.FilesRepository;

@Service
public class FilesService {

	@Autowired
	private FilesRepository filesRepository;

	public String uploadFiles(Files newFile) throws IOException {
		return filesRepository.uploadDocumentToElastic(newFile);
	}
	
	public List<Files> getAllFilesInfo() {
		
		return filesRepository.findAllFilesDetailsFromElastic();
	}

	public List<Files> getFilesDataByName(String fileName) {
		return filesRepository.findAllFilesDataByNameFromElastic(fileName);
	}

	public List<Files> getFilesDataByNameAndContent(String fileName, String content) {
		return filesRepository.findAllFilesDataByNameAndContentFromElastic(fileName,content);
	}


}
