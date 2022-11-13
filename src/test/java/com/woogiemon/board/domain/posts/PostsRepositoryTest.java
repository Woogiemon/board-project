package com.woogiemon.board.domain.posts;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@AfterEach
	void cleanAll() {
		postsRepository.deleteAll();
	}

	@Test
	void 게시글저장_불러오기() {

		//given
		String title = "title";
		String content = "content";

		postsRepository.save(Posts.builder()
			.title(title)
			.content(content)
			.author("taschen94@gmail.com")
			.build());

		//when
		List<Posts> postsList = postsRepository.findAll();

		//then
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}
}