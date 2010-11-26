package util;

public enum ChavesPasta {
	CAIXETA(1,"Caixeta","NM_CAIXETA","string"),
	DATAREFERENCIA(2,"Data de Referência","DT_REFERENCIA","date"),
	
	EMPRESA(3,"Empresa","L.ID_LOCAL = P.ID_LOCAL AND L.ID_EMPRESA = E.ID_EMPRESA AND E.NM_EMPRESA","empresa","TB_LOCAL L,TB_EMPRESA E"),
	ITEMDOCUMENTAL(4,"Item Documental","P.ID_ITEM_DOCUMENTAL = IT.ID_ITEM_DOCUMENTAL AND IT.NM_ITEM_DOCUMENTAL","itemdocumental","TB_ITEM_DOCUMENTAL IT"),

	LIMITEDATA(5,"Limite Data","DT_DATA_LIMITE_INICIAL","date"),
	LIMITENOME(6,"Limite Nome","NM_NOME_LIMITE_INICIAL","string"),
	LIMITEVALOR(7,"Limite Valor","NU_NUMERO_LIMITE_INICIAL","number"),
	
	LOCAL(8,"Local","L.ID_LOCAL = P.ID_LOCAL AND L.NM_LOCAL","local","TB_LOCAL L"),
	
	OBSERVACAO(9,"Observação","TX_OBSERVACAO","string"),
	TITULOPASTA(10,"Título Pasta","NM_TITULO","string"),
	//VAO(11,"Vão","caixa.vao.vao"),
	//VAONUMERO(12,"Número Vão","caixa.numeroVao","number"),
	DESCRICAO(11,"Descrição","NM_DESCRICAO","string"),
	NUMEROPROTOCOLO(12,"Número de protocolo","NM_NUMERO_PROTOCOLO","string");
	
	ChavesPasta(int id, String label, String dataValue) {
		this.id = id;
		this.label = label;
		this.dataValue = dataValue;
		this.conversor = null;
	}
	ChavesPasta(int id, String label, String dataValue, String conversor) {
		this.id = id;
		this.label = label;
		this.dataValue = dataValue;
		this.conversor = conversor;
	}
	ChavesPasta(int id, String label, String dataValue, String conversor, String from) {
		this.id = id;
		this.label = label;
		this.dataValue = dataValue;
		this.conversor = conversor;
		this.from = from;
	}
	
	private int id;
	private String label;
	private String dataValue;
	private String conversor;
	private String from;
	
	public static String getById(int id){
		for(ChavesPasta c: ChavesPasta.values()){
			if(c.getId() == id){
				return c.getLabel();
			}
		}
		return null;
	}
	
	public static ChavesPasta get(int id){
		for(ChavesPasta c: ChavesPasta.values()){
			if(c.getId() == id){
				return c;
			}
		}
		return null;
	}
	
	public static String getDataBaseValue(int id) {
		for(ChavesPasta c: ChavesPasta.values()){
			if(c.getId() == id){
				return c.getDataValue();
			}
		}
		return null;
	}
	
	public static String getConversorValue(int id) {
		for(ChavesPasta c: ChavesPasta.values()){
			if(c.getId() == id){
				return c.getConversor();
			}
		}
		return null;
	}
	
	public static String getFromValue(int id) {
		for(ChavesPasta c: ChavesPasta.values()){
			if(c.getId() == id){
				return c.getFrom();
			}
		}
		return null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDataValue() {
		return dataValue;
	}
	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	public String getConversor() {
		return conversor;
	}

	public void setConversor(String conversor) {
		this.conversor = conversor;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}