package com.evan.wj.utils;

import com.evan.wj.pojo.Audience;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtTokenUtil {

    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 构建jwt
     *
     * @param userId
     * @param username
     * @param audience
     * @return
     */
    public static String createJWT(int userId, String username, Audience audience) {

        // 使用HS256加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(audience.getBase64Secret());
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                // 可以将基本不重要的对象信息放到claims
                .claim("userId", userId)
                .setSubject(username)    // 代表这个JWT的主体，即它的所有人
                .setIssuer(audience.getClientId())  // 该JWT的签发者，是否使用是可选的
                .setIssuedAt(new Date()) // 是一个时间戳，代表这个JWT的签发时间；
                .setAudience(audience.getName())    // 代表这个JWT的接收对象；
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        int TTLMillis = audience.getExpiresSecond();
        if (TTLMillis >= 0) {
            long expMillis = nowMillis + TTLMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                    .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
        }

        return builder.compact();
    }

    /**
     * 解析jwt
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
            .parseClaimsJws(jsonWebToken).getBody();
    }

    /**
     * 是否已过期
     * @param token
     * @param base64Security
     * @return
     */
    public static boolean isExpiration(String token, String base64Security) {
        return parseJWT(token, base64Security).getExpiration().before(new Date());
    }

}