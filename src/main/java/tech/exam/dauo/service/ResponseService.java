package tech.exam.dauo.service;

import org.springframework.stereotype.Service;
import tech.exam.dauo.response.BaseResponse;
import tech.exam.dauo.response.ListDataResponse;
import tech.exam.dauo.response.SingleDataResponse;

import java.util.List;


public interface ResponseService {
    public <T> SingleDataResponse<T> getSingleDataResponse(boolean success, String message, T data) ;

    public <T> ListDataResponse<T> getListDataResponse(boolean success, String message, List<T> data);

    public BaseResponse getBaseResponse(boolean success, String message) ;
}
