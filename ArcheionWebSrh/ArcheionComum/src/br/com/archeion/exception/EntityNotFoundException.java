/**
 * 
 */
package br.com.archeion.exception;

import br.com.archeion.resources.MessagesResources;

/**
 * Exce��o retornada em caso de entidade n�o encontrada.
 * 
 */
public class EntityNotFoundException extends BusinessException {

	/**
	 * serialVersionUID de EntityNotFoundException.
	 */
	private static final long serialVersionUID = 6205059783156737675L;

	/**
	 * Mensagem que ser� exibida para o usu�rio.
	 */
	private static final String MSG_EXCEPTION = "error.business.entityNotFound";

	/**
	 * Campo que gerou a exce��o.
	 */
	private final String entity;

	/**
	 * Construtor
	 * 
	 * @param entity
	 *            nome da entidade que gerou a exce��o
	 */
	public EntityNotFoundException(final String entity) {
		super(MSG_EXCEPTION);
		this.entity = entity;

	}

	@Override
	public String getErrorI18n() {
		return super.getErrorI18n(new String[] { entity });
	}

	/**
	 * 
	 * @see br.com.archeion.exception.ArcheionException#getMessage()
	 * 
	 * @return Mensagem
	 */
	@Override
	public String getMessage() {
		String fieldName = MessagesResources.getBundleMessage(this.getEntity());
		return super.getErrorI18n(new String[] { fieldName });
	}

	/**
	 * @return entity
	 */
	public String getEntity() {
		return entity;
	}

}
