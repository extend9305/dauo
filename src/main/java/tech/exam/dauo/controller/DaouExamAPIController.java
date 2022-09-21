package tech.exam.dauo.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.exam.dauo.dto.DataDTO;
import tech.exam.dauo.service.DauoExamService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DaouExamAPIController {
    private final DauoExamService dauoExamService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/test")
    public List<DataDTO>test(){

        return dauoExamService.getDaouExamList();
    }
}
