package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;

@Repository
@Transactional // O JPA abre a transação quando ele entra na ProdutoDao, e fecha quando sai.
public class ProdutoDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void gravar(Produto produto) {
		entityManager.persist(produto);
	}

	public List<Produto> listar() {
		// O nome da tabela é case sensitive, então tem que ser igual ao que mostra no banco.		
		// Aqui temos um exemplo de "join fetch" para tratar Lazy Initialization Exception direto na query.
		// Essa é a melhor forma de tratar esse problema!
		return entityManager.createQuery("SELECT DISTINCT(p) FROM Produto p join fetch p.precos", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		return (Produto) entityManager
				.createQuery("SELECT DISTINCT(p) FROM Produto p " + " join fetch p.precos preco WHERE p.id = :id",
						Produto.class)
				.setParameter("id", id).getSingleResult();
	}

	public BigDecimal somaPrecosPorTipoPreco(TipoPreco tipoPreco) {
		TypedQuery<BigDecimal> query = entityManager.createQuery(
				"SELECT SUM(preco.valor) FROM Produto p " + " join p.precos preco WHERE preco.tipo = :tipoPreco ",
				BigDecimal.class);
		
		return query.setParameter("tipoPreco", tipoPreco).getSingleResult();
	}

}
