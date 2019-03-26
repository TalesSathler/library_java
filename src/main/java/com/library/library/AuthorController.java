package com.library.library;

import com.library.library.models.Author;
import com.library.library.services.Interface.AuthorServiceInterface;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/author")
@RestController
public class AuthorController {

    private AuthorServiceInterface authorService;

    public AuthorController(AuthorServiceInterface authorService) {
        this.authorService = authorService;
    }

    @GetMapping("get")
    public List<Author> getAuthor() {
        return authorService.getRepository().findAll();
    }

    @GetMapping("get/{authorId}")
    public Optional<Author> getAuthor(@PathVariable Long authorId) {
        return authorService.getRepository().findById(authorId);
    }

    @PostMapping("store")
    public String store(@Valid @RequestBody Author author) {
        try {
            authorService.save(author);
        } catch (RuntimeException $ex) {
            return $ex.getMessage();
        }

        return "Author stored with success!";
    }

    @PutMapping("update")
    public String update(@Valid @RequestBody Author author) {
        try {
            authorService.getRepository().findById(author.getAuthorId()).orElseThrow(() -> new Exception("Author not found!"));
            authorService.save(author);
        } catch (Exception $ex) {
            return $ex.getMessage();
        }

        return "Author updated with success!";
    }

    @DeleteMapping("delete/{authorId}")
    public String delete(@PathVariable Long authorId) throws Exception {
        try {
            Author author = authorService.getRepository().findById(authorId).orElseThrow(() -> new Exception("Author not found!"));
            authorService.getRepository().delete(author);
        } catch (Exception $ex) {
            return $ex.getMessage();
        }

        return "Author deleted with success!";
    }

}
