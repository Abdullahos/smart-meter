package com.root.meter;

import com.root.meter.security.HMACUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = MeterApplication.class)
public class Hmac {
    @Test
    public void givenDataAndKeyAndAlgorithm_whenHmacWithJava_thenSuccess()
            throws NoSuchAlgorithmException, InvalidKeyException {
        //expected hmac
        String hmacSHA256Value = "5b50d80c7dc7ae8bb1b1433cc0b99ecd2ac8397a555c6f75cb8a619ae35a0c35";
        //hash algo
        String hmacSHA256Algorithm = "HmacSHA256";
        //input
        String data = "baeldung";
        //secret or key
        String key = "123456";
        //
        String result = HMACUtil.hmacWithJava(hmacSHA256Algorithm, data, key);
        assertEquals(hmacSHA256Value, result);
    }
    @Test
    public void integrity_check()throws NoSuchAlgorithmException, InvalidKeyException{
        //what i received
        String data = "i am the real message";  //received data
        String hashed_data = "5ddd9fa70b6b24c2eff20a3feb2ba226cd5bfe0b6015c0c7634a3049e4a72ac2";    //data hashed
        //what i have
        String hmacSHA256Algorithm = "HmacSHA256";  //algo
        String key = "123456";                      //key
        String result = HMACUtil.hmacWithJava(hmacSHA256Algorithm, data, key);  //hashed received data calculated with the shared key and hash algo
        assertEquals(hashed_data,result);
    }
}
