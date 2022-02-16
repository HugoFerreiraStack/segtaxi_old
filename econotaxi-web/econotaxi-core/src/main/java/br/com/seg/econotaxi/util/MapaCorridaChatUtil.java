/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.vo.CarrosVO;
import br.com.seg.econotaxi.vo.MensagemCorridaVO;

/**
 * @author bruno
 *
 */
public class MapaCorridaChatUtil {

	private static MapaCorridaChatUtil instance = new MapaCorridaChatUtil();
	
	private Map<Long, List<MensagemCorridaVO>> mapaCorridaChat = new HashMap<Long, List<MensagemCorridaVO>>();
	private Map<Long, Usuario> mapaCorridaChatNova = new HashMap<Long, Usuario>();
	
	static {
		instance.setMapaCorridaChat(new HashMap<Long, List<MensagemCorridaVO>>());
		instance.setMapaCorridaChatNova(new HashMap<Long, Usuario>());
	}
	
	public static void adicionarMensagem(MensagemCorridaVO mensagem) {
		
		if (MapaCorridaChatUtil.getInstance().getMapaCorridaChat().get(mensagem.getIdCorrida()) != null) {
			MapaCorridaChatUtil.getInstance().getMapaCorridaChat().get(mensagem.getIdCorrida()).add(mensagem);
		} else {
			List<MensagemCorridaVO> mensagens = new ArrayList<MensagemCorridaVO>();
			mensagens.add(mensagem);
			MapaCorridaChatUtil.getInstance().getMapaCorridaChat().put(mensagem.getIdCorrida(), mensagens);
		}
	}
	
	public static List<MensagemCorridaVO> recuperarMensagens(Long idCorrida) {
		
		return MapaCorridaChatUtil.getInstance().getMapaCorridaChat().get(idCorrida);
	}
	
	public static void apagarMensagens(Long idCorrida) {
		
		if (MapaCorridaChatUtil.getInstance().getMapaCorridaChat().get(idCorrida) != null) {
			MapaCorridaChatUtil.getInstance().getMapaCorridaChat().remove(idCorrida);
		}
	}
	
	public static void definirMensagemNova(Long idUsuario) {
		
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		MapaCorridaChatUtil.getInstance().getMapaCorridaChatNova().put(idUsuario, usuario);
	}
	
	public static void definirMensagemNova(Long idUsuario, Motorista motorista) {
		
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		CarrosVO carro = MapaCarroUtil.recuperarCarroMotorista(motorista.getCidade(), motorista.getId());
		if (carro != null) {
			usuario.setNomeMotorista(carro.getNomeMotorista());
			usuario.setPlacaMotorista(carro.getPlaca());
			usuario.setUnidadeMotorista(carro.getUnidade());
			usuario.setMensagemEnviada(motorista.getMensagemEnviada());
		}
		MapaCorridaChatUtil.getInstance().getMapaCorridaChatNova().put(idUsuario, usuario);
	}
	
	public static void retirarMensagemNova(Long idUsuario) {
		
		if (MapaCorridaChatUtil.getInstance().getMapaCorridaChatNova().get(idUsuario) != null) {
			MapaCorridaChatUtil.getInstance().getMapaCorridaChatNova().remove(idUsuario);
		}
	}
	
	public static Usuario verificaMensagemNova(Long idUsuario) {
		
		return MapaCorridaChatUtil.getInstance().getMapaCorridaChatNova().get(idUsuario);
	}
	
	/* Métodos Get/Set */
	public Map<Long, List<MensagemCorridaVO>> getMapaCorridaChat() {
		return mapaCorridaChat;
	}
	public void setMapaCorridaChat(Map<Long, List<MensagemCorridaVO>> mapaCorridaChat) {
		this.mapaCorridaChat = mapaCorridaChat;
	}
	public Map<Long, Usuario> getMapaCorridaChatNova() {
		return mapaCorridaChatNova;
	}
	public void setMapaCorridaChatNova(Map<Long, Usuario> mapaCorridaChatNova) {
		this.mapaCorridaChatNova = mapaCorridaChatNova;
	}
	public static MapaCorridaChatUtil getInstance() {
		return instance;
	}

}