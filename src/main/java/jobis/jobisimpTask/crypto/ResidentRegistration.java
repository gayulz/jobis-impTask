package jobis.jobisimpTask.crypto;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class ResidentRegistration {
    private static final String AES = "AES";
//    @Value("${cryptionKey}")
//    private static String KEY;
        private static String KEY = "1234567890123456";

    // 주민등록번호 데이터 암호화 메서드
    public static String encrypt(String data) throws Exception {

        // 주민등록번호 앞뒤 나누기
        String[] parts = data.split("-");
        String frontPart = parts[0];
        String backPart = parts[1];

        // 뒷부분만 암호화
        String encryptedBackPart = encryptBackPart(backPart);

        // 암호화된 뒷부분과 앞부분을 합침
        return frontPart + "-" + encryptedBackPart;
    }

    // 주민등록번호 데이터 복호화 메서드
    public static String decrypt(String encryptedData) throws Exception {
        // 앞부분과 암호화된 뒷부분 분리
        String[] parts = encryptedData.split("-");
        String frontPart = parts[0];
        String encryptedBackPart = parts[1];

        // 암호화된 뒷부분을 복호화
        String decryptedBackPart = decryptBackPart(encryptedBackPart);

        // 암호화되지 않은 뒷부분과 앞부분을 합침
        return frontPart + "-" + decryptedBackPart;
    }

    // 뒷부분 암호화 메서드
    private static String encryptBackPart(String backPart) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(backPart.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // 뒷부분 복호화 메서드
    private static String decryptBackPart(String encryptedBackPart) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedBackPart));
        return new String(original);
    }
}

