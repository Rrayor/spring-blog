package com.benjaminsimon.spring_blog.service;

import com.benjaminsimon.spring_blog.dto.PostDto;
import com.benjaminsimon.spring_blog.exception.PostNotFoundException;
import com.benjaminsimon.spring_blog.model.Post;
import com.benjaminsimon.spring_blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public void createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User username = authService.getCurrentUser()
                .orElseThrow(() -> new IllegalArgumentException("No user logged in"));
        post.setUserName(username.getUsername());
        post.setCreatedOn(Instant.now());

        postRepository.save(post);
    }

    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapFromPostToDto)
                .collect(toList());
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();

        postDto.setId(post.getId());
        postDto.setTitle(post.getContent());
        postDto.setUsername(post.getUserName());

        return postDto;
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("For id" + id));

        return mapFromPostToDto(post);
    }
}
