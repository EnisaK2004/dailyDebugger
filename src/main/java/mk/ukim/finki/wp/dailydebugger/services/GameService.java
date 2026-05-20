package mk.ukim.finki.wp.dailydebugger.services;


import mk.ukim.finki.wp.dailydebugger.models.CodePuzzle;
import mk.ukim.finki.wp.dailydebugger.models.User;
import mk.ukim.finki.wp.dailydebugger.models.UserHistory;

import java.util.List;

public interface GameService {
    CodePuzzle getDailyPuzzle();
    boolean submitGuess(String username, String guess);
    List<User> getLeaderboard();
    List<UserHistory> getUserHistory(String username);
    User registerUser(String username, String password);
}