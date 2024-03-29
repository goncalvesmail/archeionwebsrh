package br.com.archeion.mbean.graficos;

import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;

import br.com.archeion.jsf.Util;
import br.com.archeion.mbean.ArcheionBean;
import br.com.archeion.modelo.caixa.Caixa;
import br.com.archeion.modelo.enderecocaixa.EnderecoCaixa;
import br.com.archeion.negocio.caixa.CaixaBO;
import br.com.archeion.negocio.enderecocaixa.EnderecoCaixaBO;

public class GraficoVagasMBean extends ArcheionBean {
	/**
	 * BO de endere�o de caixa
	 */
	private EnderecoCaixaBO enderecoCaixaBO = (EnderecoCaixaBO) Util.getSpringBean("enderecoCaixaBO");
	/**
	 * BO de caixa
	 */
	private CaixaBO caixaBO = (CaixaBO) Util.getSpringBean("caixaBO");
	
	/**
	 * Chama o grafico
	 * @return
	 */
	public String goToGrafico() {
		return "graficoVaga";
	}
	/**
	 * Chamado para montar o grafico
	 * @return
	 */
	public DefaultCategoryDataset getCategoryDataset() {
		
		DefaultCategoryDataset categoryDataSet;

		String series1 = "Livres";
		String series2 = "Ocupados";
		
		List<EnderecoCaixa> listaEnderecoCaixa = enderecoCaixaBO.findAll();		
		categoryDataSet = new DefaultCategoryDataset();
		
		for(EnderecoCaixa endereco:listaEnderecoCaixa) {
			List<Caixa> ocupados = caixaBO.findVaoOcupados(endereco.getVao());
			long livre = endereco.getVaoFinal() - endereco.getVaoInicial() - ocupados.size();
			
			categoryDataSet.addValue(livre, series1, endereco.getVao());
			categoryDataSet.addValue(ocupados.size(), series2, endereco.getVao());
		}
		
		return categoryDataSet;
	}
	
}
