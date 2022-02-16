/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Voucher;
import br.com.seg.econotaxi.repository.CentroCustoRepository;
import br.com.seg.econotaxi.repository.EmpresaConveniadaRepository;
import br.com.seg.econotaxi.repository.ParametrosRepository;
import br.com.seg.econotaxi.repository.VoucherRepository;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.util.EmailUtil;
import br.com.seg.econotaxi.vo.EmailVO;

/**
 * @author bruno
 *
 */
@Service("empresaConveniadaService")
public class EmpresaConveniadaServiceImpl implements EmpresaConveniadaService {

	@Autowired
	private EmpresaConveniadaRepository empresaConveniadaRepository;
	@Autowired
	private CentroCustoRepository centroCustoRepository;
	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private ParametrosRepository parametrosRepository;
	
	@Override
	public Long pesquisarCountEmpresaConveniadaPorFiltro(EmpresaConveniada filtro) {
		return empresaConveniadaRepository.pesquisarCountEmpresaConveniadaPorFiltro(filtro);
	}

	@Override
	public List<EmpresaConveniada> pesquisarEmpresaConveniadaPorFiltro(EmpresaConveniada filtro, int first,
			int pageSize) {
		return empresaConveniadaRepository.pesquisarEmpresaConveniadaPorFiltro(filtro, first, pageSize);
	}

