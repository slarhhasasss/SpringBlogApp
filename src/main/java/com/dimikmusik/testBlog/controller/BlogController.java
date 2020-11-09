package com.dimikmusik.testBlog.controller;

import com.dimikmusik.testBlog.models.Post;
import com.dimikmusik.testBlog.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String mainBlog(
            Model model
    ) {
        Iterable<Post> allPosts = postRepository.findAll();
        model.addAttribute("allPosts", allPosts);
        return "main_blog";
    }

    @GetMapping("/blog/add")
    public String addArticleGet(Model model) {
        return "add_post";
    }

    @PostMapping("/blog/add")
    public String addArticlePost(
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            Model model
    ) {
        Post post = new Post(title, anons, full_text);

        //Добавление новой записи Post в бд
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{articleId}")
    public String fullArticle(
            @PathVariable(value = "articleId") long artId,
            Model model
    ) {
        if (!postRepository.existsById(artId)) {
            return "no_article_page";
        }

        Optional<Post> post =  postRepository.findById(artId);
        ArrayList<Post> result = new ArrayList<>();
        post.ifPresent(result::add);
        model.addAttribute("post", result);
        model.addAttribute("title", result.get(0).getTitle());
        return "blog-details";
    }

}
