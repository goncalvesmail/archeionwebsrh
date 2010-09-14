package br.com.archeion.negocio.pasta;

import java.util.Date;

import br.com.archeion.exception.BusinessException;
import br.com.archeion.exception.CadastroDuplicadoException;
import br.com.archeion.modelo.pasta.Emprestimo;
import br.com.archeion.persistencia.pasta.EmprestimoDAO;

public class EmprestimoBOImpl implements EmprestimoBO {
	private EmprestimoDAO emprestimoDAO;
	
	public Emprestimo persist(Emprestimo emprestimo) throws CadastroDuplicadoException, BusinessException {
		validar(emprestimo);
		return emprestimoDAO.persist(emprestimo);
	}
	
	public void validar(Emprestimo emprestimo) throws BusinessException  {
		if ( emprestimo.getDataEmprestimo().after(new Date()) ) {
			throw new BusinessException("emprestimo.pasta.erro.dataSaida.invalida");
		}
		if ( emprestimo.getDataEmprestimo().after(emprestimo.getPrevisaoDevolucao()) ) {
			throw new BusinessException("emprestimo.pasta.erro.previsao.invalida");
			
		}
	}

	public EmprestimoDAO getEmprestimoDAO() {
		return emprestimoDAO;
	}

	public void setEmprestimoDAO(EmprestimoDAO emprestimoDAO) {
		this.emprestimoDAO = emprestimoDAO;
	}
}
