package br.com.casadocodigo.loja.controller;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.model.CarrinhoCompras;
import br.com.casadocodigo.loja.model.DadosPagamento;
import br.com.casadocodigo.loja.model.Usuario;

@RequestMapping("/pagamento")
@Controller
public class PagamentoController {

	@Autowired
	private CarrinhoCompras carrinhoCompras;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MailSender mailSender;

	// O @AuthenticationPrincipal é do Spring Security. Ele busca qual usuário está logado e nos retorna.
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Callable<ModelAndView> finalizar(@AuthenticationPrincipal Usuario usuario
																  , RedirectAttributes redirectAttribute) {

		boolean retornoErro = false;
		
		if (carrinhoCompras.getQuantidade() == 0) {
			redirectAttribute.addFlashAttribute("falha", "Carrinho deve conter ao menos 1 item.");
			retornoErro = true;
		} else if (carrinhoCompras.getTotal().doubleValue() <= 0) {
			redirectAttribute.addFlashAttribute("falha", "Valor deve ser maior que 0");
			retornoErro = true;
		} 
		
		if(retornoErro == true){
			return (Callable<ModelAndView>) new ModelAndView("redirect:/");
		} else {

			// O servidor não é assincrono (ou seja, apenas uma thread), tu tem
			// que dizer isso pra ele. No Java 8 tu pode dizer
			// usando o Callable e disparando uma thread para esse método
			// finalizar. Assim ele não vai trancar toda a
			// aplicação nem fazer todos os usuários esperarem acabar cada
			// finalizar de cada usuário.

			// O Callable é uma classe anônima.
			// Para retornar uma classe anônima, usamos uma expressão do Java 8.
			// Essa expressão abaixo cria uma classe anônima já do tipo do
			// retorno.

			// No fim, isso é uma feature de performance, nos cenários em que
			// temos muitos usuários fazendo
			// muitas e muitas requisições, daí tu precisa criar uma thread
			// separada para executar o finalizar
			// enquanto os recursos do servidor ficam disponíveis para os outros
			// usuários.
			return () -> {
				String uri = "http://book-payment.herokuapp.com/payment";

				try {
					String response = restTemplate.postForObject(uri, new DadosPagamento(carrinhoCompras.getTotal()),
							String.class);
					//enviaEmailCompraProduto(usuario);
					redirectAttribute.addFlashAttribute("sucesso", response);
					return new ModelAndView("redirect:/");
				} catch (HttpClientErrorException e) {
					e.printStackTrace();
					redirectAttribute.addFlashAttribute("falha", "Valor maior que o permitido");
					return new ModelAndView("redirect:/");
				}
			};
		}
	}

	private void enviaEmailCompraProduto(Usuario usuario) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Compra finalizada com sucesso");
		//email.setTo(usuario.getEmail());
		email.setTo("andreybevilacqua@gmail.com");
		email.setText("Compra aprovada com sucesso no valor de " + carrinhoCompras.getTotal());
		email.setFrom("andreybevilaqua@gmail.com");
		
		// O Spring tem um objeto chamado Mail Sender, que é quem envia o email.
		mailSender.send(email);
		
	}
}
