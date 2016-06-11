package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.common.GenericException;
import cn.vpclub.pinganquan.mobile.dto.UserDto;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/11.
 */
@Service
public class JwtService {

    public final static String KEY = "Q9m4u5hInf6iYTN66Obrl2qr40rBHndzoavbzs4IgXc";


    @Value("${customer.cookie.max-age}")
    private int maxAge;


    /**
     * 将用户信息放入token
     *
     * @param user
     * @return
     */
    public String putUserIntoToken(UserDto user) {
        try {
            JWSHeader jwsHeader = JWSHeader.parse("{\"alg\": \"HS256\", \"typ\": \"JWT\"}");
            JWTClaimsSet claimsSet = new JWTClaimsSet();
            claimsSet.setCustomClaim("userName", user.getUserName());
            claimsSet.setCustomClaim("userType", user.getUserType());
            claimsSet.setCustomClaim("fromUserName", user.getFromUserName());
            claimsSet.setCustomClaim("fromUserType", user.getFromUserType());
            claimsSet.setExpirationTime(new Date(new Date().getTime() + maxAge * 1000)); // unit:second
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
            MACSigner signer = new MACSigner(KEY);
            signedJWT.sign(signer);
            String jwt = signedJWT.serialize();
            return jwt;
        } catch (JOSEException | ParseException exp) {
            throw new GenericException("fail to generate jwt token");
        }
    }


    /**
     * 从token中取出用户信息
     *
     * @param token
     * @return
     */
    public UserDto getUserFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean flag1 = signedJWT.verify(new MACVerifier(KEY));
            if (flag1 == false) {
                throw new GenericException("the signature of the jwt token is invalid");
            }
            boolean isNotExpired = new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime()); // 当前时间要在保持期之前)
            if (isNotExpired == false) {
                throw new GenericException("jwt token has been expired");
            }
            ReadOnlyJWTClaimsSet clainSet = signedJWT.getJWTClaimsSet();
            UserDto userDto = new UserDto(clainSet.getStringClaim("userName"), clainSet.getIntegerClaim("userType"),
                    clainSet.getStringClaim("fromUserName"), clainSet.getIntegerClaim("fromUserType"));
            return userDto;
        } catch (JOSEException | ParseException exp) {
            throw new GenericException("fail to parsing the jwt token");
        }
    }


}
