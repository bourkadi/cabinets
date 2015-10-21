package com.bourgadix.cryptoUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.RandomStringUtils;

/**
 * A tester for the CryptoUtils class.
 * 
 * @author www.codejava.net
 *
 */
public class CryptoUtilsTest {

	public static byte[] makeKey() throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(192);
		SecretKey key = kgen.generateKey();
		return key.getEncoded();

	}

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		String c = RandomStringUtils.randomAlphanumeric(16);
		byte[] bs = c.getBytes();

		for (byte b : bs) {
			System.out.println(b);
		}

		System.out.println(c);
		System.out.println(c.length());
		File inputFile = new File("pgadmin.log");
		File encryptedFile = new File("document.encrypted");
		// File decryptedFile = new File("document.decrypted");

		try {
			CryptoUtils.encrypt(c, inputFile, encryptedFile);
			// CryptoUtils.decrypt(key, encryptedFile, decryptedFile);
			System.out.println("good crypt");
		} catch (CryptoException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
}
