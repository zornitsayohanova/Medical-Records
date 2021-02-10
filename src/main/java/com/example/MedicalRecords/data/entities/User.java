package com.example.MedicalRecords.data.entities;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Entity;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseEntity implements UserDetails {
    @Column
    private String username;

    @Column
    private String password;

    @Column
    private boolean isAccountNonExpired;

    @Column
    private boolean isAccountNonLocked;

    @Column
    private boolean isCredentialsNonExpired;

    @Column
    private boolean isEnabled;

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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> authorities;
}