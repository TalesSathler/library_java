package com.library.library.services.Interface;

import com.library.library.models.Author;
import com.library.library.repositorys.AuthorRepository;

public interface AuthorServiceInterface {

    public AuthorRepository getRepository();

    public Author save(Author author);

    public boolean remove(Author author);

}
