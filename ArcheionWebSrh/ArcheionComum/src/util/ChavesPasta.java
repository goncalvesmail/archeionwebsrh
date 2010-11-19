package util;

public enum ChavesPasta {
	CAIXETA(1,"Caixeta","NM_CAIXETA","string"),
	DATAREFERENCIA(2,"Data de Refer�ncia","DT_REFERENCIA","date"),
	EMPRESA(3,"Empresa","u.local.empresa.nome","empresa"),
	ITEMDOCUMENTAL(4,"Item Documental","itemDocumental.nome","itemdocumental"),
	LIMITEDATA(5,"Limite Data","DT_DATA_LIMITE_INICIAL","date"),
	LIMITENOME(6,"Limite Nome","NM_NOME_LIMITE_INICIAL","string"),
	LIMITEVALOR(7,"Limite Valor","NU_NUMERO_LIMITE_INICIAL","number"),
	LOCAL(8,"Local","local.nome","local"),
	OBSERVACAO(9,"Observa��o","TX_OBSERVACAO","string"),
	TITULOPASTA(10,"T�tulo Pasta","NM_TITULO","string"),
	//VAO(11,"V�o","caixa.vao.vao"),
	//VAONUMERO(12,"N�mero V�o","caixa.numeroVao","number"),
	DESCRICAO(11,"Descri��o","NM_DESCRICAO","string"),
	NUMEROPROTOCOLO(12,"N�mero de protocolo","NM_NUMERO_PROTOCOLO","string");
	
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
	private int id;
	private String label;
	private String dataValue;
	private String conversor;
	
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
}