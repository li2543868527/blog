package LoginEx.com.ex.services;

import java.util.Random;

public class VerificationCodeGenerator {
    public String generateVerificationCode() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;
        int verificationCode = random.nextInt(max - min + 1) + min;
        return String.valueOf(verificationCode);
    }

}
