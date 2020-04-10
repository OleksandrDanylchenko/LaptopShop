package ua.alexd.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.alexd.repos.UserRepo;

@Service
public class ShopUserDetails implements UserDetailsService {
    private final UserRepo userRepo;

    public ShopUserDetails(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
