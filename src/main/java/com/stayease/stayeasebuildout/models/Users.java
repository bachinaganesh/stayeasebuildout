package com.stayease.stayeasebuildout.models;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Users implements UserDetails{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email shouble be valid")
    @NotBlank(message = "Email is madatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "password should be atleast 8 characters length")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}:;<>?,./]).*$", message = "Password must contain at least one uppercase letter, one number, and one special character")
    @Pattern(regexp = "^\\S+$", message = "Password must not contain spaces")
    private String password;
    
    private String firstName;
    private String lastName;
    private String role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of(new SimpleGrantedAuthority("ROLE_"+role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
