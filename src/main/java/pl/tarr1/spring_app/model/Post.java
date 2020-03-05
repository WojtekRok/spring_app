package pl.tarr1.spring_app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pl.tarr1.spring_app.model.enums.Category;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    @Type(type = "text")    //rozszerza typ varchar (255) dla String na text
    private String content;
    @Enumerated
    private Category category;
    private LocalDateTime submissionDate;

    // każdy post ma dokładnie jednego autora -> obiekt klasy User
    @ManyToOne
    private User user;


    public Post(String title, String content, Category category, User user, LocalDateTime submissionDate) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
        this.submissionDate = submissionDate;
    }
}
