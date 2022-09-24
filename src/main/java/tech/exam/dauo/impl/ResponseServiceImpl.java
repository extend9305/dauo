package tech.exam.dauo.impl;

import org.springframework.stereotype.Service;
import tech.exam.dauo.response.BaseResponse;
import tech.exam.dauo.response.ListDataResponse;
import tech.exam.dauo.response.SingleDataResponse;
import tech.exam.dauo.service.ResponseService;

import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    public <T> SingleDataResponse<T> getSingleDataResponse(boolean success, String message, T data) {
        SingleDataResponse<T> response = new SingleDataResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    public <T> ListDataResponse<T> getListDataResponse(boolean success, String message, List<T> data) {
        ListDataResponse<T> response = new ListDataResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);

        return response;
    }

    public BaseResponse getBaseResponse(boolean success, String message) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(success);
        response.setMessage(message);

        return response;
    }
}
