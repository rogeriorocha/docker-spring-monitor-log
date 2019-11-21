package br.gov.mg.bdmg.fs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

import br.gov.mg.bdmg.fs.exception.FileUtilException;
import br.gov.mg.bdmg.fs.util.base.FileUtilConstants;
import br.gov.mg.bdmg.fs.util.base.NumericalConstants.Base.Decimal;

public class PathUtil {

	private static final String DIRECTORY_PATH = "DIRECTORY_PATH";
	private static final String FALHA_AO_SALVAR_ARQUIVO = "Falha ao salvar arquivo";
	private static final String EXTENSAO = ".hashfile";

	private String firstFolder;

	private String folder;

	private String nameFile;

	private String rootPath;

	public void delete() {
		
		File rootFolder = new File(rootPath);
		File file = new File(new File(new File(rootFolder, firstFolder), folder), this.nameFile);
		File fileHash = new File(new File(new File(rootFolder, firstFolder), folder), this.nameFile+EXTENSAO);
		
		if (file.exists())
			file.delete();
		
		if (fileHash.exists())
			fileHash.delete();
	}

	public PathUtil(Long id, String rootPath) {
		this.rootPath = rootPath;
		String hex = EncryptionUtil.encryptIdArquivo(id.toString());
		this.nameFile = id.toString();
		this.firstFolder = hex.substring(Decimal.ZERO, Decimal.TWO);
		this.folder = hex.substring(Decimal.TWO, Decimal.FOUR);
	}

	public File getFile(boolean createIfNotExist) throws IOException {
		File rootFolder = new File(rootPath);
		File path = new File(new File(rootFolder, firstFolder), folder);
		if (path.exists()) {
			return new File(path, nameFile);
		} else if (createIfNotExist) {
			FileUtil.forceMkdir(path);
			return new File(path, nameFile);
		} else {
			throw new FileNotFoundException(FileUtilConstants.ExceptionsMessage.ARQUIVO_NAO_ENCONTRADO);
		}
	}

	public static String saveFile(byte[] is, File file) throws IOException, FileUtilException {
		String repositoryHash = null;
		try {
			MessageDigest digest = MessageDigest.getInstance(FileUtilConstants.CryptographicHash.MD5);

			digest.update(is);

			byte[] hashedBytes = digest.digest();

			repositoryHash = EncryptionUtil.toHex(hashedBytes);

			FileOutputStream fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(is);
			fileOutputStream.flush();
			fileOutputStream.close();

			save(repositoryHash, file);
		} catch (IOException i) {
			throw new IOException(FALHA_AO_SALVAR_ARQUIVO, i);
		} catch (Exception e) {
			throw new FileUtilException(e);
		}
		return repositoryHash;
	}

	private static void save(String repository, File file) throws IOException {
		String hashFileName = getHashFileName(file);
		FileWriter fWriter = null;
		try {
			File hashFile = new File(hashFileName);
			boolean okToWrite = true;
			if (hashFile.exists()) {
				okToWrite = hashFile.delete();
			}
			if (okToWrite) {
				fWriter = new FileWriter(hashFile);
				try {
					fWriter.write(repository);
				} finally {
					fWriter.close();
				}
			}
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}

	private static String getHashFileName(File file) {
		return file.getPath() + EXTENSAO;
	}

}
