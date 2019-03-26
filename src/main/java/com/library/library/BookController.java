package com.library.library;

import com.library.library.models.Book;
import com.library.library.services.Interface.BookServiceInterface;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/book")
@RestController
public class BookController {

    private BookServiceInterface bookService;

    public BookController (BookServiceInterface bookService) {
        this.bookService = bookService;
    }

    @GetMapping("get")
    public List<Book> getBook() {
        return bookService.getRepository().findAll();
    }

    @GetMapping("get/{bookId}")
    public Optional<Book> getBook(@PathVariable Long bookId) {
        return bookService.getRepository().findById(bookId);
    }

    @PostMapping("store")
    public String store(@Valid @RequestBody Book book) {
        try {
            bookService.save(book);
        } catch (RuntimeException $ex) {
            return $ex.getMessage();
        }

        return "Book stored with success!";
    }

    @PutMapping("update")
    public String update(@Valid @RequestBody Book book) {
        try {
            bookService.getRepository().findById(book.getBookId()).orElseThrow(() -> new Exception("Book not found!"));
            bookService.save(book);
        } catch (Exception $ex) {
            return $ex.getMessage();
        }

        return "Book updated with success!";
    }

    @DeleteMapping("delete/{bookId}")
    public String delete(@PathVariable Long bookId) throws Exception {
        try {
            Book book = bookService.getRepository().findById(bookId).orElseThrow(() -> new Exception("Book not found!"));
            bookService.getRepository().delete(book);
        } catch (Exception $ex) {
            return $ex.getMessage();
        }

        return "Book deleted with success!";
    }

}
