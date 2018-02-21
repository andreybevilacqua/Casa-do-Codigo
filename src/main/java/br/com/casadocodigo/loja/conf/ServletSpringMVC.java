package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// Classes para iniciarlizar junto com o Spring.
		return new Class[] {SecurityConfiguration.class, AppWebConfiguration.class, JPAConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// Retorna uma lista de classes que possuem as configuracoes do Spring Servlet.
		return new Class[] {};
	}

	@Override
	protected String[] getServletMappings() {
		
		// Retorna um array de String contendo o endereço que tu quer mapear.
		// Nesse caso, tudo em diante depois da "/".
		return new String[] {"/"};
	}
	
	// Colocar o characterSet pra mostrar corretamente na tela.
	@Override
	protected Filter[] getServletFilters() {
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		
		return new Filter[] {characterEncodingFilter};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// Qual é a parte que será configurada como arquivo?
		// Com o parâmetro em branco, a gente quer que ele envie o arquivo
		// simplesmente do jeito que ele vem, que ele é recebido.
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
