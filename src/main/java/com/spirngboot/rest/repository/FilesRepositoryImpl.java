package com.spirngboot.rest.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.elasticsearch.action.ActionListener;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirngboot.rest.entity.Files;


@Component
public class FilesRepositoryImpl implements FilesRepository  {


	@Autowired
	private ObjectMapper objectMapper;
	
	RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("localhost", 9200, "http")));

	@Override
	public String uploadDocumentToElastic(Files newFile) throws IOException {
		UUID uuid = UUID.randomUUID();
		newFile.setID(uuid.toString());

		IndexRequest request = new IndexRequest("filesinfo");
		request.id(newFile.getID());
		request.source(new ObjectMapper().writeValueAsString(newFile), XContentType.JSON);
		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
		System.out.println("response id: "+indexResponse.getId());
		return indexResponse.getResult().name();
	}

	@Override
	public List<Files> findAllFilesDetailsFromElastic() {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("filesinfo");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(searchSourceBuilder);
		List<Files> filesList = new ArrayList<>();
		SearchResponse searchResponse = null;
		try {
			searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
			if (searchResponse.getHits().getTotalHits().value > 0) {
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for (SearchHit hit : searchHit) {
					Map<String, Object> map = hit.getSourceAsMap();
					filesList.add(objectMapper.convertValue(map, Files.class));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filesList;
	}

	@Override
	public List<Files> findAllFilesDataByNameFromElastic(String filesName) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("filesinfo");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name", filesName)));
		searchRequest.source(searchSourceBuilder);
		List<Files> filesList = new ArrayList<>();
		
		try {
			SearchResponse searchResponse = null;
			searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
			if (searchResponse.getHits().getTotalHits().value > 0) {
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for (SearchHit hit : searchHit) {
					Map<String, Object> map = hit.getSourceAsMap();
					filesList.add(objectMapper.convertValue(map, Files.class));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filesList;
	}

	@Override
	public List<Files> findAllFilesDataByNameAndContentFromElastic(String fileName, String content) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("filesinfo");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("name", fileName))
				                                           .must(QueryBuilders.matchQuery("type", content)));
		searchRequest.source(searchSourceBuilder);
		List<Files> filesList = new ArrayList<>();
		
		try {
			SearchResponse searchResponse = null;
			searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
			if (searchResponse.getHits().getTotalHits().value > 0) {
				SearchHit[] searchHit = searchResponse.getHits().getHits();
				for (SearchHit hit : searchHit) {
					Map<String, Object> map = hit.getSourceAsMap();
					filesList.add(objectMapper.convertValue(map, Files.class));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filesList;
	}

}