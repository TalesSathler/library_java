package com.library.library.services.Interface;

import com.library.library.models.Author;
import com.library.library.models.Book;
import com.library.library.repositorys.BookRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {

    public BookRepository getRepository();

    public Book save (Book book);

    public boolean remove (Book book);

}
