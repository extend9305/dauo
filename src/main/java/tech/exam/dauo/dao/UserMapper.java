package tech.exam.dauo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tech.exam.dauo.dto.LoginDTO;
import tech.exam.dauo.dto.UserDTO;

import java.util.Optional;

@Repository
@Mapper
public interface UserMapper {
    Optional<UserDTO> findUserByUsername(Long userId);
    Optional<UserDTO> findByUserId(Long userId);
    //public String login(LoginDTO loginDto);
    public Long join(UserDTO userDto);
    public void delete(Long userId);


}
