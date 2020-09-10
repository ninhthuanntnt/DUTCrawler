package com.ntnt.dut.crawlerapi.controllers;

import com.gargoylesoftware.htmlunit.util.Cookie;
import com.ntnt.dut.crawlerapi.binders.NotiTypeEditor;
import com.ntnt.dut.crawlerapi.enums.NotiType;
import com.ntnt.dut.crawlerapi.models.entities.ScheduleEntity;
import com.ntnt.dut.crawlerapi.models.entities.ScoreEntity;
import com.ntnt.dut.crawlerapi.models.entities.UserEntity;
import com.ntnt.dut.crawlerapi.models.responses.ApiResponse;
import com.ntnt.dut.crawlerapi.models.responses.JwtResponse;
import com.ntnt.dut.crawlerapi.services.HtmlService;
import com.ntnt.dut.crawlerapi.services.JwtService;
import com.ntnt.dut.crawlerapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@SessionAttributes({"user", "cookie"})
public class MainController {
    @Autowired
    private HtmlService htmlService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private JwtService jwtService;

    @InitBinder("notiType")
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(NotiType.class, new NotiTypeEditor());
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotifications(
            @RequestParam NotiType notiType,
            @RequestParam int pageNumber
    ) {
        return ResponseEntity.ok(htmlService.getNotifications(notiType, pageNumber));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserEntity user
    ) {
        ArrayList datas = htmlService.getDatasAfterLogin(user.getUsername(), user.getPassword());
        Cookie cookie = (Cookie) datas.get(1);
        if (cookie != null) {
            JwtResponse jwtResponse = new JwtResponse(
                    jwtService.generateToken(
                            cookie.getName() + '=' + cookie.getValue(),
                            user.getUsername()
                    ), (String) datas.get(0)
            );

            return ResponseEntity.ok(jwtResponse);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserEntity user
    ) {
        try {
            ArrayList datas = htmlService.getDatasAfterLogin(user.getUsername(), user.getPassword());
            Cookie cookie = (Cookie) datas.get(1);
            user.setCrawledWebCookie(cookie.getName() + '=' + cookie.getValue());

            if (userService.isExistedUser(user)) {
                return ResponseEntity.ok(new ApiResponse(null, "You registered to our system"));
            } else if (cookie != null) {
                UserEntity newUser = userService.saveUser(user);
                return ResponseEntity.ok(new ApiResponse(newUser, "Created an user"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ApiResponse(null, "Username or password is not correct"), HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/schedule")
    public ResponseEntity<?> schedule(
            @RequestParam("type") String type,
            Authentication authentication
    ) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        List<ScheduleEntity> schedules = htmlService.getSchedules(user.getCrawledWebCookie(), type);

        if (schedules.isEmpty()) {
            ArrayList datas = htmlService.getDatasAfterLogin(user.getUsername(), user.getPassword());
            Cookie cookie = (Cookie) datas.get(1);
            schedules = htmlService.getSchedules(cookie.getName() + '=' + cookie.getValue(), type);
        }

        return ResponseEntity.ok(new ApiResponse(schedules, ""));
    }

    @GetMapping("/score")
    public ResponseEntity<?> getScores(
            Authentication authentication
    ) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        List<ScoreEntity> scores = htmlService.getScores(user.getCrawledWebCookie());

        if (scores.isEmpty()) {
            ArrayList datas = htmlService.getDatasAfterLogin(user.getUsername(), user.getPassword());
            Cookie cookie = (Cookie) datas.get(1);
            scores = htmlService.getScores(cookie.getName() + '=' + cookie.getValue());
        }
        return ResponseEntity.ok(new ApiResponse(scores, ""));
    }
}
