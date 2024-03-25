package com.innocodes.bloggingplatform.repository;

import com.innocodes.bloggingplatform.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
