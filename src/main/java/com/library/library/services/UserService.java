package com.library.library.services;

import com.library.library.models.User;
import com.library.library.repositorys.UserRepository;
import com.library.library.services.Interface.UserServiceInterface;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@ComponentScan
@EnableTransactionManagement
@Service
public class UserService implements UserServiceInterface {

    private UserRepository userRepository;
    private EntityManager entityManager;


    public UserService(UserRepository userRepository, EntityManagerFactory entityManagerFactory) {
        this.userRepository = userRepository;
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    public UserRepository getRepository () {
        return this.userRepository;
    }

    @Override
    public User save (User user) throws RuntimeException {
        try {
            this.entityManager.getTransaction().begin();

            if (user.getUserId() != 0) {
                this.entityManager.merge(user);
            }
            else {
                user.setUserPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
                this.entityManager.persist(user);
            }
            this.entityManager.flush();
        } catch (Exception $ex) {
            this.entityManager.getTransaction().rollback();
            throw new RuntimeException($ex.getMessage());
        }

        this.entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public boolean remove(User user) throws RuntimeException {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(user);
        this.entityManager.getTransaction().commit();
        return true;
    }

}
