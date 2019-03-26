package com.library.library.services;

import com.library.library.models.Author;
import com.library.library.models.Book;
import com.library.library.repositorys.AuthorRepository;
import com.library.library.repositorys.BookRepository;
import com.library.library.services.Interface.BookServiceInterface;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ComponentScan
@EnableTransactionManagement
@Service
public class BookService implements BookServiceInterface {

    private BookRepository bookRepository;
    private AuthorService authorService;
    private EntityManager entityManager;


    public BookService (BookRepository bookRepository, AuthorService authorService, EntityManagerFactory entityManagerFactory) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public BookRepository getRepository () {
        return this.bookRepository;
    }

    @Override
    public Book save (Book book) throws RuntimeException {
        try {
            this.entityManager.getTransaction().begin();

            for (Author author : book.getAuthors()) {
                this.authorService.getRepository().findById(author.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found! ID: "+ author.getAuthorId()));
            }

            if (book.getBookId() != 0) {
                this.entityManager.merge(book);
            }
            else {
                this.entityManager.persist(book);
            }
            this.entityManager.flush();
        } catch (Exception $ex) {
            this.entityManager.getTransaction().rollback();
            throw new RuntimeException($ex.getMessage());
        }

        this.entityManager.getTransaction().commit();
        return book;
    }

    @Override
    public boolean remove(Book book) throws RuntimeException {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(book);
        this.entityManager.getTransaction().commit();
        return true;
    }

}
