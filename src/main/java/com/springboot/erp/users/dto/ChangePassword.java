package com.springboot.erp.users.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ChangePassword {

    @NotNull
    @Getter @Setter
    private Long id;

    @NotBlank(message = "La contraseña actual no debe estar vacía")
    @Getter @Setter
    private String currentPassword;

    @NotBlank(message = "La nueva contraseña no debe estar vacía")
    @Getter @Setter
    private String newPassword;

    @NotBlank(message = "La confirmación de la nueva contraseña no debe estar vacía")
    @Getter @Setter
    private String confirm;

    public ChangePassword(Long id) {
        this.id = id;
    }
}
