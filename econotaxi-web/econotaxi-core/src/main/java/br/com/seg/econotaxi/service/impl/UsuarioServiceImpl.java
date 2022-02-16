package br.com.seg.econotaxi.service.impl;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioPerfil;
import br.com.seg.econotaxi.repository.CorridaRepository;
import br.com.seg.econotaxi.repository.ParametrosRepository;
import br.com.seg.econotaxi.repository.UsuarioPerfilRepository;
import br.com.seg.econotaxi.repository.UsuarioRepository;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.util.EmailUtil;
import br.com.seg.econotaxi.util.HashUtil;
import br.com.seg.econotaxi.util.MaxiPagoUtil;
import br.com.seg.econotaxi.vo.CodigoSmsVo;
import br.com.seg.econotaxi.vo.EmailVO;

@Service("usuarioService")
public class UsuarioServiceImpl extends AbstractService implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;
    @Autowired
    private CorridaRepository corridaRepository;
    @Autowired
    private ParametrosRepository parametrosRepository;

    @Autowired
    private MaxiPagoUtil maxiPagoUtil;

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario recuperarPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

	@Override
	public void excluirUsuario(Usuario usuario) {
		Usuario user = usuarioRepository.findOne(usuario.getId());
		user.setIndicadorAtivo(2);
		usuarioRepository.save(user);
	}
	
	@Override
	public void ativarUsuario(Usuario usuario) {
		Usuario user = usuarioRepository.findOne(usuario.getId());
		user.setIndicadorAtivo(1);
		usuarioRepository.save(user);
	}

	@Override
	public void cadastrarUsuario(Usuario usuario) {
		
		if (usuario.getId() == null) {
			
			Parametro parametro = parametrosRepository.recuperarParametro();
			
			String hash = HashUtil.stringHexa(HashUtil.gerarHash(
					usuario.getLogin() + new SimpleDateFormat("ddMMYYYYHHmmss").format(
							usuario.getDataCadastro()), "MD5"));
			usuario.setHashTrocaSenha(hash);
			usuarioRepository.save(usuario);
			EmailVO email = new EmailVO();
			email.setAssunto(parametro.getNomeAplicativo() + " - Solicitação de Troca de Senha");
			StringBuilder mensagem = new StringBuilder();
			mensagem.append("Este é um e-mail confirmando seu cadastro na plataforma, realizado em ");
			mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(new Date()) + ".");
			mensagem.append("<br /> ");
			mensagem.append("Para configurar uma senha, clique no link abaixo (ou copie e cole no seu browser):<br /><br /> ");
			mensagem.append("<a href=\"" + parametro.getHost() + "/econotaxi-web/public/trocarSenha.jsf?hashTrocaSenha=" + usuario.getHashTrocaSenha() + "\">");
			mensagem.append(parametro.getHost() + "/econotaxi-web/public/trocarSenha.jsf?hashTrocaSenha=" + usuario.getHashTrocaSenha() + "</a>");
			mensagem.append("<br /><br /> ");
			mensagem.append("Atenciosamente,<br /> ");
			mensagem.append("<strong>Equipe " + parametro.getNomeAplicativo() + "</strong>");
			email.setMensagem(mensagem.toString());
			email.setNomeRemetente(parametro.getUsuarioEmail());
			email.setDestinatarios(usuario.getLogin());
			
			email.setServidorSMTP(parametro.getUrlSmtp());
			email.setUsuarioEmail(parametro.getUsuarioEmail());
			email.setSenhaEmail(parametro.getSenhaEmail());
			email.setPortaServidorSMTP(parametro.getPortaSmtp());
			email.setTitulo(parametro.getNomeAplicativo() + " - Notifica!");
			
			try {
				EmailUtil.sendEmail(email);
			} catch (UnsupportedEncodingException | MessagingException e) {
				e.printStackTrace();
			}
		} else {
			usuarioRepository.save(usuario);
		}
	}

	@Override
	public boolean verificaExistenciaUsuario(Usuario usuario) {
		return usuarioRepository.verificaExistenciaUsuario(usuario);
	}

	@Override
	public Usuario recuperarUsuarioPorID(Long id) {
		return usuarioRepository.findOne(id);
	}

	@Override
	public Long pesquisarCountUsuarioPorFiltro(Usuario filtro) {
		return usuarioRepository.pesquisarCountUsuarioPorFiltro(filtro);
	}

	@Override
	public List<Usuario> pesquisarUsuarioPorFiltro(Usuario filtro, int first, int pageSize) {
		return usuarioRepository.pesquisarUsuarioPorFiltro(filtro, first, pageSize);
	}

	@Override
	public Integer recuperarQtdUsuarioAppPorStatus(Integer status) {
		return usuarioRepository.recuperarQtdUsuarioAppPorStatus(status);
	}

	@Override
	public Integer recuperarQtdUsuarioAppComPagamento() {
		return usuarioRepository.recuperarQtdUsuarioAppComPagamento();
	}

	@Override
	public Usuario enviarEmailTrocaSenha(String emailTrocaSenha) {
		
		Usuario usuario = usuarioRepository.findByLogin(emailTrocaSenha);
		if (usuario != null && usuario.getId() != null) {
			
			Parametro parametro = parametrosRepository.recuperarParametro();
			
			usuario.setDataAlteracao(new Date());
			
			String hash = HashUtil.stringHexa(HashUtil.gerarHash(
					usuario.getLogin() + new SimpleDateFormat("ddMMYYYYHHmmss").format(
							usuario.getDataAlteracao()), "MD5"));
			usuario.setHashTrocaSenha(hash);
			cadastrarUsuario(usuario);
			EmailVO email = new EmailVO();
			email.setAssunto(parametro.getNomeAplicativo() + " - Solicitação de Troca de Senha");
			StringBuilder mensagem = new StringBuilder();
			mensagem.append("Este é um e-mail para realização de troca de senha, solicitada em ");
			mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(new Date()) + ".");
			mensagem.append("<br /> ");
			mensagem.append("Para realizar a troca da senha, clique no link abaixo (ou copie e cole no seu browser):<br /><br /> ");
			mensagem.append("<a href=\"" + parametro.getHost() + "/econotaxi-web/public/trocarSenha.jsf?hashTrocaSenha=" + usuario.getHashTrocaSenha() + "\">");
			mensagem.append(parametro.getHost() + "/econotaxi-web/public/trocarSenha.jsf?hashTrocaSenha=" + usuario.getHashTrocaSenha() + "</a>");
			mensagem.append("<br /><br /> ");
			mensagem.append("Atenciosamente,<br /> ");
			mensagem.append("<strong>Equipe " + parametro.getNomeAplicativo() + "</strong>");
			email.setMensagem(mensagem.toString());
			email.setNomeRemetente(parametro.getUsuarioEmail());
			email.setDestinatarios(emailTrocaSenha);
			
			email.setServidorSMTP(parametro.getUrlSmtp());
			email.setUsuarioEmail(parametro.getUsuarioEmail());
			email.setSenhaEmail(parametro.getSenhaEmail());
			email.setPortaServidorSMTP(parametro.getPortaSmtp());
			email.setTitulo(parametro.getNomeAplicativo() + " - Notifica!");
			
			try {
				EmailUtil.sendEmail(email);
			} catch (UnsupportedEncodingException | MessagingException e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}

	@Override
	public Usuario recuperarPorHashTrocaSenha(String hashTrocaSenha) {
		Usuario usuario = usuarioRepository.findByHashTrocaSenha(hashTrocaSenha);
		
		if (usuario == null) {
			throw new NegocioException("Código de recuperação de senha (hash) inválido.");
		}
		
		return usuario;
	}

	@Override
	public void trocarSenha(String login, String novaSenha, String hashTrocaSenha) {
		
		Usuario usuario = usuarioRepository.recuperarUsuarioPorHashTrocaSenha(hashTrocaSenha);
		trocarSenha(usuario, novaSenha);
	}

	@Override
	public void trocarSenhaCliente(Usuario usuario, String novaSenha) {
		trocarSenha(usuario, novaSenha);
	}

	private void trocarSenha(Usuario usuario, String novaSenha) {
		if (usuario != null && usuario.getId() != null) {
			String hashSenha = HashUtil.stringHexa(HashUtil.gerarHash(
				novaSenha.concat(usuario.getLogin())
					.concat(new SimpleDateFormat("ddMMyyyy").format(
						usuario.getDataCadastro())), "SHA-256"));
			usuario.setHashSenha(hashSenha);
			usuario.setDataAlteracao(new Date());
			usuario.setHashTrocaSenha(null);
			cadastrarUsuario(usuario);
		}
	}

	@Override
	public void bloquearPassageiro(Usuario usuario) {
		
		usuario.setDataAlteracao(new Date());
		cadastrarUsuario(usuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void salvarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	public void atualizarDados(Usuario usuario) {
		Usuario usuarioDB = usuarioRepository.findOne(usuario.getId());

		if (usuarioDB != null) {

			usuarioDB.setNome(usuario.getNome());
			usuarioDB.setLogin(usuario.getLogin());
			usuarioDB.setIndicadorCadeirante(usuario.getIndicadorCadeirante());
			usuarioDB.setSexo(usuario.getSexo());
			usuarioDB.setDtNascimento(usuario.getDtNascimento());
			if (usuario.getImagem() != null && !usuario.getImagem().isEmpty()) {
				usuarioDB.setImagem(usuario.getImagem());
			}
			usuarioDB.setCnpj(usuario.getCnpj());

			usuarioRepository.save(usuarioDB);

		} else {
			throw new NegocioException("Usuário não econtrado");
		}
	}

	@Override
	public CodigoSmsVo gerarCodigoSms(CodigoSmsVo codigoSmsVo) {
    	System.out.println(codigoSmsVo.getCelular());
    	
    	if (usuarioRepository.findByLogin(codigoSmsVo.getEmail()) != null) {
    		throw new NegocioException("Este e-mail já está cadastrado");
    	} else {
    		List<Usuario> users = usuarioRepository.findByCelular(codigoSmsVo.getCelular());
    		if (users != null && !users.isEmpty()) {
    			throw new NegocioException("Este celular já está cadastrado");
    		}
    	}

		StringBuilder codigoSms = new StringBuilder();

		int randomInt;
		String randomString;

		Random randomGenerator = new Random();
		for (int idx = 1; idx <= 4; ++idx){
			randomInt = randomGenerator.nextInt(9);
			randomString = String.valueOf(randomInt);
			codigoSms.append(randomString);
		}

		codigoSmsVo.setCodigo(codigoSms.toString());
		System.out.println("codigo SMS !!!!!!");
		System.out.println(codigoSms.toString());
		return codigoSmsVo;
	}
	
	@Override
	public void salvarCliente(Usuario usuario) {
		usuario.setDataCadastro(new Date());
		usuario.setDataAlteracao(new Date());
		usuario.setTipo(TipoUsuarioEnum.CLIENTE.getCodigo());
		getLogger().info("Data de cadastro: " + usuario.getDataCadastro());

		String senhaCadastro = usuario.getSenha();
		usuario.setSenha(null);

		salvarUsuario(usuario);
		
		Usuario usuarioDb = usuarioRepository.findByLogin(usuario.getLogin());
		getLogger().info("Data de cadastro DB: " + usuarioDb.getDataCadastro());
		
		String hashSenha = HashUtil.stringHexa(HashUtil.gerarHash(
				senhaCadastro.concat(usuarioDb.getLogin())
				.concat(new SimpleDateFormat("ddMMyyyy").format(usuario.getDataCadastro())), "SHA-256"));
		
		usuarioDb.setHashSenha(hashSenha);
		
		salvarUsuario(usuarioDb);
	}

	@Override
	public String recuperarNomePorId(Long idUsuario) {
		return usuarioRepository.findNomeById(idUsuario);
	}

	@Override
	public List<Usuario> recuperarUsuariosAdministrativo() {
		return usuarioRepository.recuperarUsuariosAdministrativo();
	}

	@Override
	public String adicionarMaxipago(Long id) {
		Usuario usuario = usuarioRepository.findOne(id);

		if (usuario.getIdMaxipago() == null || usuario.getIdMaxipago().isEmpty()) {
			String idMaxipago = maxiPagoUtil.adicionarUsuario(usuario);

			usuario.setIdMaxipago(idMaxipago);
			usuarioRepository.save(usuario);
		}

		return usuario.getIdMaxipago();
	}

	@Override
	public String recuperarIdMaxipago(Long idUsuario) {
		return usuarioRepository.findIdMaxipagoById(idUsuario);
	}

	@Override
	public List<Usuario> recuperarUsuariosSemNotificacao(Notificacao notificacao) {
		return usuarioRepository.recuperarUsuariosSemNotificacao(notificacao);
	}

	@Override
	public List<Usuario> recuperarUsuariosSemNovidade(Novidade novidade) {
		return usuarioRepository.recuperarUsuariosSemNovidade(novidade);
	}

	@Override
	public int quantidadeUsuariosSemNotificacao(Notificacao notificacao) {
		return usuarioRepository.quantidadeUsuariosSemNotificacao(notificacao);
	}

	@Override
	public int quantidadeUsuariosSemNovidade(Novidade novidade) {
		return usuarioRepository.quantidadeUsuariosSemNovidade(novidade);
	}

	@Override
	public List<Usuario> recuperarUsuariosPorTipoCidade(Integer codigo, Cidade cidade) {
		return usuarioRepository.recuperarUsuariosPorTipoCidade(codigo, cidade);
	}

	@Override
	public void excluirPassageiro(Usuario passageiro) {
		
		List<Corrida> corridas = corridaRepository.recuperarCorridasPorUsuario(passageiro, 
				TipoCorridaEnum.CORRIDA.getCodigo());
		List<Corrida> entregas = corridaRepository.recuperarCorridasPorUsuario(passageiro, 
				TipoCorridaEnum.ENTREGA.getCodigo());
		if (corridas != null && !corridas.isEmpty()) {
			for (Corrida corrida : corridas) {
				corridaRepository.delete(corrida);
			}
		}
		if (entregas != null && !entregas.isEmpty()) {
			for (Corrida corrida : entregas) {
				corridaRepository.delete(corrida);
			}
		}
		usuarioRepository.delete(passageiro);
	}

	@Override
	public Usuario consultarUsuarioPorCelular(String celular) {
		return usuarioRepository.consultarUsuarioPorCelular(celular);
	}

	@Override
	public List<Usuario> recuperarPermissionariosUsuario(Motorista motorista) {
		return usuarioRepository.recuperarPermissionariosUsuario(motorista);
	}

	@Override
	public List<Usuario> recuperarUsuariosPorTipoCidadeRadio(Integer codigo, Cidade cidade, Integer tipoRadio) {
		return usuarioRepository.recuperarUsuariosPorTipoCidadeRadio(codigo, cidade, tipoRadio);
	}
	
	@Override
	public List<Usuario> recuperarPassageiros() {
		return usuarioRepository.recuperarPassageiros();
	}

	@Override
	public void excluirPerfis(List<UsuarioPerfil> listaGrupoUsuario) {
		if (listaGrupoUsuario != null && !listaGrupoUsuario.isEmpty()) {
			usuarioPerfilRepository.delete(listaGrupoUsuario);
		}
	}
	
	@Override
	public Integer recuperarTipoPorLogin(String login) {
		return usuarioRepository.findTipoByLogin(login);
	}

	@Override
	public boolean verificaExistenciaUsuarioCelular(Usuario usuario) {
		return usuarioRepository.verificaExistenciaUsuarioCelular(usuario);
	}
	
}