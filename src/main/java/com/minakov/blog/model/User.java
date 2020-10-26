package com.minakov.blog.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=5, message = "at least 5")
    private String username;
    @Size(min=5, message = "at least 5")
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;

    @ManyToMany(mappedBy = "viewers" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> viewed;  // TO SET
    @ManyToMany(mappedBy = "likes" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> liked; // TO SET
    private String date;     // TO DATE



    @Override
    public boolean equals(Object obj) {
        return this.username.equals(((User) obj).getUsername());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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

    @Override
    public String toString() {
        return username;
    }

}