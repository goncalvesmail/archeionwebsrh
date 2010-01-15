package br.com.archeion.listeners;

import java.util.Locale;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import br.com.archeion.util.ListaUnidadesPersistencia;
import br.com.archeion.util.UnidadePersistencia;

/**
 * Inicializa o contexto do Spring populando o banco de dados.
 * */
public class ArcheionSpringContextListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(final ServletContextEvent evt) {

		// Define que a Unidade de Persist�ncia que utilizaremos ser� a
		// utilizada para acessar o Banco de Dados.
		UnidadePersistencia.unidadePersistencia = ListaUnidadesPersistencia.ARCHEION;

		// Define o Locale padr�o da aplica��o.
		Locale br = new Locale("pt", "BR");
		Locale.setDefault(br);

		super.contextInitialized(evt);

	}
}
