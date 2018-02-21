package br.com.casadocodigo.loja.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
// Cria uma instância do usuário para cada CarrinhoCompras
// O ProxyMode faz a ligação entre todas as classes que usarem CarrinhoCompras, pra tu não
// precisar colocar scope_session em tudo, ou mudar a scope_request da CarrinhoComprasController
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.TARGET_CLASS)
public class CarrinhoCompras implements Serializable{
	
	// Toda a vez que tu/servidor salvar uma sessão, o servidor vai salvar um objeto.
	// Assim ele serializa e salva esse objeto aqui em um arquivo, e retorna para o usuário esse mesmo objeto.
	private static final long serialVersionUID = 1L;
	
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();	
	
	public void add(CarrinhoItem item){
		itens.put(item, getQuantidade(item) + 1);
	}
	
	public Collection<CarrinhoItem> getItens() {
		return itens.keySet(); // Retorna uma lista de carrinhoItem.
	}

	// Integer = no caso de retornar null não da nenhum problema.
	public Integer getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)){
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	// Expressão Lambda do Java 8.
	// Este código percorre toda a lista de itens e soma a quantidade de cada item a um aculumador.
	public int getQuantidade(){
		return itens.values().stream().reduce(0, (proximo, acumulador) -> proximo + acumulador);
	}
	
	public BigDecimal getTotal(CarrinhoItem item){
		return item.getTotal(getQuantidade(item));
	}
	
	public BigDecimal getTotal(){
		BigDecimal total = BigDecimal.ZERO;
		for(CarrinhoItem item : itens.keySet()){
			total = total.add(getTotal(item));
		}
		return total;
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		// Daria pra consultar no banco e buscar esse produto;
		Produto produto = new Produto();
		produto.setId(produtoId);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
	
}
