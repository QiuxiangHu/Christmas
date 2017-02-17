package com.amily.Christmas.common;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DesUtil {
	// 算法名称
	public static final String KEY_ALGORITHM = "desede";
	// 算法名称/加密模式/填充方式
	public static final String CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";

	public static final String PROVIDER = "BC";
	public static final String ENCODING = "utf-8";

	private static final byte[] iv = { 1, 2, 3, 4, 5, 16, 17, 8 };

	static {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	public static String encode(String key, String val) throws Exception {
		return Base64.encodeBase64String(des3EncodeCBC(key.getBytes(ENCODING), iv, val.getBytes(ENCODING)));
	}

	public static String decode(String key, String val) throws Exception {
		return new String(des3DecodeCBC(key.getBytes(ENCODING), iv, Base64.decodeBase64(val)), ENCODING);
	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {

		Key deskey = keyGenerator(new String(key, ENCODING));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 
	 * 生成密钥key对象
	 * 
	 * @param KeyStr
	 *            密钥字符串
	 * @return 密钥对象
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws Exception
	 */
	private static Key keyGenerator(String keyStr) throws Exception {
		byte input[] = hexString2Bytes(keyStr);
		DESedeKeySpec KeySpec = new DESedeKeySpec(input);
		SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM, PROVIDER);
		return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	// 从十六进制字符串到字节数组转换
	private static byte[] hexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
		Key deskey = keyGenerator(new String(key));
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}
}
