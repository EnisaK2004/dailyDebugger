package mk.ukim.finki.wp.dailydebugger.web;


import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.dailydebugger.models.CodePuzzle;
import mk.ukim.finki.wp.dailydebugger.models.User;
import mk.ukim.finki.wp.dailydebugger.models.UserHistory;
import mk.ukim.finki.wp.dailydebugger.services.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // 1. Главната страница на играта
    @GetMapping
    public String getGamePage(Model model, HttpSession session) {
        CodePuzzle puzzle = gameService.getDailyPuzzle();
        model.addAttribute("puzzle", puzzle);

        // Земање на логираниот корисник од сесијата (ако постои)
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username != null ? username : "Анонимен");

        return "index"; // ќе бара index.html во templates
    }

    // 2. Проверка на одговорот (Погодување)
    @PostMapping("/guess")
    public String submitGuess(@RequestParam String guess, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        // Ако нема логиран корисник, праќаме "anonymous" до сервисот
        String userToRegister = (username != null) ? username : "anonymous";

        boolean isCorrect = gameService.submitGuess(userToRegister, guess);

        model.addAttribute("puzzle", gameService.getDailyPuzzle());
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("hasGuessed", true);
        model.addAttribute("username", username != null ? username : "Анонимен");

        return "index";
    }

    // 3. Ранг листа (Leaderboard)
    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        List<User> leaderboard = gameService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        return "leaderboard"; // ќе бара leaderboard.html во templates
    }

    // 4. Профил и Историја (Календар на решени денови)
    @GetMapping("/history")
    public String getUserHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/game/login"; // ако не е логиран, го праќа на логин
        }

        List<UserHistory> history = gameService.getUserHistory(username);
        model.addAttribute("history", history);
        model.addAttribute("username", username);
        return "history"; // ќе бара history.html во templates
    }

    // 5. Едноставен Login / Регистрација преку Сесија
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            // За едноставност, ако корисникот не постои, автоматски го регистрираме (2 во 1 акција)
            // Инаку тука би ставиле проверка на лозинка
            try {
                gameService.registerUser(username, password);
            } catch (Exception e) {
                // Корисникот веќе постои, продолжуваме кон логирање
            }

            session.setAttribute("username", username);
            return "redirect:/game";
        } catch (Exception e) {
            model.addAttribute("error", "Грешка при најава.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String doLogout(HttpSession session) {
        session.invalidate(); // ја брише сесијата
        return "redirect:/game";
    }
}