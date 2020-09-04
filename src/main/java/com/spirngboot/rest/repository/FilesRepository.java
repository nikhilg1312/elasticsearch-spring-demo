package com.spirngboot.rest.repository;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;

import com.spirngboot.rest.entity.Files;

@Repository
public interface FilesRepository  {

	String uploadDocumentToElastic(Files newFile) throws IOException;

	List<Files> findAllFilesDetailsFromElastic();

	List<Files> findAllFilesDataByNameFromElastic(String filesName);

	List<Files> findAllFilesDataByNameAndContentFromElastic(String fileName, String content);

	
}
