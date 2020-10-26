package com.minakov.blog.controllers;



import com.minakov.blog.model.Post;
import com.minakov.blog.model.User;
import com.minakov.blog.repository.CommentRepository;
import com.minakov.blog.repository.PostRepository;
import com.minakov.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/") // +
    public String homePage(Model model){

        List<Post> list = postRepository.findAll();
        list.sort(((o1, o2) -> o2.getViewers().size() - o1.getViewers().size()));

        model.addAttribute("postList" , list);
        return "home";
    }

    @GetMapping("/all-posts") // +
    public String blog(Model model){
            Iterable<Post> post = postRepository.findAll();  // WHY ITERABLE ?
            model.addAttribute("postList", post);
        return "all-posts";
    }

     @GetMapping("post/add") // +
     public String addHaikuForm(Post post){
     return "post-control/add-post";
     }

     @PostMapping("post/add") // +
     public String addHaiku(@AuthenticationPrincipal User user,
                            @Valid Post post,
                            BindingResult bindingResult){

             if(bindingResult.hasErrors()){
                 return "post-control/add-post";
             }
         SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
         Date date = new Date();

         post.setUser(user);
         post.setDate(formatter.format(date));
         // TO SERVICE
         postRepository.save(post);
             return "redirect:/all-posts";
     }

     @PostMapping("search") // +
    public String search(@RequestParam("searchingText") String searchingText,
                         Model model){
        List<Post> list = postRepository.findAll();
        List<Post> trueList = new ArrayList<>();
        for(Post post : list){
            if(post.getFullText().contains(searchingText) || post.getTittle().contains(searchingText)){
                trueList.add(post);
            }
        }
        model.addAttribute("list", trueList);
        return "search";
     }





}
