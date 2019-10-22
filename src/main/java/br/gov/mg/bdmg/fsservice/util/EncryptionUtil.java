package br.gov.mg.bdmg.fsservice.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.gov.mg.bdmg.fsservice.util.base.FileUtilConstants;
import br.gov.mg.bdmg.fsservice.util.base.NumericalConstants;

public class EncryptionUtil {

	private static final Logger LOGGER = Logger.getLogger(EncryptionUtil.class.getName());
	private static final int MAX_BIT_HASH = 32;
	private static final String HEX = "%02X"; 
	
	private EncryptionUtil() {
		super();
	}

	public static String encryptIdArquivo(String id) {
		String hashtext = null;
		try {
			byte[] bytesOfDefaultPassword = id.getBytes(FileUtilConstants.Encoding.UTF_8);
			byte[] bytesOfEncryptedDefaultPassword = MessageDigest.getInstance(FileUtilConstants.CryptographicHash.MD5)
					.digest(bytesOfDefaultPassword);
			BigInteger bigInt = new BigInteger(NumericalConstants.Base.Decimal.ONE, bytesOfEncryptedDefaultPassword);
			hashtext = bigInt.toString(NumericalConstants.Base.Hexadecimal.VALUE);
			while (hashtext.length() < MAX_BIT_HASH) {
				hashtext = NumericalConstants.Base.Decimal.ZERO.toString() + hashtext;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return hashtext.toUpperCase();
	}
	
	public static String toHex(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format(HEX, data[i]));
		}
		return sb.toString();
	}
}
