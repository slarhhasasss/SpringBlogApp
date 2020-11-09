package com.dimikmusik.testBlog.repos;


import com.dimikmusik.testBlog.models.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Long> {
}
