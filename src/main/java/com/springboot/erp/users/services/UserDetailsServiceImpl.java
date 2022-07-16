package com.springboot.erp.users.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.erp.users.entitys.Role;
import com.springboot.erp.users.repositories.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.springboot.erp.users.entitys.User> appUser = repository.findByUsername(username);
        if (!appUser.isPresent()) {
            throw new UsernameNotFoundException("Usuario invalido");
        } else {
            Set<GrantedAuthority> grantList = new HashSet<GrantedAuthority>();
            for (Role role : appUser.get().getRoles()) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getDescription());
                grantList.add(grantedAuthority);
            }
            UserDetails user = (UserDetails) new User(username, appUser.get().getPassword(), grantList);
            return user;
        }
    }

}
