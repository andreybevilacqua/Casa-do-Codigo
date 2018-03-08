package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// Classes para iniciarlizar junto com o Spring.
		return new Class[] {AppWebConfiguration.class
						 , JPAConfiguration.class
						 , SecurityConfiguration.class
						 , JPAProductionConfiguration.class};
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
		
		// Para não precisar colocar "join fetch" nas consultas para que o JPA traga na consulta
		// algo que ele não precisa na hora, mas pode precisar no futuro, criamos o filtro
		// do Spring chamado OpenEntityManagerInViewFilter, o qual resolve esse problema.
		// Assim, a lista que é Lazy (que será carregada depois), vai vir depois também.
		// O Spring mantém o EntityManager aberto automaticamente para isso.
		
		// Um problema!! Quando tu usa isso, o sistema começa a fazer muitas consultas, toda a hora.
		// O Hibernate passa a fazer uma nova consulta para CADA REGISTRO que ele buscar.
		// Portanto, para as consultas que tiverem Lazy Initialization (aquelas que não busca todos
		// os registros entre as entidaddes pq não é necessários), colocar "join fetch entidade.coluna"
		// na query É MUITO MELHOR do que usar OpenEntityManagerInViewFilter.
		
		return new Filter[] {characterEncodingFilter, new OpenEntityManagerInViewFilter()};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// Qual é a parte que será configurada como arquivo?
		// Com o parâmetro em branco, a gente quer que ele envie o arquivo
		// simplesmente do jeito que ele vem, que ele é recebido.
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	// Na inicialização, devemos falar agora qual o ActiveProfile do banco a aplicação deve usar.
	// Durante a subida do servidor, o profile que a gente quer que fique ativo, que funcione,
	// é o profile dev.
	// Para que ele encontre o profile, ele tem que ficar "ouvindo os contextos", portanto, usar o Listener que entregamos pra ele.
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		super.onStartup(servletContext);
//		servletContext.addListener(RequestContextListener.class);
//		servletContext.setInitParameter("spring.profiles.active", "dev");
//	} // Método comentado pois, sempre que ele fazer o startup, ele setaria o profile como "dev". Para ir para o Heroku, temos que tirar isso.

}