	@Override
	public void salvarEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		
		if (empresaConveniada.getId() == null) {
			empresaConveniadaRepository.save(empresaConveniada);
			/*if (empresaConveniada.getEmail() != null 
					&& empresaConveniada.getEmail().isEmpty()) {
				
				Usuario usuario = new Usuario();
				usuario.setTipo(TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
				usuario.setDataCadastro(new Date());
				usuario.setDataAlteracao(new Date());
				usuario.setLogin(empresaConveniada.getEmail());
				usuario.setIndicadorEmpresaConveniada(1);
				usuario.setTipoTeleTaxi(2);
				usuario.setIdEmpresaConveniada(empresaConveniada.getId());
				
				if (!usuarioService.verificaExistenciaUsuario(usuario)) {
					usuarioService.cadastrarUsuario(usuario);
				}
				
			}*/
		} else {
			empresaConveniadaRepository.save(empresaConveniada);
		}
		
	}

	@Override
	public EmpresaConveniada consultarPorChave(Long id) {
		return empresaConveniadaRepository.findOne(id);
	}

	@Override
	public List<EmpresaConveniada> recuperarEmpresas() {
		return empresaConveniadaRepository.recuperarEmpresas();
	}

	@Override
	public boolean verificaExistenciaVoucher(String voucher) {
		return voucherRepository.verificaExistenciaVoucher(voucher);
	}

	@Override
	public void salvarVoucher(Voucher voucher) {
		voucherRepository.save(voucher);
	}

	@Override
	public void enviarEmailVoucher(Corrida corridaNova, EmpresaConveniada empresaConveniada) {
		
		try {
			Parametro parametro = parametrosRepository.recuperarParametro();
			
			EmailVO email = new EmailVO();
			email.setAssunto(parametro.getNomeAplicativo() + " - Voucher da Corrida");
			StringBuilder mensagem = new StringBuilder();
			mensagem.append("Este é um e-mail confirmando uma nova solicitação de corrida, realizada em ");
			mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(new Date()) + ".");
			mensagem.append("<br /> ");
			
			mensagem.append("<div style=\"width: 100%; height: auto; border: 1px dotted black;\"><div style=\"float: left; text-align: center; min-width: 270px; width: 40%; margin-top: 10px;\"><label style=\"font-weight: bold;\">" + corridaNova.getVoucher() + "</label><br />");
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			String qrCode = corridaNova.getId() 
					+ "###FROTA###" + corridaNova.getUsuario().getId() 
					+ "###FROTA###" + corridaNova.getVoucher();
			System.out.println(qrCode);
			BitMatrix byteMatrix = qrCodeWriter.encode(qrCode, BarcodeFormat.QR_CODE, 250,
					250, hintMap);
			BufferedImage image = toBufferedImage(byteMatrix);
			
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			String imagem = Base64.getEncoder().encodeToString(os.toByteArray());
			
			mensagem.append("<img title=\"QR Code\" src=\"data:image/png;base64," + imagem + "\" /></div>");
			
			mensagem.append("<div style=\"float: left; width: 60%\">");
			
			mensagem.append("<div style=\"padding-top: 20px;\"><label style=\"font-weight: bold; \">Passageiro:</label><br />");
			mensagem.append(corridaNova.getNomePassageiro() + "</div><br />");
			
			mensagem.append("<div><label style=\"font-weight: bold;\">Data da Solicitação:</label><br />");
			mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(corridaNova.getDataSolicitacao()) + "</div><br />");
			
			if (corridaNova.getDataAgendamento() != null && !corridaNova.getDataAgendamento().isEmpty()
					&& corridaNova.getHoraAgendamento() != null && !corridaNova.getHoraAgendamento().isEmpty()) {
				mensagem.append("<div><label style=\"font-weight: bold;\">Data do Agendamento:</label><br />");
				mensagem.append(corridaNova.getDataAgendamento() + " às " + corridaNova.getHoraAgendamento() + "</div><br />");
			}
			
			mensagem.append("<div><label style=\"font-weight: bold;\">Origem (Endereço):</label><br />");
			mensagem.append(corridaNova.getOrigemEndereco() + "</div><br />");
			
			if (corridaNova.getDestinoEndereco() != null && !corridaNova.getDestinoEndereco().isEmpty()) {
				mensagem.append("<div><label style=\"font-weight: bold;\">Destino (Endereço):</label><br />");
				mensagem.append(corridaNova.getDestinoEndereco() + "</div><br />");
			}
			
			if (empresaConveniada.getDescontoPorcentagem() != null) {
				mensagem.append("<div><label style=\"font-weight: bold;\">Desconto Concedido:</label><br />");
				mensagem.append(empresaConveniada.getDescontoPorcentagem() + "%</div><br />");
				
			}
			if (corridaNova.getValorFinal() != null) {
				mensagem.append("<div><label style=\"font-weight: bold;\">Valor Final Aproximado da Corrida com Desconto:</label><br />");
				mensagem.append("R$ " + corridaNova.getValorFinal() + "</div><br />");
			}
			
			mensagem.append("</div></div>");
			
			mensagem.append("<br /><br /> ");
			mensagem.append("<div style=\"clear: both;\">Atenciosamente,<br /> ");
			mensagem.append("<strong>Equipe " + parametro.getNomeAplicativo() + "</strong></div>");
			email.setMensagem(mensagem.toString());
			email.setNomeRemetente(parametro.getUsuarioEmail());
			email.setDestinatarios(empresaConveniada.getEmail());
			if (corridaNova.getEmail() != null && !corridaNova.getEmail().isEmpty()) {
				email.getDestinatarios().concat(",").concat(corridaNova.getEmail());
			}
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private BufferedImage toBufferedImage(BitMatrix matrix) {

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int onColor = 0xFF000000;
		int offColor = 0xFFFFFFFF;
		int[] rowPixels = new int[width];
		BitArray row = new BitArray(width);
		for (int y = 0; y < height; y++) {
			row = matrix.getRow(y, row);
			for (int x = 0; x < width; x++) {
				rowPixels[x] = row.get(x) ? onColor : offColor;
			}
			image.setRGB(0, y, width, 1, rowPixels, 0, width);
		}
		return image;
	}

	@Override
	public void alterarEmpresaConveniada(EmpresaConveniada empresaConveniadaAlterar) {
		empresaConveniadaRepository.save(empresaConveniadaAlterar);
	}

	@Override
	public void delete(EmpresaConveniada empresaConveniada) {
		empresaConveniadaRepository.delete(empresaConveniada);
	}

	@Override
	public void enviarEmailVoucherFinalizado(Corrida c, EmpresaConveniada empresa) {
		
		try {
			Parametro parametro = parametrosRepository.recuperarParametro();
			
			EmailVO email = new EmailVO();
			email.setAssunto(parametro.getNomeAplicativo() + " - Voucher da Corrida");
			StringBuilder mensagem = new StringBuilder();
			mensagem.append("Este é um e-mail confirmando a finalização da sua corrida, finalizada em ");
			mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(c.getDataFinalizacao()) + ".");
			mensagem.append("<br /> ");
			
			mensagem.append("<div style=\"width: 100%; height: auto; border: 1px dotted black;\"><div style=\"float: left; text-align: center; min-width: 270px; width: 40%; margin-top: 10px;\"><label style=\"font-weight: bold;\">" + c.getVoucher() + "</label><br />");
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			
			// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(c.getId() 
					+ "###FROTA###" + c.getUsuario().getId() 
					+ "###FROTA###" + c.getVoucher(), BarcodeFormat.QR_CODE, 250,
					250, hintMap);
			BufferedImage image = toBufferedImage(byteMatrix);
			
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			String imagem = Base64.getEncoder().encodeToString(os.toByteArray());
			
			mensagem.append("<img src=\"data:image/png;base64, " + imagem + "\" /></div>");
			
			mensagem.append("<div style=\"float: left; width: 60%\">");
			
			mensagem.append("<div style=\"padding-top: 20px;\"><label style=\"font-weight: bold; \">Passageiro:</label><br />");
			mensagem.append(c.getNomePassageiro() + "</div><br />");
			
			mensagem.append("<div><label style=\"font-weight: bold;\">Data da Solicitação:</label><br />");
			mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(c.getDataSolicitacao()) + "</div><br />");
			
			mensagem.append("<div><label style=\"font-weight: bold;\">Origem (Endereço):</label><br />");
			mensagem.append(c.getOrigemEndereco() + "</div><br />");
			
			if (c.getDestinoEndereco() != null && !c.getDestinoEndereco().isEmpty()) {
				mensagem.append("<div><label style=\"font-weight: bold;\">Destino (Endereço):</label><br />");
				mensagem.append(c.getDestinoEndereco() + "</div><br />");
			}
			
			if (c.getValorFinal() != null) {
				mensagem.append("<div><label style=\"font-weight: bold;\">Valor Final da Corrida com Desconto:</label><br />");
				mensagem.append("R$ " + c.getValorFinal() + "</div><br />");
			}
			
			mensagem.append("</div></div>");
			
			mensagem.append("<br /><br /> ");
			mensagem.append("<div style=\"clear: both;\">Atenciosamente,<br /> ");
			mensagem.append("<strong>Equipe " + parametro.getNomeAplicativo() + "</strong></div>");
			email.setMensagem(mensagem.toString());
			email.setNomeRemetente(parametro.getUsuarioEmail());
			email.setDestinatarios(c.getEmail());
			if (c.getEmail() != null && !c.getEmail().isEmpty()) {
				email.getDestinatarios().concat(",").concat(c.getEmail());
			}
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<EmpresaConveniada> recuperarEmpresasVoucherEletronico() {
		return empresaConveniadaRepository.recuperarEmpresasVoucherEletronico();
	}

	@Override
	public Long pesquisarCountCentroCustoPorFiltro(CentroCusto filtro) {
		return centroCustoRepository.pesquisarCountCentroCustoPorFiltro(filtro);
	}

	@Override
	public List<CentroCusto> pesquisarCentroCustoPorFiltro(CentroCusto filtro, int first, int pageSize) {
		return centroCustoRepository.pesquisarCentroCustoPorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<CentroCusto> recuperarCentroCustos(Long idEmpresaConveniada) {
		return centroCustoRepository.recuperarCentroCustos(idEmpresaConveniada);
	}

	@Override
	public void salvarCentroCusto(CentroCusto centroCusto) {
		centroCustoRepository.save(centroCusto);
	}
	
}