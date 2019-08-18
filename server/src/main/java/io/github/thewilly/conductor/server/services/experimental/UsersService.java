package io.github.thewilly.conductor.server.services.experimental;

import io.github.thewilly.conductor.server.repositories.experimental.UsersRepository;
import io.github.thewilly.conductor.server.types.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public User findEmail(String email) { return usersRepository.findByEmail(email); }

    public Boolean addUser(User user) {
        if(usersRepository.findByEmail(user.getEmail()) != null)
            return false;
        usersRepository.save(user);
        return true;
    }

    public User auth(String email, String password) {
        User stored = usersRepository.findByEmail(email);
        if(stored != null && stored.equals(password))
            return stored;
        return null;
    }
}
