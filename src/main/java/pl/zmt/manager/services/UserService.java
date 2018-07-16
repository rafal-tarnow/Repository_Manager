package pl.zmt.manager.services;

import pl.zmt.manager.entities.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
