package com.library.library.services;

import com.library.library.models.Author;
import com.library.library.repositorys.AuthorRepository;
import com.library.library.services.Interface.AuthorServiceInterface;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ComponentScan
@EnableTransactionManagement
@Service
public class AuthorService implements AuthorServiceInterface {

    private AuthorRepository authorRepository;
    private EntityManager entityManager;


    public AuthorService(AuthorRepository authorRepository, EntityManagerFactory entityManagerFactory) {
        this.authorRepository = authorRepository;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public AuthorRepository getRepository() {
        return this.authorRepository;
    }

    @Override
    public Author save(Author author) throws RuntimeException {
        try {
            this.entityManager.getTransaction().begin();

            if (author.getAuthorId() != 0) {
                this.entityManager.merge(author);
            } else {
                this.entityManager.persist(author);
            }
            this.entityManager.flush();
        } catch (Exception $ex) {
            this.entityManager.getTransaction().rollback();
            throw new RuntimeException($ex.getMessage());
        }

        this.entityManager.getTransaction().commit();
        return author;
    }

    @Override
    public boolean remove(Author author) throws RuntimeException {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(author);
        this.entityManager.getTransaction().commit();
        return true;
    }

}
