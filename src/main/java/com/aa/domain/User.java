package com.aa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 45, nullable = false)
    @NotBlank(message = "First name is required")
    @Length(min = 2, max = 45, message = "First name must have between 2 and 45 characters")
    private String firstName;
    @Column(length = 45, nullable = false)
    @NotBlank(message = "Last name is required")
    @Length(min = 2, max = 45, message = "Last name must have between 2 and 45 characters")
    private String lastName;
    @Column(length = 125, nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    @Length(min = 5, max = 125, message = "Email must have between 5 and 125 characters")
    @Email(message = "Email not valid")
    private String email;
    @Column(length = 64, nullable = false)
    @NotBlank(message = "Password is required")
    @Length(min = 8, max = 64, message = "Password must have between 8 and 64 characters")
    private String password;
    private boolean enabled;
    private boolean isNonLocked;
    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, boolean enabled, boolean isNonLocked, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.isNonLocked = isNonLocked;
        this.roles.add(role);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isNonLocked() {
        return isNonLocked;
    }

    public void setNonLocked(boolean nonLocked) {
        isNonLocked = nonLocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName=" + firstName +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", isNonLocked=" + isNonLocked +
                ", roles=" + roles +
                '}';
    }
}
