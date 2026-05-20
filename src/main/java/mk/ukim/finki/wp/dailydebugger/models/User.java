package mk.ukim.finki.wp.dailydebugger.models;// User.java

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int score = 0; // Вкупно поени за Leaderboard

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Гетери и сетери
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}