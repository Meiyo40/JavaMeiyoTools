package com.meiyotools.main.service;

import com.meiyotools.main.model.entity.Plan;
import com.meiyotools.main.model.entity.Player;
import com.meiyotools.main.model.entity.User;
import com.meiyotools.main.model.repository.PlanRepository;
import com.meiyotools.main.model.repository.PlayerRepository;
import com.meiyotools.main.model.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class PageService {

    private final UserService userService;
    private final PlanService planService;
    private final PlayerService playerService;

    @Autowired
    public PageService(UserService pUserService, PlanService pPlanService, PlayerService pPlayerService) {
        this.planService = pPlanService;
        this.playerService = pPlayerService;
        this.userService = pUserService;
    }

    public void setAdminIndex(HttpServletRequest request, Model model) {
        String username = request.getSession().getAttribute("user").toString();
        User user = userService.getUser(username);

        model.addAttribute("user", user.getUsername());
        model.addAttribute("page", "index-manager");
        model.addAttribute("title", "Admin Index");
        model.addAttribute("logged", "true");
    }

    public void setPlayerManager(HttpServletRequest request, Model model) {
        String username = request.getSession().getAttribute("user").toString();
        User user = userService.getUser(username);
        List<Player> playerList = playerService.getAllPlayers();

        model.addAttribute("user", user.getUsername());
        model.addAttribute("page", "player-manager");
        model.addAttribute("title", "Player Manager");
        model.addAttribute("logged", "true");

        model.addAttribute("players", playerList);
    }

    public void setPlanManager(HttpServletRequest request, Model model) {
        String username = request.getSession().getAttribute("user").toString();
        User user = userService.getUser(username);
        List<List<Plan>> plans = planService.getAllPlansOrdered();
        List<Player> players = playerService.getAllPlayers();

        model.addAttribute("user", user.getUsername());
        model.addAttribute("page", "plan-manager");
        model.addAttribute("title", "Plan Manager");
        model.addAttribute("logged", "true");

        model.addAttribute("plans", plans);
        model.addAttribute("players", players);
    }

    public void setPublicIndex(HttpServletRequest request, Model model) {
        List<Player> players = playerService.getAllPlayers();

        model.addAttribute("logged", "false");
        model.addAttribute("title", "SMB Plan");
        model.addAttribute("page", "manager");
        model.addAttribute("players", players);
    }
}
