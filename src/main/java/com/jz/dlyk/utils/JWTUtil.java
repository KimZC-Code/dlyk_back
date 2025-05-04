package com.jz.dlyk.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jz.dlyk.constants.JWTConstants;
import com.jz.dlyk.entity.MyUserDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    /**
     * 使用用户json生成token
     * @param myUserDetail  当前登录的用户
     * @return 生成的token
     */
    public static String generateToken(MyUserDetail myUserDetail) {
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("alg","HS256");
        headers.put("typ","JWT");
        String userJson = JSON.toJSONString(myUserDetail);
        log.info(userJson);
        return JWT.create()
                .withHeader(headers)
                .withClaim("user",userJson)
                .sign(Algorithm.HMAC256(JWTConstants.TOKEN_SECRET));
    }

    /**
     * 验证token
     * @param token 要验证的token
     * @return  验证结果
     */
    public static Boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWTConstants.TOKEN_SECRET)).build();
            verifier.verify(token);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将token中的用户数据进行提取
     * @param token 被提取的数据
     * @return  返回提取的用户数据
     */
    public static MyUserDetail parseUserFromJWT(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWTConstants.TOKEN_SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim user = decodedJWT.getClaim("user");
        String userJson = user.asString();
        return JSON.parseObject(userJson, MyUserDetail.class);
    }
}
