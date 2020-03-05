package pl.tarr1.spring_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Category;
import pl.tarr1.spring_app.repository.PostRepository;
import pl.tarr1.spring_app.service.PostService;
import pl.tarr1.spring_app.service.UserService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// restcontroller publikuje REST API ->
@RestController // mapowanie żądań protokołu html -> GET, POST, PUT, DELETE
public class EndPointController {
        private UserService userService;
        private PostService postService;

        @Autowired
        public EndPointController(UserService userService, PostService postService) {
                this.userService = userService;
                this.postService = postService;
        }


        // metoda do rejestracji użytkowników
        @PostMapping("/registration")
        public boolean  registerUser (
                String name, String lastName, String email, String password)
        {
            // logika biznesowa zapisana w klasie UserService
            return userService.registerUser(name, lastName, email, password);
        }
        @GetMapping("/users")
        public List<User> getAllUsers () {
                return userService.getAllUsers();
        }
        @GetMapping("/users/{id}")  // w nawiasach klamrowych {} część zmienna ścieżski
        public User getUserById (@PathVariable("id") Long id) {
                Optional<User> userOptional = userService.getUserById(id);
                if(userOptional.isPresent()) {                  // sprawdzam czy optional nie zawiera null
                        return userOptional.get();
                }
                return null;
        }
        @GetMapping("userByEmail")
        public User getUserByEmail (@RequestParam("email") String email) {      //RequestParam przekazuje dane w parametrach protokołu http
               return userService.getUserByEmail(email).orElse(null);
        }
        @PutMapping("/changepassword")
        public String setNewPassword (
                @RequestParam("userId") Long userId, @RequestParam("newPassword") String newPassword,
                @RequestParam("confirmPassword") String confirmPassword
        ) {
               return userService.setNewPassword(userId,newPassword,confirmPassword);
        }
        @DeleteMapping("/deleteUser")
        public boolean deleteUserById (@RequestParam("userId") Long userId) {
                return userService.deleteUserById(userId);
        }
        @PostMapping("/addPost")
        public Post addPost (@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam Category category,
                             @RequestParam Long userId) {
                return postService.addPostByUser(title, content, category, userId);
        }
        @GetMapping("/posts/{postId}")
        public Post getPostById (@PathVariable("postId") Long postId) {
                return postService.findPostById(postId);
        }
        @DeleteMapping("deletePost")
        public boolean deletePostById (@RequestParam("postId") Long postId){
                return postService.deletePostById(postId);
        }
        @PutMapping("/updatePost")
        public Post updatePost (@RequestParam Long postId,
                                @RequestParam String title,
                                @RequestParam String content,
                                @RequestParam Category category){
                return postService.updatePost(postId, title, content, category);
        }
        @GetMapping("/posts")
        public List<Post> getPostsOrderBySubmissionDateDesc () {
                return postService.getAllPostsOrderBySubmissionDateDesc();
        }
        @GetMapping("/postsByCategory/{category}")
        public List<Post> getPostsByCategoryOrderBySubmissionDateDesc (@PathVariable("category") Category category) {
                return postService.getPostsByCategoryOrderBySubmissionDateDesc(category);
        }
        @GetMapping("/groupPostsIntoCategory")
        public Map<String, String> groupPostsIntoCategory () {
              return postService.groupPostsIntoCategory();
        }



}
