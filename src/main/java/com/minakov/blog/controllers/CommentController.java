package com.minakov.blog.controllers;

import com.minakov.blog.model.Comment;
import com.minakov.blog.model.Post;
import com.minakov.blog.model.User;
import com.minakov.blog.repository.CommentRepository;
import com.minakov.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CommentController {

    PostRepository postRepository;
    CommentRepository commentRepository;

    @Autowired
    public CommentController(PostRepository postRepository,CommentRepository commentRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/post/{id}/comment")// +
    public String comment(@PathVariable("id") long id,
                          @AuthenticationPrincipal User user,
                          @Valid Comment comment,
                          BindingResult bindingResult,
                          Model model){
        if(user == null){
            return "redirect:/login";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("post", postRepository.findById(id).get());
            return "post-page";
        }

        comment.setUser(user);
        comment.setPost(postRepository.findById(id).get());
        commentRepository.save(comment);

        model.addAttribute("post" , postRepository.findById(id).get());
        return "redirect:/post/" + id;
    }

    @PostMapping("/comment/{id}/remove") // +   ADD /post/{id}/comment to the path
    public String comment(@PathVariable("id") long id){
        Post post = commentRepository.findById(id).get().getPost();
        commentRepository.delete(commentRepository.findById(id).get());
        return "redirect:/post/" + post.getId();
    }
}
