package org.example.controller;

import java.util.List;
import java.util.Optional;
import org.example.model.Content;
import org.example.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/content")
public class ContentController {
    @Autowired
    private ContentRepository contentRepository;
    
    @GetMapping
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Content> addContent(@RequestBody Content content) {
        try {
            Content newContent = contentRepository.save(content);
            return new ResponseEntity<>(newContent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable("id") Long id, @RequestBody Content content) {
        Optional<Content> findContent = contentRepository.findById(id);

        if (findContent.isPresent()) {
            Content newContent = findContent.get();

            newContent.setType(content.getType());
            newContent.setContent(content.getContent());
            newContent.setBlogPost(content.getBlogPost());

            return new ResponseEntity<>(contentRepository.save(newContent), HttpStatus.OK);
        } else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
