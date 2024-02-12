package jobis.jobisimpTask;


import jobis.jobisimpTask.crypto.Password;
import jobis.jobisimpTask.crypto.ResidentRegistration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CryptTest {

    /*
     * 비밀번호 원본을 암호화 -> 암호화 본을 복호화 했을때 동일한지 체크 */
    @Test
    @DisplayName("비밀번호 암호화 및 복호화 테스트")
    public void testPassword() {
        // Given
        String originalData = "hong1212";

        // When
        String encryptedData = null;

        try {
            encryptedData = Password.encrypt(originalData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String decryptedData = null;
        try {
            decryptedData = Password.decrypt(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        assertEquals(originalData, decryptedData);
    }

    /*
    * 주민번호 원본을 암호화 -> 암호화 본을 복호화 했을때 동일한지 체크 */
    @Test
    @DisplayName("주민등록번호 암호화 및 복호화 테스트")
    public void testResidentRegistration() {
        // Given
        String originalData = "101010-1234567";

        // When
        String encryptedData = null;

        try {
            encryptedData = ResidentRegistration.encrypt(originalData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String decryptedData = null;
        try {
            decryptedData = ResidentRegistration.decrypt(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Then
        assertEquals(originalData, decryptedData);
    }
}
