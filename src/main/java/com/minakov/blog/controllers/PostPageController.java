package com.minakov.blog.controllers;

import com.minakov.blog.model.Comment;
import com.minakov.blog.model.Post;
import com.minakov.blog.model.User;
import com.minakov.blog.repository.CommentRepository;
import com.minakov.blog.repository.PostRepository;
import com.minakov.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostPageController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/post/{id}") // +
    public String post(@PathVariable("id") long id,
                       Model model,
                       @AuthenticationPrincipal User user,
                       Comment comment){

        Post post = postRepository.findById(id).get();
        postService.addViewer(user, post);
        model.addAttribute("post", post);
        model.addAttribute("comments", post.getComments());

        return "post-page";
    }

    @GetMapping("/post/{id}/edit")
    public String haikuEditForm(@PathVariable("id") long id,  Model model){
        model.addAttribute("post", postRepository.findById(id).get());
        return "post-control/edit-post";
    }

    @PostMapping("/post/{id}/edit")// +
    public String haikuEdit(@PathVariable("id") long id,
                            @RequestParam("tittle") String tittle,
                            @RequestParam("text") String text){
        Post post = postRepository.findById(id).get();
        post.setTittle(tittle);
        post.setFullText(text);
        post.setEdited(true);
        postRepository.save(post);


        return "redirect:/all-posts";

    }

    @PostMapping("/post/{id}/remove") // +
    public String postDelete(@PathVariable("id") long id,
                             @AuthenticationPrincipal User user){

        // to CommentService
        Post post = postRepository.findById(id).get();
        if(!(user != post.getUser())) {
            for (Comment comment : post.getComments()) {        // ?????????
                commentRepository.delete(comment);
            }
            postRepository.delete(postRepository.findById(id).get());
            // to CommentService
        }
        return "redirect:/all-posts";
    }

    @PostMapping("/post/{id}/like") // +
    public String like(@PathVariable("id") long id,
                       @AuthenticationPrincipal User user){
        if(user == null){
            return "redirect:/login";
        }
        Post post = postRepository.findById(id).get();
        if(!post.getLikes().contains(user)) {
            List<User> likes = post.getLikes();
            likes.add(user);
            post.setLikes(likes);
            postRepository.save(post);
        } else{
            List<User> likes = post.getLikes();
            likes.remove(user);
            post.setLikes(likes);
            postRepository.save(post);
        }
        return "redirect:/post/" + id;
    }
}
