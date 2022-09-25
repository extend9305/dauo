package tech.exam.dauo.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;
import tech.exam.dauo.exception.DuplicatedUsernameException;
import tech.exam.dauo.exception.LoginFailedException;
import tech.exam.dauo.exception.UserNotFoundException;
import tech.exam.dauo.response.BaseResponse;
import tech.exam.dauo.response.SingleDataResponse;
import tech.exam.dauo.security.JwtTokenProvider;
import tech.exam.dauo.service.ResponseService;
import tech.exam.dauo.service.UserService;

import java.time.Duration;

@Slf4j
//@RequiredArgsConstructor
@RestController
@RequestMapping
public class SignController {
    private final UserService userService;
    private final ResponseService responseService;
    private final Bucket bucket;

    public SignController(UserService userService, ResponseService responseService) {
        this.userService = userService;
        this.responseService = responseService;

        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserDTO userDto) {
        ResponseEntity responseEntity = null;
        try {
            UserDTO savedUser = userService.join(userDto);
            SingleDataResponse<UserDTO> response = responseService.getSingleDataResponse(true, "회원가입 성공", savedUser);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DuplicatedUsernameException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDto) {
        ResponseEntity responseEntity = null;
        try {
            String token = userService.login(loginDto);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + token);

            SingleDataResponse<String> response = responseService.getSingleDataResponse(true, "로그인 성공", token);

            responseEntity = ResponseEntity.status(HttpStatus.OK).headers(httpHeaders).body(response);
        } catch (LoginFailedException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }

    @GetMapping("/users")
    public ResponseEntity findUserByUsername(final Authentication authentication) {
        ResponseEntity responseEntity = null;
        try {
            Long userId = ((UserDTO) authentication.getPrincipal()).getUserId();
            UserDTO findUser = userService.findByUserId(userId);

            SingleDataResponse<UserDTO> response = responseService.getSingleDataResponse(true, "조회 성공", findUser);

            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserNotFoundException exception) {
            log.debug(exception.getMessage());
            BaseResponse response = responseService.getBaseResponse(false, exception.getMessage());

            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return responseEntity;
    }
}
