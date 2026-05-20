package mk.ukim.finki.wp.dailydebugger.repositories;


import mk.ukim.finki.wp.dailydebugger.models.CodePuzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface CodePuzzleRepository extends JpaRepository<CodePuzzle, Long> {
    Optional<CodePuzzle> findByUsedDate(LocalDate date);
}