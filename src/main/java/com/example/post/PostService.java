package com.example.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto createPost(PostRequestDto dto) {
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        return PostResponseDto.of(postRepository.save(post));
    }

    @Transactional
    public List<PostResponseDto> getPosts() {
        return postRepository.findAll().stream().map(PostResponseDto::of).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        return PostResponseDto.of(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto dto) {
        findPost(id);
        Post updatedPost = Post.builder()
                .id(id)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        return PostResponseDto.of(postRepository.save(updatedPost));
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = findPost(id);
        postRepository.delete(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Post not found")
        );
    }
}
