package br.gov.mg.bdmg.fs.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * Classe que possui varias utilidades de strng para todos os projetos.
 *
 * @author hsfaria
 *
 */
public abstract class StringUtil {

	public static final String EMPTY = "";
	private static final Locale LOCALE_BRASIL = new Locale("pt", "BR");

	/**
	 *
	 */
	private StringUtil() {
		super();
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static boolean nonEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * Remove de uma java.lang.String tudo que não for numérico.
	 *
	 * @param valor java.Lang.String contendo caracteres mistos, não apenas
	 *              numéricos.
	 *
	 * @return java.lang.String contendo somente caracteres numéricos de 0 a 9.
	 */
	public static String removerTudoQueNaoForNumerico(String valor) {
		try {
			if (valor == null) {
				return null;
			}
			return valor.replaceAll("[^0-9]", "");
		} catch (Exception ex) {
			System.out.println("Não foi possível remover tudo que não é numérico de: " + valor);
			return null;
		}
	}

	/**
	 * Completa uma java.lang.String a esquerda de acordo com o tamanho informado e
	 * o caracter à ser inserido até o tamanho máximo.
	 *
	 * @param valor         java.lang.String à ser completada.
	 *
	 * @param tamanhoMaximo Tamanho máximo da java.lang.String.
	 *
	 * @param completarCom  Caracter á ser preenchido até o tamanho máximo.
	 *
	 * @return java.lang.String completada de acordo com os parâmetros informados.
	 */
	public static String completarComEsquerda(String valor, int tamanhoMaximo, String completarCom) {
		try {
			return StringUtils.leftPad(valor, tamanhoMaximo, completarCom);
		} catch (Exception ex) {
			System.out.println("Não foi possível completar o valor \"" + valor + "\" com o tamanho máximo \""
					+ tamanhoMaximo + "\" a esqueda com \"" + completarCom + "\"");
			return null;
		}
	}

	public static String ifNullEmpty(String val) {
		if (val == null)
			return EMPTY;
		else
			return val;
	}

	/**
	 *
	 * @param stringAcentuada
	 * @return
	 */
	public static String removerAcentuacao(String stringAcentuada) {

		if (stringAcentuada == null) {
			return stringAcentuada;
		}
		return Normalizer.normalize(stringAcentuada, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * Método criado pelo joão para remover acentuação
	 *
	 * @param str
	 * @return
	 */
	public static String removerAcentos(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	/**
	 * Método para remover os caracteres especiais
	 *
	 * @param parametro A ser corrigido
	 * @return parametro sem os caracteres especiais
	 */
	public static String removerCaractersFormatacao(String param) {

		if (!StringUtils.isEmpty(param)) {

			return param.replaceAll("\\.", "").replaceAll("-", "").replaceAll("/", "").replaceAll("\\(", "")
					.replaceAll("\\)", "").replaceAll(" ", "").replaceAll("_", "");

		}

		return "";
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static boolean formatStringToBoolean(String string) {

		return "Sim".toLowerCase().equalsIgnoreCase(string.trim()) ? true : false;
	}

	/**
	 * Método responsável por parsear uma String no formato decimal para number
	 * Verificando possiveis caracteres invalidos.
	 *
	 * @param decimal
	 * @return
	 */
	public static Number parseCompleteString(String input) {

		ParsePosition pp = new ParsePosition(0);
		NumberFormat numberFormat = NumberFormat.getInstance(LOCALE_BRASIL);
		Number result = numberFormat.parse(input, pp);
		return pp.getIndex() == input.length() ? result : null;

	}

	/**
	 *
	 * @param booleanRecebido
	 * @return
	 */
	public static String formatBooleanToString(Boolean booleanRecebido) {

		if (booleanRecebido != null) {

			return booleanRecebido ? "Sim" : "Não";

		} else {

			return null;

		}

	}

	/**
	 *
	 * @param s
	 * @param delimiter
	 * @return
	 */
	public static String join(List<?> s, String delimiter) {
		if (s == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		if (!s.isEmpty()) {
			for (int i = 0; i < s.size(); i++) {
				if (i != 0) {
					builder.append(delimiter);
				}
				builder.append(s.get(i));
			}
		}
		return builder.toString();
	}

	/**
	 *
	 * @param valueToPad
	 * @param filler
	 * @param size
	 * @return
	 */
	public static String padL(String valueToPad, String filler, int size) {
		String res = valueToPad;
		while (res.length() < size) {
			res = filler + res;
		}
		return res;
	}

	/**
	 * Método para retirar todos caracteres que não são números
	 *
	 * @param numeros
	 *
	 * @return string
	 */
	public static String apenasNumeros(String numeros) {

		if (numeros != null) {
			return numeros.replaceAll("\\D", "");
		}
		return null;
	}

	/**
	 *
	 * @param valueToPad
	 * @param filler
	 * @param size
	 * @return
	 */
	public static String padR(String valueToPad, String filler, int size) {

		StringBuilder buf = new StringBuilder(valueToPad);

		while (buf.length() < size) {
			buf.append(filler);
		}
		return buf.toString();
	}

	public static String formatCurrency(Object valor) {
		return format(valor, "###,###.00");
	}

	public static String formatCpf(String cpf) {
		if (cpf == null || cpf.trim().equals("")) {
			return "";
		}

		String cpfMask = "###.###.###-##";
		for (int i = 0; i < cpf.length(); i++) {
			cpfMask = cpfMask.replaceFirst("#", cpf.substring(i, i + 1));
		}
		return cpfMask.replaceAll("#", "");
	}

	public static String formatCnpj(String cnpj) {
		if (cnpj == null || cnpj.trim().equals("")) {
			return "";
		}

		String cnpjMask = "##.###.###/####-##";
		for (int i = 0; i < cnpj.length(); i++) {
			cnpjMask = cnpjMask.replaceFirst("#", cnpj.substring(i, i + 1));
		}
		return cnpjMask.replaceAll("#", "");
	}

	public static String format(Object valor, String formatMask) {

		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator('.');
		dfs.setDecimalSeparator(',');

		DecimalFormat format = new DecimalFormat(formatMask);
		format.setDecimalFormatSymbols(dfs);

		return format.format(valor);
	}

	public static String fromTimeStamp(Timestamp timestamp) {
		Date outraData = new Date(timestamp.getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(outraData);
	}

	/**
	 * Metodo auxiliar para transformar String em BigDecimal
	 *
	 * @param value
	 * @return BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(final String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				final String valueNum = Pattern.compile("[^0-9]").matcher(value).replaceAll("");
				final BigDecimal valueBig = new BigDecimal(valueNum);
				return valueBig;
			}
		} catch (final NumberFormatException e) {
			return null;
		}
		return new BigDecimal(0);
	}

	/**
	 * Metodo auxiliar para transformar String em Integer
	 *
	 * @param value
	 * @return Integer
	 */
	public static Integer stringToInteger(final String value) {
		try {
			if (value != null && !value.trim().equals("")) {
				final String valueNum = Pattern.compile("[^0-9]").matcher(value).replaceAll("");
				final Integer valueInt = Integer.valueOf(valueNum);
				return valueInt;
			}
		} catch (final NumberFormatException e) {
			return null;
		}
		return 0;
	}

}
