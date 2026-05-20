package mk.ukim.finki.wp.dailydebugger.repositories;



import mk.ukim.finki.wp.dailydebugger.models.User;
import mk.ukim.finki.wp.dailydebugger.models.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    List<UserHistory> findByUser(User user);
}