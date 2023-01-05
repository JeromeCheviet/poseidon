package com.poseidon.poseidon.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Class use to connect data of table users into an object
 */
@Entity
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least 1 digit")
    @Pattern(regexp = ".*\\p{Lower}.*", message = "Password must contain at least 1 lowercase")
    @Pattern(regexp = ".*\\p{Upper}.*", message = "Password must contain at least 1 uppercase")
    @Pattern(regexp = ".*\\p{Punct}.*", message = "Password must contain at least 1 symbol")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
