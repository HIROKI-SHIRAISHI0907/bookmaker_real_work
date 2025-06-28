package dev.application.common.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 特定のオブジェクトを暗号化,復号するロジック
 * @author shiraishitoshio
 *
 */
public class EncryptLogic {

	/** コンストラクタ生成禁止 */
	private EncryptLogic() {}

	/** AES */
	private static final String AES = "AES";

	/** SHA */
	private static final String SHA_1 = "SHA-1";

	/**
	 * AES暗号化と復号化を行うメソッド
	 * @param obj
	 * @param key
	 * @return
	 * @throws Exception
	 */
    public static byte[] encryptObject(Object obj, SecretKey key) throws Exception {
        // オブジェクトをシリアライズしてバイト配列に変換
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        byte[] objectBytes = byteArrayOutputStream.toByteArray();

        // AESで暗号化
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(objectBytes);
    }

    /**
     * 復号する
     * @param encryptedData
     * @param key
     * @return
     * @throws Exception
     */
    public static Object decryptObject(byte[] encryptedData, SecretKey key) throws Exception {
        // AESで復号化
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(encryptedData);

        // バイト配列をオブジェクトにデシリアライズ
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    /**
     * 秘密鍵を生成するメソッド
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateKey(String key) throws NoSuchAlgorithmException {
        // AES鍵生成
        MessageDigest sha = MessageDigest.getInstance(SHA_1);
        byte[] keyBytes = sha.digest(key.getBytes());
        keyBytes = java.util.Arrays.copyOf(keyBytes, 16); // 16バイトの長さに調整
        return new SecretKeySpec(keyBytes, AES);
    }

}
