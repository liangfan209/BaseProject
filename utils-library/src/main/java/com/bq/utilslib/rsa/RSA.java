package com.bq.utilslib.rsa;


import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class RSA {

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static final String KEY_ALGORITHM = "RSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final int MAX_ENCRYPT_BLOCK = 117;

    public static final String SRA_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANgZEPb/6t8mCSm0D1nftjKuw4QS53JZeEMRrKHCHg7U0C2C6Rt0Sqf9AS7Z1zrXp0XJf6cd80/LRIMn6N3HalQCQ72/ANJJr3o5lsuCfRJp4kT8Kf9v6KspmuKXzTR3H7/BFf7/uX+oS58BwaAd5wkX6g36dC7aWH5lHowEB76JAgMBAAECgYAY9SQpcrsclJcAUwRm29h+GAMKsOg8I6spC9SFRhcUnW2BpBnqCs9IgYbuc0hRSnDONYxhr6rXL2G1SBmz64ETBj4nte3Hq5/AwKj4GO+bMZ62MqWYSXxxCJ8G8PwPFOXsIBCUUNlcMNvwc8sDbaDGMaDlUYcOqmMM1wvMKd6XAQJBAP/mS3eTZJ5cXE5p8bk2mistj7kcmkSw2NwGOGMdKrKwJA54HIbOcfeMtfYHb18mQoRipVxJuSwyJLHZp+y5d2ECQQDYLsX8bVcp8Mh4Smtk1V0t95SlLW1RNaOo//9V9/nFWVo3635gx7U87yn+UWbfaxwzTUUoaw2yJoMHF7s2F6ApAkEAr6qq8coXz3I914J48pkeGjfrqrqM2EzCgcAvYzK5hfLOvbQMeSotKC/LFbqi5KGCFhkVxRXC7Sj68bAly6bB4QJALK7uSVCoVYjpRsPLEtpvGhM0bDfJgqGdpE7VjgVlYP8pAKnyQzrEIUYl3eUhaEiRWaE9DC+kcxA2wP6MuY+EkQJAb3i0dItgApMxpyF62S02AIP8jXD6cTj98jj4HNYn1SlCZU+DASlWky19AmL9AEw7o9dqBD4yNZ9uaOSbomR/pg==";

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static void main(String[] args) {
        Map<String, Object> keyMap;
        try {
            String content1 = "version1timestamp1560947240195signtypersaflagssoapiaccount.login";
            String content2 = "version1typeid1timestamp1561888393205signtypersaprotypecspage_size8flagblogcurrent_page1apiblog.typeList";
            String sgin = sign(content2, SRA_PRIVATE_KEY, "utf-8");
            System.out.println("sgin = "+ sgin);

            boolean b = verify(content2,sgin,
                    "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYGRD2/+rfJgkptA9Z37YyrsOEEudyWXhDEayh\n" + "wh4O1NAtgukbdEqn/QEu2dc616dFyX+nHfNPy0SDJ+jdx2pUAkO9vwDSSa96OZbLgn0SaeJE/Cn/\n" +
                            "b+irKZril800dx+/wRX+/7l/qEufAcGgHecJF+oN+nQu2lh+ZR6MBAe+iQIDAQAB", "utf-8");
            System.out.println(b);
//            System.out.println("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYGRD2/+rfJgkptA9Z37YyrsOEEudyWXhDEayh\n" + "wh4O1NAtgukbdEqn/QEu2dc616dFyX+nHfNPy0SDJ+jdx2pUAkO9vwDSSa96OZbLgn0SaeJE/Cn/\n" +
//                    "b+irKZril800dx+/wRX+/7l/qEufAcGgHecJF+oN+nQu2lh+ZR6MBAe+iQIDAQAB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取公钥
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }


    public static byte[] encrypt(byte[] plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = plainText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] cache;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(plainText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptText = out.toByteArray();
        out.close();
        return encryptText;
    }

    //获得私钥字符串
    public static String getPrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //获得公钥字符串
    public static String getPublicKeyStr(Map<String, Object> keyMap) throws Exception {
        //获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    //编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }


    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * RSA签名
     *
     * @param content       待签名数据
     * @param privateKey    商户私钥
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String input_charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            Signature signature = Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));
            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content       待签名数据
     * @param sign          签名值
     * @param public_key    公钥
     * @param input_charset 编码格式
     * @return 布尔值
     */
    public static boolean verify(String content, String sign, String public_key, String input_charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));
            return signature.verify(Base64.decode(sign));
        } catch (Exception e) {

        }

        return false;
    }

    /**
     * 解密
     *
     * @param content       密文
     * @param private_key   私钥
     * @param input_charset 编码格式
     * @return 解密后的字符串
     */
    public static String decrypt(String content, String private_key, String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }


    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 将所有非sign键的字典进行以键（string类型）反序排列，并以{key}{value}形式依次组装成字符串
     *
     * @param param
     * @return
     */
    protected static String createLinkDescStr(Map<String, String> param) {
        TreeMap<String, String> map = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        map.putAll(param);
        StringBuilder tmpStr = new StringBuilder();
        for (String key : map.keySet()) {
            if (!TextUtils.isEmpty(key)) {
                tmpStr.append(key);
                tmpStr.append(map.get(key));
            }
        }

        return tmpStr.toString();
    }

    /**
     * 将request请求的body体转换成Map数组
     *
     * @param requestBodyStr
     * @return
     */
    public static Map<String, String> requestBodyStr2Map(String requestBodyStr) {

        Map<String, String> mapOfRequestBody = new HashMap<>();
        String[] data = requestBodyStr.split("&");

        for (String datum : data) {
            String[] tmp = datum.split("=");
            if (null != tmp && tmp.length == 2) {
                try {
                    mapOfRequestBody.put(tmp[0].toLowerCase(), URLDecoder.decode(tmp[1], "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        mapOfRequestBody.remove("serialVersionUID".toLowerCase());
        return mapOfRequestBody;
    }


    /**
     *    body 以& 类型的进行签名    api=login&flag=count
     * @param body
     * @return
     */
    public static String getSign(String body){
        Map<String, String> map = requestBodyStr2Map(body);
        String linkDescStr = createLinkDescStr(map);
        return linkDescStr;
    }



    /**
     * 对签名进行抽样 1.抽样数量（默认抽样数量=参数数量*抽样因子（默认1.4）） 2.间隔数量 签名长度 / 抽样次数
     * 3.通过抽样数量及间隔来决定抽取的字符串。
     *
     * @param signStr
     * @param param
     * @param factor  默认1.4
     * @return
     */
    public static String sampling(String signStr, Map<String, String> param, double factor) {
        StringBuilder tmpStr = new StringBuilder();
        System.out.println(signStr.length());
        Double size = param.size() * factor;
        Integer interval = signStr.length() / size.intValue();
        char[] chars = signStr.toCharArray();
        for (int i = 0; i < size.intValue(); i++) {
            tmpStr.append(chars[i * interval]);
        }

        return tmpStr.toString();
    }

    public static String sha1(String data) {
        //加盐   更安全一些
//        data += "lyz";
        //信息摘要器                                算法名称
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //把字符串转为字节数组
        byte[] b = data.getBytes();
        //使用指定的字节来更新我们的摘要
        md.update(b);
        //获取密文  （完成摘要计算）
        byte[] b2 = md.digest();
        //获取计算的长度
        int len = b2.length;
        //16进制字符串
        String str = "0123456789abcdef";
        //把字符串转为字符串数组
        char[] ch = str.toCharArray();

        //创建一个40位长度的字节数组
        char[] chs = new char[len * 2];
        //循环20次
        for (int i = 0, k = 0; i < len; i++) {
            byte b3 = b2[i];//获取摘要计算后的字节数组中的每个字节
            // >>>:无符号右移
            // &:按位与
            //0xf:0-15的数字
            chs[k++] = ch[b3 >>> 4 & 0xf];
            chs[k++] = ch[b3 & 0xf];
        }

        //字符数组转为字符串
        return new String(chs);
    }

}
