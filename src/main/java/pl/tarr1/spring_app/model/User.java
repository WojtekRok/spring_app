package pl.tarr1.spring_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tarr1.spring_app.model.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity //mapowanie klasy na encję bazy danych
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //IDENTITY - auto inkrementacja w tabeli, AUTO - auto inkrementacja globalna
    private Long userId;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private Boolean status;
    @Enumerated
    private Role role;
    // użytkownik może zapisać wiele postów zapisanych w jego liście
    @JsonIgnore //zignoruj to pole w publikowanym API
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();


    public User(String name, String lastName, String email, String password, LocalDateTime registrationDate, Boolean status, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;
        this.role = role;
    }
}
