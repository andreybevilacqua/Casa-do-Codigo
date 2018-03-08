package br.com.casadocodigo.loja.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDao;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.model.Preco;
import br.com.casadocodigo.loja.model.Produto;
import br.com.casadocodigo.loja.model.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
//Quando o mapping ta na classe, todos os métodos dessa classe vão partir de /produtos.
@RequestMapping("/produtos") 
public class ProdutosController {
	
	@Autowired
	private ProdutoDao produtoDao;
	
	@Autowired
	private FileSaver fileSaver;

	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new ProdutoValidation());
	}
	
	@RequestMapping("/form")
	public ModelAndView form(Produto produto){
		// No construtor tu passa a tela que ele vai encaminhar o usuario.
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		modelAndView.addObject("tipos", TipoPreco.values());
		
		return modelAndView;
	}
	
	// PS: A ordem dos parâmetros do teu método tem que ser essa, pq o Spring
	// considera que logo depois da validação DEVE VIR O RESULTADO.
	@RequestMapping(method=RequestMethod.POST) // Aqui é POST pois tu ta enviando um produto para ser salvo.
	@CacheEvict(value="produtosHome", allEntries=true) // Toda a vez que o Spring executar esse método, ele deve limpar o cache, para gerar ele novamente.
	public ModelAndView grava(MultipartFile sumario, @Valid Produto produto, BindingResult bidingResult,
			RedirectAttributes redirectAttributes){
		
		// Aqui é a última parte da ligação. O objeto BidingResult vai te retornar
		// se houve erro no processo de validação dentro do ProdutoValidation
		// ou não. Caso haja, retorna para o form.
		if(bidingResult.hasErrors()){return form(produto);}
		
		// Pasta do s.o. O sumário é o arquivo que o usuário mandou.
		String path = fileSaver.write("arquivos-sumario", sumario);
		produto.setSumarioPath(path);
		
		// Por algum motivo, ele ta sempre pegando o Tipo em Preco = null.
		// Depois de quebrar bastante a cabeça, segue a gambiarra abaixo:
		if(produto.getPrecos().get(0).getTipo() == null){
			produto.setPrecos(setPrecos(produto));
		}
		
		produtoDao.gravar(produto);
		
		// Flash Scoped é um escopo rápido, onde os objetos adicionados duram apenas o tempo de
		// 1 request para outro.
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		
		// Técnica: "always redirect after POST". O Redirect (HTTP 302) acontece via GET.
		// O Redirect tem que ser EXATAMENTE O MESMO NOME DA PÁGINA QUE TU QUER MANDAR!
		// Nesse caso aqui, estava só "produtos", e deveria ser "/produtos".
		return new ModelAndView("redirect:/produtos");
	}
	
	private List<Preco> setPrecos(Produto produto) {
		for(int i = 0; i < produto.getPrecos().size(); i++){
			if(i == 0){	produto.getPrecos().get(i).setTipoPreco(TipoPreco.EBOOK);}
			else if (i == 1){ produto.getPrecos().get(i).setTipoPreco(TipoPreco.IMPRESSO);}
			else {produto.getPrecos().get(i).setTipoPreco(TipoPreco.COMBO);}
		}
		
		return produto.getPrecos();

	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
		List<Produto> produtos = produtoDao.listar();
		
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	@RequestMapping("/detalhe/{id}") // Mesmo nome do parâmetro recebido no método. Tudo é ligado pelo @PathVariable
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
		Produto produto = produtoDao.find(id);
		modelAndView.addObject("produto", produto); 
		
		return modelAndView;
	}
	
}
