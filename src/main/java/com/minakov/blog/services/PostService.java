package com.minakov.blog.services;

import com.minakov.blog.model.Post;
import com.minakov.blog.model.User;
import com.minakov.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }




    public void addViewer(User user, Post post){
        if(user != null){
            if(!post.getViewers().contains(user)){
                List<User> list = post.getViewers();
                list.add(user);
                post.setViewers(list);
                postRepository.save(post);
            }
        }
    }


}
