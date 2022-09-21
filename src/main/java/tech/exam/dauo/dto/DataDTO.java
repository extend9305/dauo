package tech.exam.dauo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
    private String regDtm;
    private String joinCnt;
    private String resignCnt;
    private String payAmt;
    private String usedAmt;
    private String salesAmt;

}
