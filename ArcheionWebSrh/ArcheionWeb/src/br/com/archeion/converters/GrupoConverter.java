package br.com.archeion.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import javax.faces.convert.Converter;

import br.com.archeion.jsf.Util;
import br.com.archeion.modelo.grupo.Grupo;
import br.com.archeion.negocio.grupo.GrupoBO;

/**
 * Conversor para Etiqueta de Grupo
 * @author SInforme
 *
 */
public class GrupoConverter implements Converter {
	
	/**
	 * BO de Grupo
	 */
	private GrupoBO grupoBO = (GrupoBO) Util.getSpringBean("grupoBO");

	/**
	 * Transforma uma String da p�gina em um Objeto
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		
		Grupo func = grupoBO.findByName(arg2);
		
		return func;
	}

	/**
	 * Transforma um Objeto em uma String para forma��o da p�gina
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		Grupo func = (Grupo)arg2;
		return func!=null?func.getNome():null;
	}

}
