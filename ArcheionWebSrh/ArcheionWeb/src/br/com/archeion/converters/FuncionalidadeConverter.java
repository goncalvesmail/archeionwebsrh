package br.com.archeion.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import br.com.archeion.jsf.Util;
import br.com.archeion.modelo.funcionalidade.Funcionalidade;
import br.com.archeion.negocio.funcionalidade.FuncionalidadeBO;

/**
 * Conversor para Etiqueta de Funcionalidade
 * @author SInforme
 *
 */
public class FuncionalidadeConverter implements Converter {

	/**
	 * BO de Funcionalidade
	 */
	private FuncionalidadeBO funcionalidadeBO = (FuncionalidadeBO) Util.getSpringBean("funcionalidadeBO");

	/**
	 * Transforma uma String da página em um Objeto
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		
		Funcionalidade func = funcionalidadeBO.findByDescricao(arg2);
		
		return func;
	}

	/**
	 * Transforma um Objeto em uma String para formação da página
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		Funcionalidade func = (Funcionalidade)arg2;
		return func.getDescricao();
	}

}
