package com.jz.dlyk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class R<T> {
    private Integer code;
    private String message;
    private T data;


    /**
     * 响应成功 不带数据
     * @return  响应成功的响应对象
     */
    public static <T> R<T> success(){
        return R.<T>builder()
                .code(RCode.SUCCESS.getCode())
                .message(RCode.SUCCESS.getMessage())
                .build();
    }

    /**
     * 响应成功 带数据
     * @param data  响应成功要返回的数据
     * @return  响应成功的响应对象
     */
    public static <T> R<T> success(T data){
        return R.<T>builder()
                .code(RCode.SUCCESS.getCode())
                .message(RCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    /**
     * 响应失败 不带数据
     * @return  响应失败的响应对象
     */
    public static <T> R<T> failure(){
        return R.<T>builder()
                .code(RCode.SERVER_ERROR.getCode())
                .message(RCode.SERVER_ERROR.getMessage())
                .build();
    }

    /**
     * 响应失败 带数据
     * @param data  响应失败要返回的数据
     * @return  响应失败的响应对象
     */
    public static <T> R<T> failure(T data){
        return R.<T>builder()
                .code(RCode.SERVER_ERROR.getCode())
                .message(RCode.SERVER_ERROR.getMessage())
                .data(data)
                .build();
    }
}
