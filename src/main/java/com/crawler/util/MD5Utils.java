package com.crawler.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Think
 *
 */

/**
 * MD5工具类
 * 
 * MD5的算法在RFC1321 中定义 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： 
 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e 
 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 
 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0 
 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 * 
 * @author xiayanming
 * 
 */
public class MD5Utils {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	/**
	 * 根据源字符内容产生器摘要信息
	 * 
	 * @param str      源字符内容
	 * @return 摘要信息或者null
	 */
	public static String getDigest(String str) {

		String messageDigest = null;

		try {

			MessageDigest md5 = MessageDigest.getInstance("MD5");

			md5.update(str.getBytes());

			messageDigest = byteArrayToHexString(md5.digest());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return messageDigest;
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param bytes		字节数组
	 * @return			16进制字串
	 */
	public static String byteArrayToHexString(byte[] bytes) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			resultSb.append(byteToHexString(bytes[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 字节为16进制字串
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
