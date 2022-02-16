/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.model.UsuarioNovidade;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;
import br.com.seg.econotaxi.service.UsuarioNovidadeService;

/**
 * @author bruno
 *
 */
public class MapaMensagensUtil {

	private static MapaMensagensUtil instance = new MapaMensagensUtil();
	private List<UsuarioNotificacao> notificacoes = 
			new ArrayList<UsuarioNotificacao>();
	private List<UsuarioNovidade> novidades = 
			new ArrayList<UsuarioNovidade>();
	
	static {
		UsuarioNotificacaoService usuarioNotificacaoService = (UsuarioNotificacaoService) SpringContextUtil.getBean("usuarioNotificacaoService");
		UsuarioNovidadeService usuarioNovidadeService = (UsuarioNovidadeService) SpringContextUtil.getBean("usuarioNovidadeService");
		instance.setNotificacoes(usuarioNotificacaoService.recuperarNotificacoesNaoVistas());
		instance.setNovidades(usuarioNovidadeService.recuperarNovidadesNaoVistas());
		
		if (instance.getNotificacoes() == null) {
			instance.setNotificacoes(new ArrayList<UsuarioNotificacao>());
		} else {
			System.out.println("Qtd notificacoes: " + instance.getNotificacoes().size());
		}
		if (instance.getNovidades() == null) {
			instance.setNovidades(new ArrayList<UsuarioNovidade>());
		} else {
			System.out.println("Qtd novidades: " + instance.getNovidades().size());
		}
		System.out.println("MapaMensagensUtil static init");
	}
	
	public static void start() {
		System.out.println("MapaMensagensUtil start");
	}
	
	public static void adicionarNotificacao(UsuarioNotificacao usuarioNotificacao) {
		instance.getNotificacoes().add(usuarioNotificacao);
	}
	
	public static void adicionarNovidade(UsuarioNovidade usuarioNovidade) {
		instance.getNovidades().add(usuarioNovidade);
	}
	
	public static void removerNotificacao(UsuarioNotificacao usuarioNotificacao) {
		instance.getNotificacoes().remove(usuarioNotificacao);
	}
	
	public static void removerNovidade(UsuarioNovidade usuarioNovidade) {
		instance.getNovidades().remove(usuarioNovidade);
	}
	
	public static List<UsuarioNotificacao> recuperarNotificacoesUsuario(Usuario usuario) {
		
		List<UsuarioNotificacao> notificacoes = null;
		if (instance.getNotificacoes() != null && !instance.getNotificacoes().isEmpty()) {
			List<UsuarioNotificacao> queryResult = instance.getNotificacoes().stream()
					.filter(un -> un != null)
					.filter(un -> un.getIdUsuario() != null)
					.filter(un -> un.getIdUsuario().equals(usuario.getId()))
					.collect(Collectors.toList());
			if (queryResult != null && !queryResult.isEmpty()) {
				notificacoes = new ArrayList<UsuarioNotificacao>();
				notificacoes = new ArrayList<UsuarioNotificacao>();
				for (UsuarioNotificacao usuarioNotificacao : queryResult) {
					notificacoes.add(usuarioNotificacao);
				}
			}
		}
		return notificacoes;
	}
	
	public static List<UsuarioNovidade> recuperarNovidadesUsuario(Usuario usuario) {
		
		List<UsuarioNovidade> novidades = null;
		if (instance.getNovidades() != null && !instance.getNovidades().isEmpty()) {
			List<UsuarioNovidade> queryResult = instance.getNovidades().stream()
					.filter(un -> un != null)
					.filter(un -> un.getId() != null)
					.filter(un -> un.getId().getIdUsuario() != null)
					.filter(un -> un.getId().getIdUsuario().equals(usuario.getId()))
					.collect(Collectors.toList());
			if (queryResult != null && !queryResult.isEmpty()) {
				novidades = new ArrayList<UsuarioNovidade>();
				for (UsuarioNovidade usuarioNovidade : queryResult) {
					novidades.add(usuarioNovidade);
				}
			}
		}
		return novidades;
	}

	/* Métodos Get/Set */
	public List<UsuarioNotificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<UsuarioNotificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public List<UsuarioNovidade> getNovidades() {
		return novidades;
	}

	public void setNovidades(List<UsuarioNovidade> novidades) {
		this.novidades = novidades;
	}

	public static MapaMensagensUtil getInstance() {
		return instance;
	}
	
}
