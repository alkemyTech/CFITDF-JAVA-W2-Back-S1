package com.alkemy.wallet.alkywallet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CambiarContrasenaRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String nuevaContrasena;
}
