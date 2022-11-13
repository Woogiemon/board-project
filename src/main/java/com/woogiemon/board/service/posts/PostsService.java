package com.woogiemon.board.service.posts;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woogiemon.board.domain.posts.Posts;
import com.woogiemon.board.domain.posts.PostsRepository;
import com.woogiemon.board.web.dto.PostsResponseDto;
import com.woogiemon.board.web.dto.PostsSaveRequestDto;
import com.woogiemon.board.web.dto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}

	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) {
		Posts posts = postsRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

		posts.update(requestDto.getTitle(), requestDto.getContent());

		return id;
	}

	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

		return new PostsResponseDto(entity);
	}
}
