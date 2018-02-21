package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.model.Produto;

@Repository
@Transactional
public class ProdutoDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void gravar(Produto produto) {
		entityManager.persist(produto);
	}

	public List<Produto> listar() {
		// O nome da tabela é case sensitive, então tem que ser igual ao que
		// mostra no banco.
		return entityManager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		return (Produto) entityManager
				.createQuery("SELECT DISTINCT(p) FROM Produto p " + " join fetch p.precos preco WHERE p.id = :id",
						Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}
}
