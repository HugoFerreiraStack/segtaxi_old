package br.com.seg.econotaxi.sec;

import java.io.Serializable;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Classe responsável por realizar a autenticação via LDAP.
 *
 * Criado em 28 de jun de 2017
 * @author Bruno rocha
 */
public class LDAPAutenticacao implements Serializable {

	// Constantes
	private static final long serialVersionUID = -7550752139257041685L;

	/**
	 * Não é possível instanciar esta classe. Utilize os métodos <code>static</code> públicos.
	 */
	private LDAPAutenticacao() {}
	
	/**
	 * Método responsável pela autenticação LDAP via JNDI de usuários.
	 *
	 * @param usuario o login do usuário na rede
	 * @param senha a senha do usuário na rede
	 * @param urlLDAP a url do servidor LDAP
	 * @return boolean <code>true</code> caso a autenticação seja feita com sucesso, 
	 * <code>false</code> caso contrário.
	 */
	public static boolean verificaAcessoUsuario(String usuario, String senha, String urlLDAP) {
		
		boolean verifica = Boolean.FALSE;
		Hashtable<Object, Object> env = new Hashtable<Object, Object>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put("com.sun.jndi.ldap.read.timeout", "7000");
		env.put(Context.PROVIDER_URL, urlLDAP);
		env.put(Context.SECURITY_PRINCIPAL, usuario);
		env.put(Context.SECURITY_CREDENTIALS, senha);
		try {
			
			// Criando o contexto inicial.
			DirContext ctx = new InitialDirContext(env);
			verifica = Boolean.TRUE;
			
			// Fechando o contexto.
			ctx.close();
			
		} catch (NamingException e) {
			e.printStackTrace();
			verifica = Boolean.FALSE;
		}
		return verifica;
	}
	
}