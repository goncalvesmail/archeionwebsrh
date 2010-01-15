package br.com.archeion.negocio.interceptors;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.annotation.Secured;
import org.acegisecurity.context.SecurityContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * Inserir o log de seguran�a na aplica��o
 * 
 */
public class SecurityLogInterceptor implements MethodInterceptor {

	/**
	 * Log.
	 */
	private static final Logger LOG = Logger.getLogger(SecurityLogInterceptor.class);

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 * 
	 * @param method M�todo
	 * @return O valor
	 * @throws Throwable Algum erro.
	 */
	public Object invoke(final MethodInvocation method) throws Throwable {
		// Tenta gravar o log.
		Secured secured = method.getMethod().getAnnotation(Secured.class);
		StringBuffer str = new StringBuffer();

		if (secured != null) {
			String[] funcionalidades = secured.value();
			for (String string : funcionalidades) {
				str.append(string + ";");
			}
		}
		try {
			LOG.info("Usu�rio acessando funcionalidade: " + str);
			LOG.info("Permissoes do usu�rio: " + permissoesDoUsuario());
			return method.proceed();
		} catch (Throwable ex) {
			LOG.info("Erro ao acessar a funcionalidade: \n" + str, ex);
			throw ex;
		}
	}

	/**
	 * @return Conjunto de permiss�es do usu�rio.
	 */
	private String permissoesDoUsuario() {
		String perm = "[";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			GrantedAuthority[] autorizacoes = auth.getAuthorities();
			for (GrantedAuthority autorizacao : autorizacoes) {
				perm += autorizacao.getAuthority() + ",";
			}
			return perm.substring(0, perm.length() - 1) + "]";
		}
		return "N/A";
	}

	/**
	 * Verifica se o usu�rio possui permiss�o.
	 * 
	 * @param autorizacaoNecessaria permiss�o.
	 * @return True caso possua a permiss�o.
	 */
	public boolean usuarioAutorizado(final String autorizacaoNecessaria) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			GrantedAuthority[] autorizacoes = auth.getAuthorities();
			for (GrantedAuthority autorizacao : autorizacoes) {
				if (autorizacao.getAuthority().equals(autorizacaoNecessaria)) {
					return true;
				}
			}
		}
		return false;
	}

}
