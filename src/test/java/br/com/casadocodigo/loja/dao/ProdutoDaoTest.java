package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builder.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;
import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, ProdutoDao.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProdutoDaoTest {

	@Autowired
	private ProdutoDao produtoDao;
	
	@SuppressWarnings("deprecation")
	@Test
	@Transactional // Tu tem que dizer pro Spring criar transação para inserir no banco as infos.
	public void deveSomarTodosOsPrecosPorTipoDeLivro() {

		List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN).more(3)
				.buildAll();

		List<Produto> livrosEbook = ProdutoBuilder.newProduto(TipoPreco.EBOOK, BigDecimal.TEN).more(3)
				.buildAll();
		
		// Java 8...
		livrosImpressos.stream().forEach(produtoDao::gravar);
		livrosEbook.stream().forEach(produtoDao::gravar);
		
		BigDecimal valor = produtoDao.somaPrecosPorTipoPreco(TipoPreco.EBOOK);
		
		Assert.assertEquals(new BigDecimal(40).setScale(2), valor);
	}
}
