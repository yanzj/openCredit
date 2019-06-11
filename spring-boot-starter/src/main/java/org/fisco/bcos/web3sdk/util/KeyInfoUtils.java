package org.fisco.bcos.web3sdk.util;

import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.crypto.gm.RetCode;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;

public class KeyInfoUtils {

    private static final Pattern KEY_PATTERN = Pattern.compile(
            "-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+" + // Header
                    "([a-z0-9+/=\\r\\n]+)" +                       // Base64 text
                    "-+END\\s+.*PRIVATE\\s+KEY[^-]*-+",            // Footer
            Pattern.CASE_INSENSITIVE);

    private static String privateKey;
    private static String publicKey;
    private static String account;
    private static Logger logger = LoggerFactory.getLogger(KeyInfoUtils.class);

    public static final String privJsonKey = "privateKey";
    public static final String pubJsonKey = "publicKey";
    public static final String accountJsonKey = "account";

    public KeyInfoUtils(String publicKey, String privateKey, String account) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.account = account;
    }

    public KeyInfoUtils() {}

    public void setPrivateKey(String privKey) {
        this.privateKey = privKey;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPublicKey(String pubKey) {
        this.publicKey = pubKey;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    private static String readFile(String keyFile) {
        InputStreamReader reader = null;
        BufferedReader bufReader = null;
        try {
            File file = new File(keyFile);
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            if (reader != null) bufReader = new BufferedReader(reader);
            String line = null;
            String content = "";
            if (bufReader != null) {
                while ((line = bufReader.readLine()) != null) {
                    content += line;
                }
                System.out.println("read file " + keyFile + ", result:" + content);
            }

            return content;
        } catch (Exception e) {
            logger.error("read file " + keyFile + " failed, error message:" + e.getMessage());
            return null;
        } finally {
            ReleaseBufferedReader(bufReader);
            ReleaseInputStream(reader);
        }
    }

    private static void ReleaseInputStream(InputStreamReader reader) {
        try {
            if (reader != null) reader.close();
        } catch (Exception e) {
            logger.error("close InputStreamReader failed, error message:" + e.getMessage());
        }
    }

    private static void ReleaseBufferedReader(BufferedReader bufReader) {
        try {
            if (bufReader != null) bufReader.close();
        } catch (Exception e) {
            logger.error("close BufferedReader failed, error message: " + e.getMessage());
        }
    }

    /**
     * @author: fisco-dev
     * @param keyFile: file that contains the key information
     */
    public int loadKeyInfo(String keyFile) {
        String keyInfoJsonStr = readFile(keyFile);
        if (keyInfoJsonStr == null) {
            System.out.println("load key information failed");
            logger.error("load key information failed");
            return RetCode.openFileFailed;
        }
        System.out.println("");
        System.out.println("===key info:" + keyInfoJsonStr);
        try {
            JSONObject keyInfoJsonObj = JSONObject.parseObject(keyInfoJsonStr);
            if (keyInfoJsonObj == null) {
                System.out.println("load json str from key info failed");
                logger.error("load json str from key info failed");
                return RetCode.parseJsonFailed;
            }
            if (keyInfoJsonObj.containsKey(privJsonKey))
                privateKey = keyInfoJsonObj.getString(privJsonKey);
            if (keyInfoJsonObj.containsKey(pubJsonKey)) publicKey = keyInfoJsonObj.getString(pubJsonKey);
            if (keyInfoJsonObj.containsKey(accountJsonKey))
                account = keyInfoJsonObj.getString(accountJsonKey);
            System.out.println("");
            System.out.println("====LOADED KEY INFO ===");
            System.out.println("* private key:" + privateKey);
            System.out.println("* public key :" + publicKey);
            System.out.println("* account: " + account);
            return RetCode.success;
        } catch (Exception e) {
            System.out.println(
                    "load private key from " + keyFile + " failed, error message:" + e.getMessage());
            return RetCode.loadKeyInfoFailed;
        }
    }

    /**
     * @author: YoungWilliam
     * @param keyFile: file that contains the private key information
     */
    public int loadPrivateKeyInfo(String keyFile) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource keystorekeyResource = resolver.getResource(keyFile);
        PrivateKey key;
        try {
            key = toPrivateKey(keystorekeyResource.getInputStream(), null);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new IllegalArgumentException("Input stream does not contain valid private key.", e);
        }
        privateKey = Numeric.toHexString(key.getEncoded()).replace("0x", "");
        try {
            //Create object from unencrypted private key
//            keyInfoStr = keyInfoStr.replace("-----BEGIN PRIVATE KEY-----", "");
//            keyInfoStr = keyInfoStr.replace("-----END PRIVATE KEY-----", "");
//            byte[] encoded = Base64.decode(keyInfoStr);
//            PKCS8EncodedKeySpec kspec = new PKCS8EncodedKeySpec(encoded);
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            PrivateKey unencryptedPrivateKey = kf.generatePrivate(kspec);


//            privateKey = unencryptedPrivateKey.toString();
            System.out.println("* private key:" + privateKey);
            ECKeyPair keyPair = createECDSAKeyPair(privateKey);
            publicKey = keyPair.getPublicKey().toString(16);
//            Credentials credential = GenCredential.create(privateKey);

            System.out.println("====LOADED KEY INFO ===");
            System.out.println("* private key:" + privateKey);
            System.out.println("* public key :" + publicKey);
//            System.out.println("* account: " + account);
//            System.out.println("* credential address: " + credential.getAddress());
            return RetCode.success;
        } catch (Exception e) {
            logger.error(
                    "load private key from " + keyFile + " failed, error message:" + e.getMessage());
            return RetCode.loadKeyInfoFailed;
        }
    }

    private static int writeFile(String keyFile, String content) {
        File file = null;
        PrintStream ps = null;
        try {
            file = new File(keyFile);
            ps = new PrintStream(new FileOutputStream(file));
            ps.println(content);
            return RetCode.success;
        } catch (Exception e) {
            System.out.println("write " + content + " to " + keyFile + " failed");
            logger.error(
                    "write " + content + " to " + keyFile + " failed, error message: " + e.getMessage());
        } finally {
            if (ps != null) ps.close();
        }
        return RetCode.storeKeyInfoFailed;
    }

    public int storeKeyInfo(String keyFile) {
        try {
            // Map<String, String> keyMap = new HashMap<String, String>();
            JSONObject keyMapJson = new JSONObject();
            keyMapJson.put(privJsonKey, privateKey);
            keyMapJson.put(pubJsonKey, publicKey);
            keyMapJson.put(accountJsonKey, account);

            String keyJsonInfo = keyMapJson.toString();
            System.out.println("== SAVED KEY INFO: " + keyJsonInfo);
            return writeFile(keyFile, keyJsonInfo);
        } catch (Exception e) {
            System.out.println(
                    "store keyInfo to " + keyFile + " failed, error message: " + e.getMessage());
            logger.error("store keyInfo to " + keyFile + " failed, error message: " + e.getMessage());
            return RetCode.storeKeyInfoFailed;
        }
    }

    private static ECKeyPair createECDSAKeyPair(String privKey) {
        try {
            BigInteger bigPrivKey = new BigInteger(privKey, 16);
            ECKeyPair keyPair = ECKeyPair.create(bigPrivKey);
            return keyPair;
        } catch (Exception e) {
            logger.error("create keypair of ECDSA failed, error msg:" + e.getMessage());
            return null;
        }
    }

    private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf encodedKeyBuf, String keyPassword)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidAlgorithmParameterException, KeyException, IOException {

        byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
        encodedKeyBuf.readBytes(encodedKey).release();

        PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(
                keyPassword == null ? null : keyPassword.toCharArray(), encodedKey);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(encodedKeySpec);
        } catch (InvalidKeySpecException ignore) {
            try {
                return KeyFactory.getInstance("DSA").generatePrivate(encodedKeySpec);
            } catch (InvalidKeySpecException ignore2) {
                try {
                    return KeyFactory.getInstance("EC").generatePrivate(encodedKeySpec);
                } catch (InvalidKeySpecException e) {
                    throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e);
                }
            }
        }
    }

    /**
     * Generates a key specification for an (encrypted) private key.
     *
     * @param password characters, if {@code null} an unencrypted key is assumed
     * @param key bytes of the DER encoded private key
     *
     * @return a key specification
     *
     * @throws IOException if parsing {@code key} fails
     * @throws NoSuchAlgorithmException if the algorithm used to encrypt {@code key} is unknown
     * @throws NoSuchPaddingException if the padding scheme specified in the decryption algorithm is unknown
     * @throws InvalidKeySpecException if the decryption key based on {@code password} cannot be generated
     * @throws InvalidKeyException if the decryption key based on {@code password} cannot be used to decrypt
     *                             {@code key}
     * @throws InvalidAlgorithmParameterException if decryption algorithm parameters are somehow faulty
     */
    protected static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key)
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        if (password == null) {
            return new PKCS8EncodedKeySpec(key);
        }

        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
        SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);

        Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        cipher.init(Cipher.DECRYPT_MODE, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());

        return encryptedPrivateKeyInfo.getKeySpec(cipher);
    }

    static PrivateKey toPrivateKey(InputStream keyInputStream, String keyPassword) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeySpecException,
            InvalidAlgorithmParameterException,
            KeyException, IOException {
        if (keyInputStream == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(readPrivateKey(keyInputStream), keyPassword);
    }

    static ByteBuf readPrivateKey(InputStream in) throws KeyException {
        String content;
        try {
            content = readContent(in);
        } catch (IOException e) {
            throw new KeyException("failed to read key input stream", e);
        }

        Matcher m = KEY_PATTERN.matcher(content);
        if (!m.find()) {
            throw new KeyException("could not find a PKCS #8 private key in input stream" +
                    " (see http://netty.io/wiki/sslcontextbuilder-and-private-key.html for more information)");
        }

        ByteBuf base64 = Unpooled.copiedBuffer(m.group(1), CharsetUtil.US_ASCII);
        ByteBuf der = io.netty.handler.codec.base64.Base64.decode(base64);
        base64.release();
        return der;
    }

    private static String readContent(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[8192];
            for (;;) {
                int ret = in.read(buf);
                if (ret < 0) {
                    break;
                }
                out.write(buf, 0, ret);
            }
            return out.toString(CharsetUtil.US_ASCII.name());
        } finally {
            safeClose(out);
        }
    }

    private static void safeClose(OutputStream out) {
        try {
            out.close();
        } catch (IOException e) {
            logger.warn("Failed to close a stream.", e);
        }
    }
}

