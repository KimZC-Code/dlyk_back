package com.jz.dlyk.utils;

import com.alibaba.fastjson.JSON;
import com.jz.dlyk.dto.R;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class ResponseUtil {
    public static void responsePack(HttpServletResponse response, R result){
        response.setContentType("application/json;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            response.setStatus(result.getCode());
            String s = JSON.toJSONString(result);
            writer.write(s);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
