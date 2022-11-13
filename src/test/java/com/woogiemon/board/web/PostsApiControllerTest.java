package com.woogiemon.board.web;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.woogiemon.board.domain.posts.Posts;
import com.woogiemon.board.domain.posts.PostsRepository;
import com.woogiemon.board.web.dto.PostsSaveRequestDto;
import com.woogiemon.board.web.dto.PostsUpdateRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@BeforeEach
	public void cleanAll() {
		postsRepository.deleteAll();
	}

	@Test
	void 게시글등록() {

		//given
		String title = "title";
		String content = "content";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
			.title(title)
			.content(content)
			.author("author")
			.build();

		String url = "http://localhost:" + port + "/posts";

		//when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> postsList = postsRepository.findAll();
		assertThat(postsList.get(0).getTitle()).isEqualTo(title);
		assertThat(postsList.get(0).getContent()).isEqualTo(content);
	}

	@Test
	void 게시글수정() {

		//given
		Posts savedPosts = postsRepository.save(Posts.builder()
			.title("title")
			.content("content")
			.author("author")
			.build());

		Long updateId = savedPosts.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";

		PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
			.title(expectedTitle)
			.content(expectedContent)
			.build();

		String url = "http://localhost:" + port + "/posts/" + updateId;

		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

		//when
		ResponseEntity<Long> responseEntity = restTemplate
			.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> postsList = postsRepository.findAll();
		assertThat(postsList.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);
	}
}