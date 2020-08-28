package com.spirngboot.rest.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spirngboot.rest.entity.Files;

@Repository
public interface FilesRepository {

	List<Files> findAllFilesDetailsFromElastic();

	List<Files> findAllFilesDataByNameFromElastic(String filesName);

	List<Files> findAllFilesDataByNameAndContentFromElastic(String fileName, String content);

	
}
