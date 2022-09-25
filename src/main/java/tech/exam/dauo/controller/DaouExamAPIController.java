package tech.exam.dauo.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.exam.dauo.config.ConfigProperties;
import tech.exam.dauo.dto.DataDTO;
import tech.exam.dauo.response.DaouExamAPIResponse;
import tech.exam.dauo.service.DauoExamService;
import tech.exam.dauo.validate.DataDtoValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Validated

@RestController
@RequestMapping(path = "/dauo/api")
public class DaouExamAPIController {
    private final DauoExamService dauoExamService;
    private final ConfigProperties configProperties;
    private final DataDtoValidator validator;
    private final Bucket bucket;

    public DaouExamAPIController(DauoExamService dauoExamService, ConfigProperties configProperties, DataDtoValidator validator) {
        this.dauoExamService = dauoExamService;
        this.configProperties = configProperties;
        this.validator = validator;

        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    DaouExamAPIResponse response = new DaouExamAPIResponse();

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }


    /**
     * 모든 데이터 조회
     * @return
     */
    @GetMapping("/alldata")
    public ResponseEntity<DaouExamAPIResponse> allData(){
        List<DataDTO> daouList = dauoExamService.getAllList();
        response = DaouExamAPIResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("전체 사용자 조회 성공")
                .result(new ArrayList<>(daouList))
                .count(daouList.size()).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * 시간대별 가입자수 조회
     * @param regDtm
     * @return
     */
    @GetMapping(value = "/findJoinCnt/{regDtm}")
    public ResponseEntity<DaouExamAPIResponse> findJoinCnt(@Validated @PathVariable String regDtm) {
        String findJoinCnt = dauoExamService.getFindJoinCnt(regDtm);

        if(validator.isStringEmpty(findJoinCnt)){
            response = DaouExamAPIResponse.builder()
                    .code(HttpStatus.NO_CONTENT.value())
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .message("조회한 데이터가 없습니다.")
                    .count(0)
                    .build();
            return new ResponseEntity<>(response, response.getHttpStatus());
        }else{
            response = DaouExamAPIResponse.builder()
                    .code(HttpStatus.OK.value())
                    .httpStatus(HttpStatus.OK)
                    .message("전체 사용자 조회 성공")
                    .result(Arrays.asList(findJoinCnt))
                    .count(1).build();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
    }

    /**
     * 시간대별 저장
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<DaouExamAPIResponse> oneSave(@Validated @RequestBody DataDTO dto, BindingResult bindingResult){

        validator.validate(dto,bindingResult);

        if(bindingResult.hasErrors()){
            response = DaouExamAPIResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("잘못된 값으로 요청하였습니다.")
                    .count(0)
                    .build();
            return new ResponseEntity<>(response, response.getHttpStatus());
        }

        dauoExamService.save(dto);
        response = DaouExamAPIResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("데이터 저장 성공.")
                .result(Arrays.asList(dto))
                .count(1)
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    /**
     * 시간대별 데이터 수정
     * @param regDtm
     * @param joinCnt
     * @param resignCnt
     * @param payAmt
     * @param usedAmt
     * @param salesAmt
     * @return
     */

    @PutMapping(value = "/update/{regDtm}")
    public ResponseEntity<DaouExamAPIResponse> findOneUpdate(@NotBlank  @Size(min = 10)  @Size(max = 10) @PathVariable  String regDtm, @PositiveOrZero  @RequestParam String joinCnt, @PositiveOrZero @RequestParam String resignCnt, @PositiveOrZero @RequestParam String payAmt, @PositiveOrZero @RequestParam String usedAmt, @PositiveOrZero  @RequestParam String salesAmt) {
        DataDTO dto = new DataDTO();

        dto.setRegDtm(regDtm);
        dto.setUsedAmt(usedAmt);
        dto.setSalesAmt(salesAmt);
        dto.setPayAmt(payAmt);
        dto.setResignCnt(resignCnt);
        dto.setJoinCnt(joinCnt);
        dauoExamService.update(dto);

        response = DaouExamAPIResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("데이터 변경 성공.")
                .result(Arrays.asList(dto))
                .count(1)
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @DeleteMapping(value = "/delete/{regDtm}")
    public ResponseEntity<DaouExamAPIResponse> findOneDelete(@Validated @PathVariable String regDtm) {
        DataDTO dto = dauoExamService.getFindDauoExam(regDtm);

        if(validator.isStringEmpty(regDtm)){
            response = DaouExamAPIResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("잘못된 값으로 요청하였습니다.")
                    .count(0)
                    .build();
            return new ResponseEntity<>(response, response.getHttpStatus());
        }

        dauoExamService.delete(regDtm);

        response = DaouExamAPIResponse.builder()
                .code(HttpStatus.OK.value())
                .httpStatus(HttpStatus.OK)
                .message("데이터 삭제 성공.")
                .result(Arrays.asList())
                .count(0)
                .build();
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
