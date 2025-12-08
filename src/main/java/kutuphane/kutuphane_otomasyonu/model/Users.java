package kutuphane.kutuphane_otomasyonu.model;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class Users {
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
private Long users_ID;
private String users_name;
@Column(unique = true)
private String email ;
private String password;
private String Role;

public Users(Long users_ID, String users_name, String email, String password, String role) {
    this.users_ID = users_ID;
    this.users_name = users_name;
    this.email = email;
    this.password = password;
    Role = role;
}

    public Users() {
    }
public Long getUsers_ID() {
    return users_ID;
}
public void setUsers_ID(Long users_ID) {
    this.users_ID = users_ID;
}
public String getUsers_name() {
    return users_name;
}
public void setUsers_name(String users_name) {
    this.users_name = users_name;
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
public String getRole() {
    return Role;
}
public void setRole(String role) {
    Role = role;
}
}
