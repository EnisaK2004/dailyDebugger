package mk.ukim.finki.wp.dailydebugger.models;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_history")
public class UserHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "puzzle_id", nullable = false)
    private CodePuzzle puzzle;

    private LocalDate solvedDate;
    private boolean correct;

    public UserHistory() {}

    public UserHistory(User user, CodePuzzle puzzle, LocalDate solvedDate, boolean correct) {
        this.user = user;
        this.puzzle = puzzle;
        this.solvedDate = solvedDate;
        this.correct = correct;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public CodePuzzle getPuzzle() { return puzzle; }
    public void setPuzzle(CodePuzzle puzzle) { this.puzzle = puzzle; }
    public LocalDate getSolvedDate() { return solvedDate; }
    public void setSolvedDate(LocalDate solvedDate) { this.solvedDate = solvedDate; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
}
