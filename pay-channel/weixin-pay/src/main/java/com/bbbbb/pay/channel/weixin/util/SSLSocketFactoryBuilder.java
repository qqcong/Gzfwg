package com.bbbbb.pay.channel.weixin.util;


import com.bbbbb.common.framework.util.Base64;
import com.bbbbb.pay.channel.weixin.WeixinAccount;

import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * Created by daoxing on 2016/9/20.
 */
public class SSLSocketFactoryBuilder {
    public static SSLSocketFactory getSSLSocketFactory(WeixinAccount account) throws Exception{



        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        InputStream instream= new ByteArrayInputStream(Base64.decode(account.getCert()));
            //InputStream instream=SSLSocketFactoryBuilder.class.getClassLoader().getResourceAsStream(account.getCertLocalPath());
        String certPassword=account.getMchId();

        try {
                keyStore.load(instream,certPassword.toCharArray());
        } finally {
                instream.close();
        }
            // First, get the default KeyManagerFactory.
        String alg= KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory kmFact=KeyManagerFactory.getInstance(alg);

            // Next, set up the KeyStore to use. We need to load the file into
            // a KeyStore instance.

            // Now we initialize the TrustManagerFactory with this KeyStore
        kmFact.init(keyStore, certPassword.toCharArray());
            // And now get the TrustManagers
        KeyManager[] km=kmFact.getKeyManagers();



            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = { new MyX509TrustManager() };


        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(km, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
        return sslContext.getSocketFactory();


    }
}
