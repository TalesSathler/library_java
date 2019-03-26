package com.library.library.services.Interface;

import com.library.library.models.User;
import com.library.library.repositorys.UserRepository;

public interface UserServiceInterface {

    public UserRepository getRepository();

    public User save(User user);

    public boolean remove(User user);

}
