package br.com.seg.econotaxi.enums;

import java.util.Arrays;
import java.util.Optional;

public enum MenuEnum {

	DASHBOARD (1, "Dashboard"),
	PERFIL (2, "Perfis"),
	USUARIO (3, "Usuários"),
	MOTORISTA (4, "Motoristas"),
	ALERTAS (5, "Alertas"),
	CORRIDA (6, "Corridas"),
	CIDADE (7, "Cidades"),
	VEICULO (8, "Veículos"),
	TRANSACAO (9, "Transações"),
	PACOTE (10, "Pacotes"),
	PASSAGEIROS (11, "Passageiros"),
	RADIO (12, "Rádios"),
	AUDITORIA (13, "Auditoria"),
	PARAMETROS (14, "Parâmetros"),
	ENTREGAS (15, "Entregas"),
	NOTIFICACOES (16, "Notificações"),
	NOVIDADES (17, "Novidades"),
	LOJISTAS (18, "Lojistas"),
	PROMOCOES (19, "Promoções"),
	FERIADO (20, "Feriado"),
	RATEIO (21, "Rateio"),
	USUARIO_TELE_TAXI (22, "Usuários Tele-Táxi"),
	CORRIDA_TELE_TAXI (23, "Corrida Tele-Táxi"),
	LOCALIDADE (24, "Localidade"),
	CLIENTE_TELE_TAXI (25, "Clientes (Passageiros) Tele-Táxi"),
	ACAO (26, "Ações"),
	MAPA_TELE_TAXI (27, "Mapa Tele-Táxi"),
	MOTORISTA_TELE_TAXI (28, "Motoristas Tele-Táxi"),
	CONVENIADA_TELE_TAXI (29, "Empresas Conveniadas Tele-Táxi"),
	FINANCEIRO_TELE_TAXI (30, "Financeiro Tele-Táxi"),
	RELATORIO_TELE_TAXI (31, "Relatório Empresas Conveniadas"),
	RELATORIO_MOTORISTA_TELE_TAXI (32, "Relatório Motoristas de Rádio"),
	QUADRO_TELE_TAXI (33, "Quadro Tele-Táxi"),
	RELATORIO_VEICULO_TELE_TAXI (34, "Relatório Veículos de Rádio"),
	RELATORIO_MOTORISTA_SEG_CARTAO (35, "Relatório Motoristas SEG - Cartão"),
	NOTIFICACOES_RADIO (36, "Notificações Rádio"),
	ALERTAS_RADIO (37, "Alertas Rádio"),
	VEICULO_LIGUE (38, "Veículos Ligue-Táxi"),
	MOTORISTA_LIGUE (39, "Motororistas Ligue-Táxi"),
	MOTORISTA_CREDITO (40, "Motororistas Crédito"),
	VEICULO_VOUCHER (41, "Relatório Veículos x Voucher"),
	USUARIO_APP_EMPRESA (42, "Usuário App Empresa"),
	CARROS_SELECIONADOS (43, "Carros Selecionados x Empresa"),
	CENTRO_CUSTO (44, "Centro de Custo"),
	INICIO (99, "Tela Inicial");
	
	private Integer menu;
	private String descricaoMenu;
	
	/**
	 * Instancia um novo menu enum.
	 *
	 * @param menu o menu
	 * @param descricaoMenu a descrição do menu
	 */
	private MenuEnum(Integer menu, String descricaoMenu) {
		this.menu = menu;
		this.descricaoMenu = descricaoMenu;
	}
	
	/**
	 * Value of menu.
	 *
	 * @param menu o menu
	 * @return menu o menu enum
	 */
	public static MenuEnum valueOfMenu(Integer menu) {
		Optional<MenuEnum> optional = Arrays.asList(values()).stream()
				.filter(MenuEnum -> MenuEnum.getMenu().equals(menu)).findFirst();
		
		return (optional.isPresent()) ? optional.get() : null;
	}
	
	// Métodos get/set
	public Integer getMenu() {
		return menu;
	}
	public String getDescricaoMenu() {
		return descricaoMenu;
	}
	
}