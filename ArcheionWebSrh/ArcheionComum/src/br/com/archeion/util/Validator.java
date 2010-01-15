package br.com.archeion.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CompareDateException;
import br.com.archeion.exception.InvalidFieldException;
import br.com.archeion.exception.InvalidFieldType;

/**
 * Classe utilit�ria respons�vel pelas valida��es.
 *  
 */
public final class Validator {

	/**
	 * LOG utilizado na classe.
	 */
	private static final Logger LOG = Logger.getLogger(Validator.class);

	/**
	 * Crit�rio para compara��o de datas levando em considera��o somente Ano, M�s e Dia.
	 */
	public static final int[] CRITERIO_YYYYMMDD = { Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH };

	/**
	 * Verifica se uma data � anterior (excluindo o dia de hoje) a outra
	 * compararando os crit�rios especificados.
	 * 
	 * @param criterios Os crit�rios a serem usados (Constants da classe Calendar)
	 * @param dataA Uma data
	 * @param dataB Outra data
	 * 
	 * @return <code>true</code> se dataA � anterior a dataB,
	 *         <code>false</code> sen�o.
	 */
	public static boolean isDataAnterior(final int[] criterios,
			final Date dataA, final Date dataB) {
		final Calendar calendar = Calendar.getInstance();
		Arrays.sort(criterios);

		if (dataA == null && dataB == null) {
			return true;
		}
		if (dataA == null || dataB == null) {
			return false;
		}

		boolean b = false;
		final int[] values = new int[criterios.length];

		calendar.setTime(dataA);
		for (int i = 0; i < criterios.length; i++) {
			values[i] = calendar.get(criterios[i]);
		}
		calendar.setTime(dataB);
		for (int i = 0; i < criterios.length; i++) {
			final int x = calendar.get(criterios[i]);
			if (values[i] < x) {
				b = true;
				break;
			} else if (values[i] > x) {
				break;
			}
		}
		return b;
	}

	/**
	 * Verifica se uma data � posterior (excluindo o dia de hoje) a outra
	 * compararando os crit�rios especificados.
	 * 
	 * @param criterios Os crit�rios a serem usados (Constants da classe Calendar)
	 * @param dataA Uma data
	 * @param dataB Outra data
	 * 
	 * @return <code>true</code> se dataA posterior a dataB,
	 *         <code>false</code> sen�o.
	 */
	public static boolean isDataPosterior(final int[] criterios,
			final Date dataA, final Date dataB) {
		final Calendar calendar = Calendar.getInstance();
		Arrays.sort(criterios);

		if (dataA == null && dataB == null) {
			return true;
		}
		if (dataA == null || dataB == null) {
			return false;
		}

		boolean b = false;
		final int[] values = new int[criterios.length];

		calendar.setTime(dataA);
		for (int i = 0; i < criterios.length; i++) {
			values[i] = calendar.get(criterios[i]);
		}
		calendar.setTime(dataB);
		for (int i = 0; i < criterios.length; i++) {
			final int x = calendar.get(criterios[i]);
			if (values[i] > x) {
				b = true;
				break;
			} else if (values[i] < x) {
				break;
			}
		}
		return b;
	}

	/**
	 * Lista de exce��es.
	 */
	private List<BusinessException> exceptions;

	/**
	 * Cont�m todos os caracteres especiais.
	 */
	private final String CARACTERES_ESPECIAIS = "\"!@#$%�&*()_+'�������-�=�`[{�~^]}�<,>.:;?/�\\";

	/**
	 * � poss�vel instanciar esta classe.
	 */
	public Validator() {
		exceptions = new ArrayList<BusinessException>();
	}

	/**
	 * Adiciona um erro a lista de erros.
	 * 
	 * @param e
	 *            Erro que ser� guardado.
	 */
	public void addError(final BusinessException e) {
		exceptions.add(e);
	}

	/**
	 * @return False caso n�o exista erro.
	 */
	public boolean existError() {
		return !exceptions.isEmpty();
	}

	/**
	 * @return O campo exceptions
	 */
	public List<BusinessException> getExceptions() {
		return exceptions;
	}

	/**
	 * Verifica se o campo est� preechido e sem os caracteres especiais.
	 * 
	 * @param field Valor do campo que ser� validado.
	 * @param msg Mensagem que ser� exibida.
	 */
	public void validarCampoObrigatorio(final Object field, final String msg) {

		String campo = null;

		if (field == null) {
			exceptions.add(new BusinessException(msg));
		} else {
			campo = field.toString();
			/*
			 * Faz a itera��o at� que um caractere que n�o seja especial seja
			 * encontrado.
			 */
			boolean campoValido = false;
			for (int i = 0; i < campo.length(); i++) {
				if (!CARACTERES_ESPECIAIS.contains("" + campo.charAt(i))) {
					campoValido = true;
					break;
				}
			}

			if (!campoValido) {
				exceptions.add(new BusinessException(msg));
			}
		}

	}

	/**
	 * Valida emails.
	 * 
	 * @param email Email que ser� validado
	 */
	public void validarEmail(final String email) {
		validarEmail(email, null);
	}

