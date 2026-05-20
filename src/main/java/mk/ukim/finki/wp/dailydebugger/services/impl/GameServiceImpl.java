package mk.ukim.finki.wp.dailydebugger.services.impl;


import mk.ukim.finki.wp.dailydebugger.models.CodePuzzle;
import mk.ukim.finki.wp.dailydebugger.models.User;
import mk.ukim.finki.wp.dailydebugger.models.UserHistory;
import mk.ukim.finki.wp.dailydebugger.repositories.CodePuzzleRepository;
import mk.ukim.finki.wp.dailydebugger.repositories.UserHistoryRepository;
import mk.ukim.finki.wp.dailydebugger.repositories.UserRepository;
import mk.ukim.finki.wp.dailydebugger.services.GameService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final CodePuzzleRepository puzzleRepository;
    private final UserRepository userRepository;
    private final UserHistoryRepository historyRepository;

    public GameServiceImpl(CodePuzzleRepository puzzleRepository, UserRepository userRepository, UserHistoryRepository historyRepository) {
        this.puzzleRepository = puzzleRepository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public CodePuzzle getDailyPuzzle() {
        LocalDate today = LocalDate.now();

        // Прво проверуваме дали веќе имаме доделено код за денес
        Optional<CodePuzzle> existing = puzzleRepository.findByUsedDate(today);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Ако нема, пресметуваме индекс базиран на денот од годината (1-365)
        long totalPuzzles = puzzleRepository.count();
        if (totalPuzzles == 0) {
            throw new RuntimeException("Базата со кодови е празна!");
        }

        int dayOfYear = today.getDayOfYear();
        long targetId = (dayOfYear % totalPuzzles) + 1; // Обезбедува валиден ID од 1 до 100

        CodePuzzle puzzle = puzzleRepository.findById(targetId)
                .orElse(puzzleRepository.findAll().get(0)); // Fallback на првиот ако нешто фали

        puzzle.setUsedDate(today);
        puzzleRepository.save(puzzle);
        return puzzle;
    }

    @Override
    public boolean submitGuess(String username, String guess) {
        CodePuzzle currentPuzzle = getDailyPuzzle();
        boolean isCorrect = currentPuzzle.getSolution().trim().equalsIgnoreCase(guess.trim());

        // Ако корисникот не е анонимен (т.е. пратил корисничко име), зачувуваме во база
        if (username != null && !username.isBlank() && !username.equalsIgnoreCase("anonymous")) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Корисникот не е најден"));

            // Се зачувува обидот
            UserHistory history = new UserHistory(user, currentPuzzle, LocalDate.now(), isCorrect);
            historyRepository.save(history);

            // Ако е точно, му додаваме поени (на пример 10 поени за точен одговор)
            if (isCorrect) {
                user.setScore(user.getScore() + 10);
                userRepository.save(user);
            }
        }

        return isCorrect;
    }

    @Override
    public List<User> getLeaderboard() {
        return userRepository.findTop10ByOrderByScoreDesc();
    }

    @Override
    public List<UserHistory> getUserHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Корисникот не е најден"));
        return historyRepository.findByUser(user);
    }

    @Override
    public User registerUser(String username, String password) {
        // За едноставност во овој пример лозинката ја чуваме plain.
        // За вистинска апликација би користел BCryptPasswordEncoder.
        User user = new User(username, password);
        return userRepository.save(user);
    }
}