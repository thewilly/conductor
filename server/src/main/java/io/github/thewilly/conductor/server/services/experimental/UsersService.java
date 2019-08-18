package io.github.thewilly.conductor.server.services.experimental;

import io.github.thewilly.conductor.server.repositories.experimental.UsersRepository;
import io.github.thewilly.conductor.server.types.User;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public User findEmail(String email) { return usersRepository.findByEmail(email); }

    public Boolean addUser(String email, String password) {
        if(usersRepository.findByEmail(email) != null)
            return false;

        usersRepository.save(new User(null, email, new StrongPasswordEncryptor().encryptPassword(password)));
        return true;
    }

    public User auth(String email, String password) {
        User stored = usersRepository.findByEmail(email);
        if(stored != null && new StrongPasswordEncryptor().checkPassword(password, stored.getPassword()))
            return stored;
        return null;
    }
}
