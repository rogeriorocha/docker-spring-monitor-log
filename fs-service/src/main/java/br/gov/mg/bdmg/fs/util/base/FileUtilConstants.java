package br.gov.mg.bdmg.fs.util.base;

public interface FileUtilConstants {

	interface Encoding {
		String UTF_16_BE = "utf16be";
		String UTF_16_LE = "utf16le";
		String UTF_16 = "utf16";
		String UTF_8 = "utf8";
		String ASCII = "ascii";
		String ISO8859_1 = "iso8859_1";
	}
	
	interface CryptographicHash {
		String MD5 = "MD5";
	}
	
	interface Base {
		int HEXADECIMAL = 16;
	}
	
	interface ExceptionsMessage{
		String ARQUIVO_NAO_ENCONTRADO = "Arquivo nao encontrado";
	}
	
	public interface Util{
		String COMPACTAR_BUILDER = " ,compactar=";
		String TO_ENCODE_BUILDER = " ,toEncode=";
		String FROM_ENCODE_BUILDER = " ,fromEncode=";
		String ID_BUILDER = " ,id=";
		String ID_SAIDA = "id=";
		String FROM = "from=";
		String FILE_NAME = " filename=";
		String TO_BUILDER = " ,to=";
		String ARQUIVO_DADO_BUILDER = " ArquivoDado=";
		String UNKNOW = "unknow";
		String COD_FILE = "cod_arq=";
		String TEXTO = " texto=";
		String UNION = "union=";
		String WATERMARK = "watermark=";
		String EXTENSAO_PDF = ".pdf";
		String EXTENSAO_HASHFILE = ".hashfile";
		String UNION_ = "union_";
		String WATERMARK_ = "watermark_";
	}
}
