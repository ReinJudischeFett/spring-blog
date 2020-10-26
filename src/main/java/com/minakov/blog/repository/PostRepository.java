package com.minakov.blog.repository;

import com.minakov.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
}
/** server.port=8080
 spring.jpa.hibernate.ddl-auto=update
 spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:8889/haikusea
 spring.datasource.username=root
 spring.datasource.password=root  */
