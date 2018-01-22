package com.bbbbb.pay.gateway.util;

import com.bbbbb.common.framework.util.Base64;
import com.bbbbb.common.framework.util.JsonUtils;
import com.bbbbb.common.framework.util.ReflectionUtils;
import com.bbbbb.pay.gateway.form.SignForm;
import com.bbbbb.pay.gateway.result.SignResult;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static com.bbbbb.pay.common.util.rsa.RsaConst.KEY_ALGORITHM;

@CommonsLog
public class SignUtils {

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private static String getAlgorithms(boolean rsa2) {
        return rsa2 ? SIGN_SHA256RSA_ALGORITHMS : SIGN_ALGORITHMS;
    }

    public static String privateKey;

    private static String getFile(String fileName) throws IOException {
        ClassLoader classLoader = SignUtils.class.getClassLoader();
        /**
         getResource()方法会去classpath下找这个文件，获取到url resource, 得到这个资源后，调用url.getFile获取到 文件 的绝对路径
         */
        URL url = classLoader.getResource(fileName);
        /**
         * url.getFile() 得到这个文件的绝对路径
         */

        File file = new File(url.getFile());

        FileInputStream fi = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fi.read(bytes);
        return new String(bytes);

    }

    private static String sign(String content) {
        try {
            log.info("加密内容:"+content);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(getAlgorithms(false));
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            byte[] signed = signature.sign();
           String s= Base64.encode(signed);
            log.info("签名:"+s);
            return s;
        } catch (Exception e) {
            log.error("RAS加密出错", e);
        }
        return null;
    }

    private static final int MAX_ENCRYPT_BLOCK = 117;

    public static String sign3(String content, String publicKey, boolean rsa2) {
        try {
            byte[] keyBytes = Base64.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            byte[] data = content.getBytes();
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64.encode(encryptedData);
        } catch (Exception e) {
            log.error("RAS加密出错", e);
        }
        return null;
    }

    private static String sign(Map<String, String> map) {
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
        String oriSign = sign(authInfo.toString());
        String encodedSign = "";
        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedSign;
    }


    public static void sign(SignResult result) {
        log.info("正在对返回数据进行签名");
        Map<String, String> map = transfer(result);
        String sign = sign(map);
        result.setSign(sign);
        log.info("返回签名后的数据:"+ JsonUtils.toString(result));

    }


    public static boolean checkSign(SignForm form, String publicKey) throws Exception {
        Map<String, String> map = transfer(form);
        List<String> keys = new ArrayList<>(map.keySet());
        map.remove("sign");
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
        PublicKey pubKey = getPublicKeyFromX509("RSA", publicKey);

        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

        signature.initVerify(pubKey);


        signature.update(authInfo.toString().getBytes(DEFAULT_CHARSET));

        return signature.verify(Base64.decode(URLDecoder.decode(form.getSign(),"UTF-8")));


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


    public static String[] ignoreFields() {
        return new String[]{"sign"};
    }


    public static Map<String, String> transfer(Object bean) {
        Map<String, String> map = new HashMap<>();
        List<String> ignores = Arrays.asList(ignoreFields());
        Class<?> beanClass = bean.getClass();
        Field[] viewFields = ReflectionUtils.getAllDeclaredFields(beanClass);
        for (Field viewField : viewFields) {
            try {
                String name = viewField.getName();
                if (ignores.contains(name)) {
                    continue;
                }
                viewField.setAccessible(true);
                PropertyDescriptor beanProperty = ReflectionUtils.findProperty(beanClass, name);
                if (beanProperty == null) {
                    continue;
                }
                if (beanProperty.getReadMethod() == null) {
                    throw new RuntimeException("实体[" + beanClass.getName() + "],字段[" + name + "]没有getter");
                }
                Object value = beanProperty.getReadMethod().invoke(bean);
                if (value == null||StringUtils.isBlank(value.toString())) {
                    continue;
                }
                map.put(name, value.toString());
            } catch (Exception e) {
                log.error("读取加密字段报错", e);
                continue;

            }
        }
        return map;
    }
}
