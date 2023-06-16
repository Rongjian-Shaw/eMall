package com.emall.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EMallSearchApplicationTests {

	@Autowired
	private ElasticsearchClient elasticsearchClient;

	@Test
	public void contextLoads() {
		System.out.println(elasticsearchClient);
	}

	/**
	 * 测试ES数据
	 * 更新也可以
	 */
	@Test
	public void indexData() throws IOException {

		User user = new User();
		user.setUserName("zhangsan");
		user.setAge(18);
		user.setGender("男");

		IndexRequest<User> request = IndexRequest.of(i -> i
				.index("users")
				.id("1")
				.document(user)
		);

		IndexResponse response = elasticsearchClient.index(request);

	}

	@Data
	class User {
		private String userName;
		private String gender;
		private Integer age;
	}

}
