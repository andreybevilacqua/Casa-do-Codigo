package br.com.casadocodigo.loja.controller;

import br.com.casadocodigo.loja.conf.AppWebConfiguration;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // Como ele vai ser um controller do MVC, temos que adicionar essa notação.
@ContextConfiguration(classes = { JPAConfiguration.class, AppWebConfiguration.class
													   , DataSourceConfigurationTest.class
													   , SecurityConfiguration.class})
@ActiveProfiles("test")

public class ProdutosControllerTest {
/*
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private ComponentScan.Filter springSecurityFilterChain;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
									 .addFilter(springSecurityFilterChain)
									 .build();
	}
	
	// Teste bem interessante.
	// Fazemos uma requisição HTTP GET para a home do sistema, e perguntamos se o ResultMatchers vai
	// encaminhar para a URL "/WEB-INF/views/home.jsp".
	// Tu não precisa estar com o servidor rodando para rodar o teste!
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/")) // Verificar se, dentro da view, tem um attributo "produtos" dentro.
				.andExpect(MockMvcResultMatchers.model().attributeExists("produtos")) 
				.andExpect(MockMvcResultMatchers
				.forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
											 .with(SecurityMockMvcRequestPostProcessors
											 .user("user@casadocodigo.com.br").password("123456")
											 .roles("USUARIO")))
			   .andExpect(MockMvcResultMatchers.status().is(403));
		
	}*/
	
}






