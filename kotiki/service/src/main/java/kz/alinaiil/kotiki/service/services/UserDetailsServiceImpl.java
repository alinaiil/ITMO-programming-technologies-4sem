package kz.alinaiil.kotiki.service.services;

import kz.alinaiil.kotiki.data.models.User;
import kz.alinaiil.kotiki.data.repositories.UserRepository;
import kz.alinaiil.kotiki.service.userdetails.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}