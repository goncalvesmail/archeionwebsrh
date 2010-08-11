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
 * Classe responsável por realizar a paginação para componentes que usam
 * implementações de <code>javax.faces.model.DataModel</code>.
 * </p>
 * 
 * <p>
 * Como esta classe é independente de componentes de visualização, ela pode ser
 * usada com qualquer conjunto de componentes, como Tomahawk, Trinidad ou Rich
 * Faces.
 * </p>
 * 
 * <p>
 * Essa implementação pode manter as páginas em cache ou descartá-las quando
 * novas páginas forem requisitadas. Para controlar esse comportamento, use o
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
	 * Tamanho padrão da quantidade de registros de cada página
	 */
	private static final int DEFAULT_PAGE_SIZE = 25;
	/**
	 * Map com os dados de cada página.
	 */
	protected Map<Integer, List<E>> internalData = new HashMap<Integer, List<E>>();
	/**
	 * Quantidade total de registros retornados pela consulta.
	 */
	protected int count;
	/**
	 * Total de registros por página.
	 */
	protected int pageSize;
	/**
	 * Índice que representa o elemento atual.
	 */
	protected int index;
	/**
	 * Objeto que é capaz de realizar a consulta sob demanda.
	 */
	protected PaginationSupport<E, S> paginationSupport;
	/**
	 * Objeto com os parâmetros da busca.
	 */
	protected S searchObject;

	/**
	 * Atributo para checar se é a primeira consulta para evitar que o count
	 * seja calculado 2 vezes
	 */
	private boolean firstTime = true;
	/**
	 * Mantém os dados em cache quando se navega em páginas.
	 */
	private boolean keepCache;

	public boolean isKeepCache() {
		return keepCache;
	}

	public void setKeepCache(boolean keepCache) {
		this.keepCache = keepCache;
	}

	/**
	 * Cria uma nova instância de PagedCollectionModel.
	 * 
	 * @param pageSize
	 *            tamanho da página. Indica quantos registros serão exibidos por
	 *            página.
	 * @param paginationSupport
	 *            objeto utilizado para buscas sob demanda
	 * @param searchObject
	 *            objeto com os parâmetros da consulta.
	 * @throws IllegalArgumentException
	 *             Lançada se algum dos argumentos forem <code>null</code>.
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
					"searchObject não pode ser nulo.");
		}
		if (paginationSupport == null) {
			throw new IllegalArgumentException(
					"paginationSupport não pode ser nulo.");
		}
		
		this.paginationSupport = paginationSupport;
		this.searchObject = searchObject;
		this.pageSize = pageSize;
		count();
	}

	/**
	 * Cria uma nova instância de PagedCollectionModel com tamanho de página
	 * default.
	 * 
	 * @param paginationSupport
	 *            objeto utilizado para buscas sob demanda
	 * @param searchObject
	 *            objeto com os parâmetros da consulta.
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
	 * Retorna o objeto que corresponde ao índice atual. Determina o índice da
	 * página através do índice do item atual, depois checa se existem itens
	 * nessa página. Se não houver, carrega os items através da implementação
	 * fornecida de PaginationSupport.
	 * </p>
	 * 
	 * @throws FacesException
	 *             Lançada se o DataResolver retornar 'null' em um fetch.
	 * 
	 */
	@Override
	public Object getRowData() {
		if (isRowAvailable()) {
			int pageIndex = getPageIndex(index);
			List<E> items = (List<E>) internalData.get(pageIndex);
			if (items == null) {
				// checa se é a primeira consulta para evitar o count duplicado
				if (!firstTime) {
					count();
				}
				firstTime = false;
				// busca os novos itens.
				try {
					items = fetch(pageIndex);
				} catch (Exception e) {
					
					throw new FacesException(
							"O DataResolver lançou uma exceção no método fetch, durante a requisição da página: "
									+ pageIndex, e);
				}
				if (items == null) {
					
					throw new FacesException(
							"O DataResolver retornou 'null' para a página de índice: "
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
	 * Solicita a informação da quantidade de registros ao DataResolver.
	 */
	protected void count() {
		try {
			this.count = paginationSupport.count(searchObject);
		} catch (Exception e) {
			
			throw new FacesException(
					"O DataResolver lançou uma exceção durante a chamada ao método 'count'.",
					e);
		}
		
	}

	/**
	 * Retorna os itens que correspondem a página de índice igual a
	 * <code>pageIndex</code>.
	 * 
	 * @param pageIndex
	 *            índice da página para carregar os itens
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
	 * Retorna o índice da página a que pertence o item de índice
	 * <code>itemIndex</code>.
	 * 
	 * @param itemIndex
	 *            índice do item. De <code>0</code> a <code>count - 1</code>.
	 * @return
	 */
	protected int getPageIndex(int itemIndex) {
		return (int) Math.floor((double) itemIndex / pageSize);
	}

	/**
	 * Retorna tamanho da página de índice igual a <code>pageIndex</code>.
	 * 
	 * @param pageIndex
	 *            índice da página para carregar os itens
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
	 * Retorna <code>null</code> nesta implementação.
	 */
	@Override
	public Object getWrappedData() {
		// TODO lançar UnsupportedOperationException?
		/*
		 * Aqui não se deve disponibilizar os dados internos, visto que uma
		 * manipulação externa pode prejudicar a lógica da paginação.
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
	 * Atribui o índice.
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
	 * Não faz nada nessa implementação.
	 */
	@Override
	public void setWrappedData(Object wrappedData) {
		// TODO lançar UnsupportedOperationException?
	}
}
