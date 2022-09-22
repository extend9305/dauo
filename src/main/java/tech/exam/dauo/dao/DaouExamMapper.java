package tech.exam.dauo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tech.exam.dauo.dto.DataDTO;

import java.util.List;

@Repository
@Mapper
public interface DaouExamMapper {
    List<DataDTO> getAllList();
    void save(DataDTO dto);
    void delete(String regDtm);
    DataDTO getFindDauoExam(String regDtm);
    void update(DataDTO dto);

}
