package com.whatsweb.whatswebscanner.gbwhats.gb_local;

import com.dinuscxj.circleprogressbar;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class gb_JsonData {
    static char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private IvParameterSpec b = new IvParameterSpec(circleprogressbar.getcircleprogressbarnumber().getBytes());
    private SecretKeySpec c = new SecretKeySpec(circleprogressbar.getcircleprogressbarnumber().getBytes(), "AES");
    private Cipher d;

    public gb_JsonData() {
        try {
            this.d = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
    }

    public static byte[] a(String str) { //hextobytes
        if (str == null || str.length() < 2) {
            return null;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Integer.parseInt(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static native String GenerateUnique();

    public byte[] b(String str) throws Exception {
        if (str == null || str.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.d.init(Cipher.DECRYPT_MODE, this.c, this.b);
            byte[] doFinal = this.d.doFinal(a(str));
            if (doFinal.length <= 0) {
                return doFinal;
            }
            int i = 0;
            for (int length = doFinal.length - 1; length >= 0; length--) {
                if (doFinal[length] == (byte) 0) {
                    i++;
                }
            }
            if (i <= 0) {
                return doFinal;
            }
            byte[] bArr = new byte[(doFinal.length - i)];
            System.arraycopy(doFinal, 0, bArr, 0, doFinal.length - i);
            return bArr;
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[decrypt] ");
            stringBuilder.append(e.getMessage());
            throw new Exception(stringBuilder.toString());
        }
    }
}