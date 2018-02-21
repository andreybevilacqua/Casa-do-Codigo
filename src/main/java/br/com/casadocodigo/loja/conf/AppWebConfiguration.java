package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;

import br.com.casadocodigo.loja.controller.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.model.CarrinhoCompras;

@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDao.class, FileSaver.class,  CarrinhoCompras.class })
@EnableCaching
public class AppWebConfiguration extends WebMvcConfigurerAdapter { // Para o Spring buscar fora dele os arquivos css, js, etc.

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();

		resolver.setPrefix("/WEB-INF/views/");
		// Todo a pagina Java eh JSP. Aqui tu já seta que todo o sufixo será
		// .jsp
		// dai o usuário nao precisa escrever no endereço.
		resolver.setSuffix(".jsp");

		// Assim, todos os beans ficam disponíveis como atributos na JSP.
		//resolver.setExposeContextBeansAsAttributes(true);
		
		// Agora, tu soh define os beans que tu quer expor para JSP.
		// Mesmo nome da classe, com a primeira letra minúscula!!!
		// Isso aqui é: export atributos no JSP.
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		return resolver;
	}

	// Aqui tu configura o arquivo padrão de mensagens a ser utilizado pelo servidor.
	// Esse método tb deve ser controlado pelo Spring, por isso temos que colocar o @Bean.
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();
		bundleMessageSource.setBasename("/WEB-INF/messages");
		bundleMessageSource.setDefaultEncoding("UTF-8");
		bundleMessageSource.setCacheSeconds(1); // Pode alterar o message e o servidor reconhece, sem fazer reset.

		return bundleMessageSource;
	}

	// Definindo padrão de campos de data.
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();

		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);

		return conversionService;
	}

	// Configuração para viabilizar que tu receba arquivos de vários formatos no web app.
	// Só gravamos ele em uma pasta, não estamos inserindo no banco.
	// Tem configurações no ServletSpringMVC.java também.
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	// Falar para o Spring qual o servlet que deve capturar as requisições default de js, css, etc.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	// https://cursos.alura.com.br/forum/topico-nao-carrega-css-21274
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
        			.addResourceLocations("resources/");
    }
	
	@Bean
	// Metódo que vai prover um hashmap chave-valor que será o gerente de cache do Spring. Coloca @Cacheable no método que tu quer cachear.
	// Vamos utilizar um framework Cache do Google: o Google Guava.
	public CacheManager cacheManager() {
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager manager = new GuavaCacheManager();
		
		manager.setCacheBuilder(builder);
		
		return manager;
	}
	
	@Bean
	// Método que define o que será aberto: HTML ou JSON
	public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		viewResolvers.add(internalResourceViewResolver()); // Esse método interno dessa classe resolve as requisições para HTML.
		viewResolvers.add(new JsonViewResolver()); // Essa classe resolve a visualização no formato JSON.
		
		resolver.setViewResolvers(viewResolvers);
		return resolver;
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
		super.configureContentNegotiation(configurer);
	}
	
	
}
