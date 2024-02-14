package jobis.jobisimpTask.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class Password {
    private static final String AES = "AES";
    @Value("${cryptionKey}")
    private static String KEY;


    // 비밀번호 데이터 암호화 메서드
    public static String encrypt(String data) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 비밀번호 데이터 복호화 메서드
    public static String decrypt(String encryptedData) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }

}
