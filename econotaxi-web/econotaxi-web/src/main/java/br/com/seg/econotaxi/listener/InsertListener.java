package br.com.seg.econotaxi.listener;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.seg.econotaxi.enums.TipoTransacaoEnum;
import br.com.seg.econotaxi.model.Auditoria;
import br.com.seg.econotaxi.view.comum.LoginView;

@Component
public class InsertListener extends EventListener implements PostInsertEventListener {
	
    // Constantes
	private static final long serialVersionUID = 1310405824188015537L;
	
	@Autowired
    private EntityManagerFactory entityManagerFactory;
	@Autowired
	private EntityManager entityManager;

    @PostConstruct
    private void init() {
    	
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
    }

    @Override
    public void onPostInsert(PostInsertEvent postInsertEvent) {
    	
    	if (!postInsertEvent.getEntity().getClass().equals(Auditoria.class)) {
    		Auditoria auditoria = new Auditoria();
    		LoginView view = null;
    		try {
    			view = (LoginView) recuperarBean("loginView", LoginView.class);
			} catch (Exception e) {}
    		if (view != null && view.getUsuario() != null) {
    			auditoria.setIdUsuario(view.getUsuario().getId());
    			auditoria.setTransacaoViaSistema(0);
    			if (FacesContext.getCurrentInstance() != null 
    					&& FacesContext.getCurrentInstance().getExternalContext() != null
    					&& FacesContext.getCurrentInstance().getExternalContext().getRequest() != null) {
    				HttpServletRequest request = (HttpServletRequest) 
    						FacesContext.getCurrentInstance().getExternalContext().getRequest();
    				if (request != null) {
    					String ipAddress = request.getHeader("X-FORWARDED-FOR");
    					if (ipAddress == null) {
    						ipAddress = request.getRemoteAddr();
    						auditoria.setIp(ipAddress);
    					}
    				}
    			}
    		} else {
    			auditoria.setTransacaoViaSistema(1);
    		}
    		auditoria.setDataEvento(new Date());
    		auditoria.setClasse(postInsertEvent.getEntity().getClass().getCanonicalName());
    		auditoria.setObjeto(converterObjectParaJson(postInsertEvent.getEntity()));
    		auditoria.setTipoTransacao(TipoTransacaoEnum.INSERT.getCodigo());
    		entityManager.persist(auditoria);
    		//entityManager.flush();
    	}
    }

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return false;
	}
	
}