package br.com.casadocodigo.loja.model;

import java.math.BigDecimal;

public class DadosPagamento {

	// O valor TEM QUE SE CHAMAR value
	private BigDecimal value;
	
	public DadosPagamento(BigDecimal value){
		this.value = value;
	}
	
	// Boa prática para deixar o sistema compilando.
	public DadosPagamento(){
		
	}
	
	public BigDecimal getValue(){
		return value;
	}
}
