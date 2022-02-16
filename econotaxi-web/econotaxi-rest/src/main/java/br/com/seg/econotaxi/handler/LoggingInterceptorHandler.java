package br.com.seg.econotaxi.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoggingInterceptorHandler extends HandlerInterceptorAdapter {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoggingInterceptorHandler.class);
	
	private static List<String> recursosNaoLogados;
	
	static {
		recursosNaoLogados = new ArrayList<>();
		
		recursosNaoLogados.add("/rest/corrida/solicitadas/");
		recursosNaoLogados.add("/rest/corrida/solicitadasEntregas/");
		recursosNaoLogados.add("/rest/usuario/salvarPosicao/");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (ehParaLogar(request)) {
			LOG.info("[User: " + getUsername() + "][preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI());
		}
		
		return Boolean.TRUE;
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (ehParaLogar(request)) {
			LOG.info("[User: " + getUsername() + "][postHandle][" + request + "]");
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		if (ex != null) {
			LOG.error(ex.getMessage(), ex);
			LOG.warn("[User: " + getUsername() + "][afterCompletion][" + request + "][error: " + ex + "]");
		}
		
		if (ehParaLogar(request)) {
			LOG.info("[User: " + getUsername() + "][afterCompletion][" + request + "][error: " + ex + "]");
		}
	}
	
	private String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null) {
			return authentication.getName();
		}
		
		return null;
	}
	
	private Boolean ehParaLogar(HttpServletRequest request) {
		Long count = recursosNaoLogados.stream().filter(recurso -> request.toString().contains(recurso)).count();
		return count.compareTo(0L) == 0;
	}
	
}
