package com.woogiemon.board.service.posts;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.woogiemon.board.domain.posts.PostsRepository;
import com.woogiemon.board.web.dto.PostsSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}
}
