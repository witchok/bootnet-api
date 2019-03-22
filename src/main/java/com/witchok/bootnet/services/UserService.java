package com.witchok.bootnet.services;

import com.sun.security.auth.UserPrincipal;
import com.witchok.bootnet.domain.users.User;
import com.witchok.bootnet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser= userRepository.findUserByUsername(username);
        if(!optionalUser.isPresent()){
            throw new UsernameNotFoundException(String.format("User with username '%s' is not found", username));
        }else {
            User user = optionalUser.get();
            return new UserDetails() {
                final String ROLE_PREFIX = "ROLE_";

                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return user.getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX +role.getRoleValue()))
                            .collect(Collectors.toList());
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getUsername();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
        }
    }


}
