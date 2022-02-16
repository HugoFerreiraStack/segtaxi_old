package br.com.seg.econotaxi;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import br.com.seg.econotaxi.util.ViewScope;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Fábrica de beans
     * 
     * @return Fábrica de beans
     */
    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new BeanFactoryPostProcessor() {

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) 
            		throws BeansException {
                configurableListableBeanFactory.registerScope("view", new ViewScope());
            }

        };
    }
    
}
