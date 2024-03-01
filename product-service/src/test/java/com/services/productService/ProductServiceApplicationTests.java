package com.services.productService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.productService.Dtos.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	// Create a MongoContainer, with the docker image"name","version"
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.0.10"); //using static because i want it to belong to the class

	// im setting the properties dynamically
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry)
	{
		dynamicPropertyRegistry.add("spring.data.mongodb.url=mongodb",mongoDBContainer::getReplicaSetUrl);
	}

	// need to perfom mocks -> inject and also autoConfigThem

	@Autowired
	private MockMvc mockMvc;

	//Convert a POJO into a String
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateProduct() throws Exception {
		var getProductReq = getProductRequest();
		var getProdectReqString = objectMapper.writeValueAsString(getProductReq);

		//performing mocks
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
						.content(getProdectReqString) // takes a string argument
				).andExpect(status().isCreated());
	}

	//Requesting product POJO
	// will have t transform it into a string
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 15+")
				.description("Iphone")
				.price(BigDecimal.valueOf(30500))
				.build();
	}


}
