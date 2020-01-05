package com.monitor.task.user.controller;

import com.monitor.task.user.UserMapper;
import com.monitor.task.user.dto.UserDto;
import com.monitor.task.user.service.UserService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @NonNull
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody final UserDto dto) {
        UserDto user = UserMapper.mapFromUserEntity(userService.save(UserMapper.mapToUserEntity(dto)));
        log.debug("User created: " + user.getId());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logged")
    public ResponseEntity<String> getCurrentLogged() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return ResponseEntity.ok(username);
    }
}
