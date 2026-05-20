package mk.ukim.finki.wp.dailydebugger.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "code_puzzles")
public class CodePuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String snippet; // AI генерираниот код со дефект

    @Column(nullable = false)
    private String solution; // Точниот одговор (пр. линијата каде е грешката или точниот збор)

    private LocalDate usedDate; // Датумот кога оваа загатка била активна

    public CodePuzzle() {}

    public CodePuzzle(String snippet, String solution) {
        this.snippet = snippet;
        this.solution = solution;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSnippet() { return snippet; }
    public void setSnippet(String snippet) { this.snippet = snippet; }
    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }
    public LocalDate getUsedDate() { return usedDate; }
    public void setUsedDate(LocalDate usedDate) { this.usedDate = usedDate; }
}