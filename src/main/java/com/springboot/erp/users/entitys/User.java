package com.springboot.erp.users.entitys;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Getter @Setter
    private Long id;
    
    @Column
    @NotBlank(message = "El campo <Nombre> no debe estar vacio")
    @Getter @Setter
    private String name;

    @Column
    @NotBlank(message = "El campo <Apellidos> no debe estar vacio")
    @Getter @Setter
    private String lastName;

    @Column(unique=true)
    @NotBlank(message = "El campo <usuario> no debe estar vacio")
    @Getter @Setter
    private String username;

    @Column(unique=true)
    @NotBlank(message = "El campo <Email> no debe estar vacio")
    @Email(message = "Debe introducir un Email válido")
    @Getter @Setter
    private String email;

    @Column
    @NotBlank(message = "Debe introducir una contraseña")
    @Getter @Setter
    private String password;

    @Transient
    @Getter @Setter
    private String confirmPassword;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Getter @Setter
    private Set<Role> roles;
    
}
