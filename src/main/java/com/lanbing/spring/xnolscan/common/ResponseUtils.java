package com.lanbing.spring.xnolscan.common;

public class ResponseUtils {

    public static <T> ResponseDTO<T> buildSuccess(T t) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setData(t);
        responseDTO.setStatus(ResponseStatus.SUCCESS.getStatus());
        return responseDTO;
    }

    public static <T> ResponseDTO<T> buildFailure(String detailMessage) {
        return buildFailure(null, detailMessage);
    }

    public static <T> ResponseDTO<T> buildFailure(String errorCode, String detailMessage) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setData(null);
        responseDTO.setStatus(ResponseStatus.FAILURE.getStatus());
        responseDTO.setErrorCode(errorCode);
        responseDTO.setDetailMessage(detailMessage);
        return responseDTO;
    }

    public static boolean isSuccess(ResponseDTO<?> responseDTO) {
        return ResponseStatus.SUCCESS.getStatus() == responseDTO.getStatus();
    }
}
