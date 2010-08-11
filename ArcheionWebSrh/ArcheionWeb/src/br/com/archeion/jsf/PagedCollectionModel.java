package br.com.archeion.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import br.com.archeion.util.PaginationSupport;


/**
 * <p>
 * Classe respons�vel por realizar a pagina��o para componentes que usam
 * implementa��es de <code>javax.faces.model.DataModel</code>.
 * </p>
 * 
 * <p>
 * Como esta classe � independente de componentes de visualiza��o, ela pode ser
 * usada com qualquer conjunto de componentes, como Tomahawk, Trinidad ou Rich
 * Faces.
 * </p>
 * 
 * <p>
 * Essa implementa��o pode manter as p�ginas em cache ou descart�-las quando
 * novas p�ginas forem requisitadas. Para controlar esse comportamento, use o
 * atributo <code>keepCache</code> (<code>false</code> por default).
 * </p>
 * 
 * @author Tarso Bessa
 * @author Daniel Sousa
 * 
 */
public class PagedCollectionModel<E, S> extends DataModel implements
		Serializable {
	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 4029388233826640762L;

	/**
	 * Tamanho padr�o da quantidade de registros de cada p�gina
	 */
	private static final int DEFAULT_PAGE_SIZE = 25;
	/**
	 * Map com os dados de cada p�gina.
	 */
	protected Map<Integer, List<E>> internalData = new HashMap<Integer, List<E>>();
	/**
	 * Quantidade total de registros retornados pela consulta.
	 */
	protected int count;
	/**
	 * Total de registros por p�gina.
	 */
	protected int pageSize;
	/**
	 * �ndice que representa o elemento atual.
	 */
	protected int index;
	/**
	 * Objeto que � capaz de realizar a consulta sob demanda.
	 */
	protected PaginationSupport<E, S> paginationSupport;
	/**
	 * Objeto com os par�metros da busca.
	 */
	protected S searchObject;

	/**
	 * Atributo para checar se � a primeira consulta para evitar que o count
	 * seja calculado 2 vezes
	 */
	private boolean firstTime = true;
	/**
	 * Mant�m os dados em cache quando se navega em p�ginas.
	 */
	private boolean keepCache;

	public boolean isKeepCache() {
		return keepCache;
	}

	public void setKeepCache(boolean keepCache) {
		this.keepCache = keepCache;
	}

	/**
	 * Cria uma nova inst�ncia de PagedCollectionModel.
	 * 
	 * @param pageSize
	 *            tamanho da p�gina. Indica quantos registros ser�o exibidos por
	 *            p�gina.
	 * @param paginationSupport
	 *            objeto utilizado para buscas sob demanda
	 * @param searchObject
	 *            objeto com os par�metros da consulta.
	 * @throws IllegalArgumentException
	 *             Lan�ada se algum dos argumentos forem <code>null</code>.
	 */
	public PagedCollectionModel(int pageSize,
			PaginationSupport<E, S> paginationSupport, S searchObject) {
		super();
		if (pageSize <= 0) {
			throw new IllegalArgumentException(
					"pageSize precisa ser maior que 0");
		}
		if (searchObject == null) {
			throw new IllegalArgumentException(
					"searchObject n�o pode ser nulo.");
		}
		if (paginationSupport == null) {
			throw new IllegalArgumentException(
					"paginationSupport n�o pode ser nulo.");
		}
		
		this.paginationSupport = paginationSupport;
		this.searchObject = searchObject;
		this.pageSize = pageSize;
		count();
	}

	/**
	 * Cria uma nova inst�ncia de PagedCollectionModel com tamanho de p�gina
	 * default.
	 * 
	 * @param paginationSupport
	 *            objeto utilizado para buscas sob demanda
	 * @param searchObject
	 *            objeto com os par�metros da consulta.
	 */
	public PagedCollectionModel(PaginationSupport<E, S> paginationSupport,
			S searchObject) {
		this(DEFAULT_PAGE_SIZE, paginationSupport, searchObject);
	}

	/**
	 * Retorna o total de registros da consulta.
	 */
	@Override
	public int getRowCount() {
		return count;
	}
	/**
	 * Remove os dados internos. 
	 */
	public void clear(){
		internalData.clear();
	}

	/**
	 * <p>
	 * Retorna o objeto que corresponde ao �ndice atual. Determina o �ndice da
	 * p�gina atrav�s do �ndice do item atual, depois checa se existem itens
	 * nessa p�gina. Se n�o houver, carrega os items atrav�s da implementa��o
	 * fornecida de PaginationSupport.
	 * </p>
	 * 
	 * @throws FacesException
	 *             Lan�ada se o DataResolver retornar 'null' em um fetch.
	 * 
	 */
	@Override
	public Object getRowData() {
		if (isRowAvailable()) {
			int pageIndex = getPageIndex(index);
			List<E> items = (List<E>) internalData.get(pageIndex);
			if (items == null) {
				// checa se � a primeira consulta para evitar o count duplicado
				if (!firstTime) {
					count();
				}
				firstTime = false;
				// busca os novos itens.
				try {
					items = fetch(pageIndex);
				} catch (Exception e) {
					
					throw new FacesException(
							"O DataResolver lan�ou uma exce��o no m�todo fetch, durante a requisi��o da p�gina: "
									+ pageIndex, e);
				}
				if (items == null) {
					
					throw new FacesException(
							"O DataResolver retornou 'null' para a p�gina de �ndice: "
									+ pageIndex);
				}
				if (!keepCache) {
					clear();
				}
				
				internalData.put(pageIndex, items);
			}
			return items.get(index - (pageSize * pageIndex));
		}
		return null;
	}

	/**
	 * Solicita a informa��o da quantidade de registros ao DataResolver.
	 */
	protected void count() {
		try {
			this.count = paginationSupport.count(searchObject);
		} catch (Exception e) {
			
			throw new FacesException(
					"O DataResolver lan�ou uma exce��o durante a chamada ao m�todo 'count'.",
					e);
		}
		
	}

	/**
	 * Retorna os itens que correspondem a p�gina de �ndice igual a
	 * <code>pageIndex</code>.
	 * 
	 * @param pageIndex
	 *            �ndice da p�gina para carregar os itens
	 * @return
	 */
	protected List<E> fetch(int pageIndex) {
		int maxItems = this.getPageSize(pageIndex);
		int startIndex = pageSize * pageIndex;
	
		List<E> items = paginationSupport.search(searchObject, startIndex,
				maxItems);
		return items;
	}

	/**
	 * Retorna o �ndice da p�gina a que pertence o item de �ndice
	 * <code>itemIndex</code>.
	 * 
	 * @param itemIndex
	 *            �ndice do item. De <code>0</code> a <code>count - 1</code>.
	 * @return
	 */
	protected int getPageIndex(int itemIndex) {
		return (int) Math.floor((double) itemIndex / pageSize);
	}

	/**
	 * Retorna tamanho da p�gina de �ndice igual a <code>pageIndex</code>.
	 * 
	 * @param pageIndex
	 *            �ndice da p�gina para carregar os itens
	 * @return
	 */
	private int getPageSize(int pageIndex) {
		int min = count - (pageSize * pageIndex);
		min = min < pageSize ? min : pageSize;
		return min;
	}

	@Override
	public int getRowIndex() {
		return index;
	}

	/**
	 * Retorna <code>null</code> nesta implementa��o.
	 */
	@Override
	public Object getWrappedData() {
		// TODO lan�ar UnsupportedOperationException?
		/*
		 * Aqui n�o se deve disponibilizar os dados internos, visto que uma
		 * manipula��o externa pode prejudicar a l�gica da pagina��o.
		 */
		return null;
	}

	/**
	 * Retorna <code>true</code> se o <code>index</code> for menor que o
	 * <code>count</code> e maior ou igual que zero.
	 */
	@Override
	public boolean isRowAvailable() {
		return getRowIndex() < count && getRowIndex() >= 0;
	}

	/**
	 * Atribui o �ndice.
	 */
	@Override
	public void setRowIndex(int index) {
		this.index = index;
		DataModelListener[] listeners = getDataModelListeners();
		if (isRowAvailable() && listeners.length > 0 && getRowData() != null) {
			
			DataModelEvent event = new DataModelEvent(this, index, getRowData());
			for (int i = 0; i < listeners.length; i++) {
				DataModelListener dataModelListener = listeners[i];
				dataModelListener.rowSelected(event);
			}
		}
	}

	/**
	 * N�o faz nada nessa implementa��o.
	 */
	@Override
	public void setWrappedData(Object wrappedData) {
		// TODO lan�ar UnsupportedOperationException?
	}
}
