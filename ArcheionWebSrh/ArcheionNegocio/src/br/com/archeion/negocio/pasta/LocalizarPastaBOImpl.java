package br.com.archeion.negocio.pasta;

import java.util.List;

import br.com.archeion.modelo.pasta.ParametroConsulta;
import br.com.archeion.modelo.pasta.Pasta;
import br.com.archeion.persistencia.pasta.PastaDAO;

public class LocalizarPastaBOImpl implements LocalizarPastaBO {
	
	private PastaDAO pastaDAO;
	
	public List<Pasta> search(ParametroConsulta searchParameters, int startIndex, int pageSize) {		
		return pastaDAO.consultaEtiquetaPasta(searchParameters,startIndex,pageSize);
	}

	public int count(ParametroConsulta searchParameters) {
		return pastaDAO.consultaEtiquetaPastaSize(searchParameters).intValue();
	}

	public PastaDAO getPastaDAO() {
		return pastaDAO;
	}

	public void setPastaDAO(PastaDAO pastaDAO) {
		this.pastaDAO = pastaDAO;
	}

	
}
