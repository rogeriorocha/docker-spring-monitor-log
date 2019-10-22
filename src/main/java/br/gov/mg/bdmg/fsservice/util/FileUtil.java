package br.gov.mg.bdmg.fsservice.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import br.gov.mg.bdmg.fsservice.exception.FileUtilException;
import br.gov.mg.bdmg.fsservice.util.base.FileUtilConstants;



public class FileUtil {

	private static final String DECODE_EXCEPTION = "Falha na decodificacao do arquivo";
	private static final String ENCODE_EXCEPTION = "Falha na codificacao do arquivo";
	
	public static String decodedFile(String fromEncode, byte[] b) throws FileUtilException {
		String decoded = null;
		switch (fromEncode) {
			case FileUtilConstants.Encoding.ASCII:
				decoded = StringUtils.newStringUsAscii(b);
				break;
			case FileUtilConstants.Encoding.ISO8859_1:
				decoded = StringUtils.newStringIso8859_1(b);
				break;
			case FileUtilConstants.Encoding.UTF_16:
				decoded = StringUtils.newStringUtf16(b);
				break;
			case FileUtilConstants.Encoding.UTF_16_BE:
				decoded = StringUtils.newStringUtf16Be(b);
				break;
			case FileUtilConstants.Encoding.UTF_16_LE:
				decoded = StringUtils.newStringUtf16Le(b);
				break;
			case FileUtilConstants.Encoding.UTF_8:
				decoded = StringUtils.newStringUtf8(b);
				break;
			default:
				break;
		}
		if(decoded == null){
			throw new FileUtilException(DECODE_EXCEPTION);
		}
		return decoded;
	}
	
	public static byte[] encodedFile(String toEncode, String decoded) throws FileUtilException {
		byte[] encoded = null;
		switch (toEncode) {
			case FileUtilConstants.Encoding.ASCII:
				encoded = StringUtils.getBytesUsAscii(decoded);
				break;
			case FileUtilConstants.Encoding.ISO8859_1:
				encoded = StringUtils.getBytesIso8859_1(decoded);
				break;
			case FileUtilConstants.Encoding.UTF_16:
				encoded = StringUtils.getBytesUtf16(decoded);
				break;
			case FileUtilConstants.Encoding.UTF_16_BE:
				encoded = StringUtils.getBytesUtf16Be(decoded);
				break;
			case FileUtilConstants.Encoding.UTF_16_LE:
				encoded = StringUtils.getBytesUtf16Le(decoded);
				break;
			case FileUtilConstants.Encoding.UTF_8:
				encoded = StringUtils.getBytesUtf8(decoded);
				break;
			default:
				break;
		}
		if(encoded == null){
			throw new FileUtilException(ENCODE_EXCEPTION);
		}
		return encoded;
	}
	
	public static void forceMkdir(File directory) throws IOException {
		FileUtils.forceMkdir(directory);
	}
	
	public static byte[] convertToByteArray(InputStream inputStream) throws IOException {
		byte[] bytes = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			byte data[] = new byte[1024];
			int count;

			while ((count = inputStream.read(data)) != -1) {
				bos.write(data, 0, count);
			}

			bos.flush();
			bos.close();
			inputStream.close();

			bytes = bos.toByteArray();
		} catch (IOException e) {
			throw new IOException(e);
		}
		return bytes;
	}
	
	public static byte[] convertToByteArray(File file) throws IOException {
		InputStream inputStream = new FileInputStream(file);

		byte[] byteFile = IOUtils.toByteArray(inputStream);
		
		inputStream.close();

		return byteFile;
	}

	public static File convertToFile(byte[] b, String path) throws IOException {
		File tempFile = File.createTempFile(path, null);
		FileOutputStream fos = new FileOutputStream(tempFile);
		fos.write(b);
		fos.close();
		return tempFile;
	}
}
