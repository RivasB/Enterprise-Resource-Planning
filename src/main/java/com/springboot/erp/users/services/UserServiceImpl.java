package com.springboot.erp.users.services;

import java.util.Optional;

import javax.validation.Valid;

import com.springboot.erp.users.dto.ChangePassword;
import com.springboot.erp.users.entitys.User;
import com.springboot.erp.users.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Autowired
    BCryptPasswordEncoder cryptPasswordEncoder;

    @Override
    public Iterable<User> getAll() {
        return repository.findAll();
    }

    private boolean checkUsername(User user) throws Exception {
        Optional<User> userFound = repository.findByUsername(user.getUsername());
        if (userFound.isPresent()) {
            throw new Exception("Usuario no disponible");
        }
        return true;
    }

    private boolean checkPassword(User user) throws Exception {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }
        if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
            throw new Exception("Confirmar contraseña no debe estar vacío");
        }
        return true;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    @Override
    public User create(@Valid User user) throws Exception {
        if (checkPassword(user) && checkUsername(user)) {
            String encodePassword = cryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user = repository.save(user);
        }
        return user;
    }

    @Override
    public User update(@Valid User fromMap) throws Exception {
        User toMap = getById(fromMap.getId());
        map(fromMap, toMap);
        repository.save(toMap);
        return toMap;
    }

    protected void map(@Valid User fromMap, User toMap) {
        toMap.setName(fromMap.getName());
        toMap.setLastName(fromMap.getLastName());
        toMap.setUsername(fromMap.getUsername());
        toMap.setEmail(fromMap.getEmail());
        toMap.setRoles(fromMap.getRoles());
    }

    @Override
    public User getById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe"));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR')")
    @Override
    public void delete(Long id) throws Exception {
        User user = getById(id);
        repository.delete(user);
    }

    public boolean isLoggedUserADMIN() {
        return loggedUserHasRole("ROLE_ADMINISTRATOR");
    }

    public boolean loggedUserHasRole(String role) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails loggedUser = null;
        Object roles = null;
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;
            roles = loggedUser.getAuthorities().stream()
                    .filter(x -> role.equals(x.getAuthority()))
                    .findFirst().orElse(null); // loggedUser = null;
        }
        return roles != null ? true : false;
    }

    @Override
    public User changePassword(ChangePassword dto) throws Exception {
        User user = getById(dto.getId());
        boolean passwordMatch = cryptPasswordEncoder.matches(dto.getCurrentPassword(), user.getPassword());
        if (!isLoggedUserADMIN() && !passwordMatch) {
            throw new Exception("La contraseña actual no es correcta");
        }
        if (user.getPassword().equals(dto.getNewPassword())) {
            throw new Exception("La nueva contraseña debe ser distinta a la contraseña actual");
        }
        if (!dto.getNewPassword().equals(dto.getConfirm())) {
            throw new Exception("La confirmación de la nueva contraseña no es correcta");
        }
        String encodeNewPassword = cryptPasswordEncoder.encode(dto.getNewPassword());
        user.setPassword(encodeNewPassword);
        return repository.save(user);
    }

}
