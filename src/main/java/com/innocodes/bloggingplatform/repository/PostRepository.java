package com.innocodes.bloggingplatform.repository;

import com.innocodes.bloggingplatform.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
