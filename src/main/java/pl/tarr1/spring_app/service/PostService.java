package pl.tarr1.spring_app.service;

import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Category;
import pl.tarr1.spring_app.repository.PostRepository;
import pl.tarr1.spring_app.repository.UserRepository;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    // dodawanie posta przez użytkownika
    public Post addPostByUser (String title, String content, Category category, Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
                Post post = new Post (title, content, category, user.get(), LocalDateTime.now());
                postRepository.save(post);      //insert into post values (?, ? , ? ,?)
                return post;
        }

        return null;

    }

    // wyszukiwanie posta po kluczu głównym
    public Post findPostById (Long postId) {
        if(postRepository.findById(postId).isPresent()){
            return postRepository.findById(postId).get();  // SELECT * FROM post WHERE post_id = ?
        }
        return null;
    }

    // usuwanie posta
    public boolean deletePostById (Long postId) {
       if(postRepository.findById(postId).isPresent()) {
           postRepository.deleteById(postId);
           return true;
       }
        return false;
    }

    // zmiana danych posta (edit)
    public Post updatePost (Long postId, String title, String content, Category category) {
        if(postRepository.findById(postId).isPresent()) {
            Post postToUpdate = postRepository.findById(postId).get();
            postToUpdate.setTitle(title);
            postToUpdate.setCategory(category);
            postToUpdate.setContent(content);
            postRepository.save(postToUpdate);      // UPDATE post SET title = ?, content = ?, category = ?, WHERE post_id = ?

            return postToUpdate;
        }
        return null;
    }

    // pobranie wszystkich postów z bloga posortowanych po dacie dodania malejąco
    public List<Post> getAllPostsOrderBySubmissionDateDesc () {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "submissionDate"));
    }

    // zwróć posty danej kategorii posortowane po dacie dodania
    public List<Post> getPostsByCategoryOrderBySubmissionDateDesc(Category category) {
        return postRepository.findAllByCategoryOrderBySubmissionDateDesc(category);
    }
    // wynik zapytania w adnotacji @Query
    public Map<String, String> groupPostsIntoCategory() {
        Map<String, String> groupedMap = new HashMap<>();
        for (Object[] groupedObjects : postRepository.groupPostsIntoCategory()) {
            groupedMap.put(Category.values()[(Integer) groupedObjects[0]].name(), (String) groupedObjects[1]);
        }
        return groupedMap;
    }


}
