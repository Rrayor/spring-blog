package com.benjaminsimon.spring_blog.repository;

import com.benjaminsimon.spring_blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
