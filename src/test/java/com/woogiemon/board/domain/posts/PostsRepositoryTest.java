package com.woogiemon.board.domain.posts;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@BeforeEach
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

	@Test
	void BaseTimeEntity_등록() {

		//given
		LocalDateTime now =
			LocalDateTime.of(2022, 10, 1, 0, 0, 0);
		postsRepository.save(Posts.builder()
			.title("title")
			.content("content")
			.author("author")
			.build());

		//when
		List<Posts> postsList = postsRepository.findAll();

		//then
		Posts posts = postsList.get(0);

		System.out.println(
			">>>>>>>>> createdDate = " + posts.getCreatedDate() + ", modifiedDate = " + posts.getModifiedDate());

		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifiedDate()).isAfter(now);
	}
}