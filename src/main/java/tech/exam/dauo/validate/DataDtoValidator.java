package tech.exam.dauo.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.exam.dauo.dto.DataDTO;

import javax.xml.crypto.Data;

@Component
@RequiredArgsConstructor
public class DataDtoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DataDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DataDTO dto = (DataDTO) target;

        if(isStringEmpty(dto.getRegDtm()) || dto.getRegDtm().length() != 10){
            errors.rejectValue("regDtm","regDtm.failed");
        }
        if(!isNumFormat(dto.getJoinCnt())){
            errors.rejectValue("joinCnt","joinCnt.failed");
        }
        if(!isNumFormat(dto.getPayAmt())){
            errors.rejectValue("payAmt","payAmt.failed");
        }
        if(!isNumFormat(dto.getSalesAmt())){
            errors.rejectValue("salesAmt","salesAmt.failed");
        }
        if(!isNumFormat(dto.getUsedAmt())){
            errors.rejectValue("usedAmt","usedAmt.failed");
        }
    }
    /**
     * 문자열 공백 체크 함수
     * @param str
     * @return
     */
    public boolean isStringEmpty(String str){
        return str == null || str.trim().isEmpty();
    }
    /**
     * 문자열 숫자타입 체크 함수
     * @param str
     * @return
     */
    public boolean  isNumFormat(String str){
        try{
            int number = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
