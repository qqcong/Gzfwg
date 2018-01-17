package com.bwoil.pay.sdk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class SignUtils {

    private final static Log log= LogFactory.getLog(SignUtils.class);

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static String getAlgorithms(boolean rsa2) {
        return rsa2 ? SIGN_SHA256RSA_ALGORITHMS : SIGN_ALGORITHMS;
    }

    @SuppressWarnings("resource")
	public static String getFile(String fileName)  {
        ClassLoader classLoader = SignUtils.class.getClassLoader();
        /**
         getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
         */
        URL url = classLoader.getResource(fileName);
        /**
         * url.getFile() 得到这个文件的绝对路径
         */
        File file = new File(url.getFile());

        try {
            FileInputStream fi = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fi.read(bytes);
            return new String(bytes);
        }catch (Exception e){
            log.error("读取文件错误",e);
            return  null;
        }


    }

    private static String sign(String content,String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(getAlgorithms(false));
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            log.error("RAS加密出错", e);
        }
        return null;
    }


    public static String sign(Map<String, String> map,String privateKey) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));
        String oriSign = sign(authInfo.toString(),privateKey);
        String encodedSign = "";
        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedSign;
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = Base64.decode(publicKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }


    public static boolean checkSign(Map<String, Object> map,String sign, String publicKey) throws Exception {

        if(sign==null||"".equals(sign.trim())){
            return true;
        }
        List<String> keys = new ArrayList<>(map.keySet());
        sign= URLDecoder.decode(sign,"UTF-8");
        map.remove("sign");
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            Object value = map.get(key);
            if(value==null|| "".equals(value.toString().trim())){
                continue;
            }
            authInfo.append(buildKeyValue(key, value.toString(), false));
            authInfo.append("&");
        }
        String tailKey = keys.get(keys.size() - 1);
        Object tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue.toString(), false));
        PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(authInfo.toString().getBytes(DEFAULT_CHARSET));
        return signature.verify(Base64.decode(sign));


    }
}
