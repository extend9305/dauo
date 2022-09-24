package tech.exam.dauo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tech.exam.dauo.dto.UserDTO;

import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {
    Optional<UserDTO> findUserByUsername(String username);
    Optional<UserDTO> findByUserId(Long userId);
    void save(UserDTO userDto);
}
