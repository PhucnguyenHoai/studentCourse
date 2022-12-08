package org.example.controller;

import java.util.List;
import java.util.Optional;
import org.example.model.BlogPost;
import org.example.model.Course;
import org.example.repository.BlogPostRepository;
import org.example.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/blogpost")
@CrossOrigin
public class BlogPostController {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping()
    public List<BlogPost> getAll() {
        return blogPostRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<BlogPost> addBlogPost(@RequestBody BlogPost blogPost) {
        try {
            Course course = courseRepository.findById(blogPost.getCourse().getId()).get();
            course.setCount(course.getCount() + 1L);
            courseRepository.save(course);
            BlogPost newBlogPost = blogPostRepository.save(blogPost);
            return new ResponseEntity<>(newBlogPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable("id") Long id, @RequestBody BlogPost blogPost) {
        Optional<BlogPost> findBlogPost = blogPostRepository.findById(id);

        if (findBlogPost.isPresent()) {
            BlogPost newBlogPost = findBlogPost.get();

            newBlogPost.setName(blogPost.getName());
            newBlogPost.setImage(blogPost.getImage());
            newBlogPost.setUrl(blogPost.getUrl());
            newBlogPost.setDescription(blogPost.getDescription());
            newBlogPost.setCourse(blogPost.getCourse());

            return new ResponseEntity<>(blogPostRepository.save(newBlogPost), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
