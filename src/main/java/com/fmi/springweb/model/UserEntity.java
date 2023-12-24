package com.fmi.springweb.model;

import com.fmi.springweb.constants.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name="users")
@Getter
@Setter
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue
    private Long userId;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String role;
    private Float funds;
    private Date dateCreated;
    private Date lastRecordUpdate;
    @OneToMany
    private List<OrderEntity> orders;

    public UserEntity () {}

    public UserEntity(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role != null ? role : Role.USER;
        this.funds = 0.0F;
        this.dateCreated = new Date();
        this.lastRecordUpdate = new Date();
        this.orders = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
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
        return false;
    }

    public void addNewOrderToUser(OrderEntity order) {
        this.orders.add(order);
    }
}
