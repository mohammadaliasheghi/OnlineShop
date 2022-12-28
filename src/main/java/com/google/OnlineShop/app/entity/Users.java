package com.google.OnlineShop.app.entity;

import com.google.OnlineShop.config.annotation.Email;
import com.google.OnlineShop.config.annotation.Phone;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class Users implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Email
    @Column(name = "email", nullable = false
            , length = 500)
    private String email;

    @Column(name = "username")
    private String username;

    @Phone
    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "enabled")
    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