	/**
	 * Valida emails. <BR />
	 * 
	 * @param email Email que ser� validado
	 * @param campo Campo que ser� mostrado na mensagem de erro
	 */
	public void validarEmail(final String email, final String campo) {
		Validator.LOG.info("Validando email: " + email);

		// final Pattern p = Pattern
		// .compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
		final Pattern p = Pattern
		.compile("^[a-z0-9_\\.-]{2,}@([a-z0-9_-]{2,}\\.)+[a-z]{2,4}$");
		InvalidFieldException invalidFieldException = null;
		if (campo != null) {
			invalidFieldException = new InvalidFieldException(
					InvalidFieldType.EMAIL, new String[] { campo });
		} else {
			invalidFieldException = new InvalidFieldException(
					InvalidFieldType.EMAIL);
		}

		if (email == null) {
			exceptions.add(invalidFieldException);
		} else {
			final Matcher m = p.matcher(email);
			if (!m.find()) {
				exceptions.add(invalidFieldException);
			}
		}
	}

	/**
	 * Valida ceps.
	 * 
	 * @param cep CEP que ser� validado
	 */
	public void validarCEP(final String cep) {
		this.validarCEP(cep, new InvalidFieldException(InvalidFieldType.CEP));
	}

	/**
	 * Valida ceps.
	 * 
	 * @param cep CEP que ser� validado
	 * @param exception Exce��o.
	 */
	public void validarCEP(final String cep, final BusinessException exception) {
		final int tamanhoCEP = 8;

		if (cep.length() != tamanhoCEP) {
			exceptions.add(exception);
		} else {
			validarCampoNumerico(cep, InvalidFieldType.CEP);

			final char value = cep.charAt(0);
			boolean valido = false;
			for (int i = 1; i < cep.length(); i++) {
				if (cep.charAt(i) != value) {
					valido = true;
				}
			}

			if (!valido) {
				exceptions.add(exception);
			}
		}
	}

	/**
	 * Valida se a primeira data � anterior a segunda data.
	 * 
	 * @param dataInicio Data de in�cio
	 * @param dataFim Data de t�rmino
	 */
	public void validateDatas(final Date dataInicio, final Date dataFim) {
		validateDatas(dataInicio, dataFim, null);
	}

	/**
	 * Valida se a primeira data � anterior a segunda data.
	 * 
	 * @param dataInicio Data de in�cio
	 * @param dataFim Data de t�rmino
	 * @param mensagem Mensagem que ser� exibida para o usu�rio.
	 */
	public void validateDatas(final Date dataInicio, final Date dataFim,
			final String mensagem) {
		if ((dataInicio == null) || (dataFim == null)
				|| (dataFim.before(dataInicio))) {
			if (mensagem == null) {
				exceptions.add(new CompareDateException());
			} else {
				exceptions.add(new CompareDateException(mensagem));
			}
		}
	}

	/**
	 * M�todo para validar campo num�rico.
	 * 
	 * @param numero n�mero que ser� validado.
	 * @param invalidFieldType Tipo de valida��o
	 */
	public void validarCampoNumerico(final String numero, final InvalidFieldType invalidFieldType) {
		if (numero.length() > 0) {
			if (!NumberUtils.isNumber(numero)) {
				exceptions.add(new InvalidFieldException(invalidFieldType));
			}
		}
	}

	/**
	 * M�todo para validar uma String toda igual.
	 * 
	 * @param string String que ser� validado.
	 * @param invalidFieldType Tipo de valida��o
	 */
	public void validarStringIgual(final String string,
			final InvalidFieldType invalidFieldType) {
		if (string.length() > 0) {
			int total = 0;
			// Compara a quantidade de vezes que um caractere da String � igual
			// ao primeiro.
			for (int i = 1; i < string.length(); i++) {
				if (string.charAt(i) == string.charAt(0)) {
					total++;
				}
			}
			// Se a quantidade de caracteres iguais for igual ao tamanho da
			// String -
			// 1, pois o primeiro n�o � comparado, ent�o a String � toda igual.
			if (total == (string.length() - 1)) {
				exceptions.add(new InvalidFieldException(invalidFieldType));
			}
		}
	}

	/**
	 * Valida se o primeiro n�mero � menor que o segundo n�mero.
	 * 
	 * @param numeroInicio Primeiro N�mero
	 * @param numeroFim  Segundo N�mero
	 */
	public void validateNumbers(final Number numeroInicio,
			final Number numeroFim) {
		validateNumbers(numeroInicio, numeroFim, null);
	}

	/**
	 * Valida se o primeiro n�mero � menor que o segundo n�mero.
	 * 
	 * @param numeroInicio Primeiro N�mero
	 * @param numeroFim Segundo N�mero
	 * @param mensagem Mensagem que ser� exibida para o usu�rio.
	 */
	public void validateNumbers(final Number numeroInicio,
			final Number numeroFim, final String mensagem) {
		Double primeiro = (Double) numeroInicio;
		Double segundo = (Double) numeroFim;
		if ((primeiro == null) || (segundo == null)
				|| (segundo.compareTo(primeiro) < 0)) {
			// || (segundo.equals(primeiro)) || (segundo.compareTo(primeiro) <=
			// 0) ) {
			if (mensagem == null) {
				exceptions.add(new CompareDateException());
			} else {
				exceptions.add(new CompareDateException(mensagem));
			}
		}
	}

}
