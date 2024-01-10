package codigoprincipal;

import java.util.List;
import java.util.Scanner;

import Dao.*;


public abstract class Agenda extends RepositoryDAO<Object> {
	static Scanner leitor = new Scanner(System.in);

	static void listarPessoas() {

		List<Pessoa> pessoas = findAllPessoa();
		
		if (verificaSeTemPessoaNaAgenda()) {
			for (Pessoa pessoa : pessoas) {
				
				System.out.println(pessoa);
				System.out.println("Contatos de " + pessoa.getNome());
				pessoa.setContatos(retornaContatosDaPessoa(pessoa));
				pessoa.listarContatos();
			}
		}
	}

	public static void listarSomenteInformacoesDaPessoa() {

		List<Pessoa> pessoas = findAllPessoa();
		
		if (verificaSeTemPessoaNaAgenda()) {
			for (Pessoa pessoa : pessoas) {
				System.out.println(pessoa);
			}
		}

	}

	public static void listarContatosDaAgenda() {

		List<Pessoa> pessoas = Agenda.findAllPessoa();
		
		if (verificaSeTemPessoaNaAgenda()) {
			for (Pessoa pessoa : pessoas) {
				System.out.println("Contatos de " + pessoa.getNome());
				pessoa.setContatos(retornaContatosDaPessoa(pessoa));
				pessoa.listarContatos();
			}
		}
	
	}

	public static boolean verificaSeTemPessoaNaAgenda() {

		List<Pessoa> pessoas = findAllPessoa();

		if (pessoas.size() == 0) {
			System.out.println("A agenda está vazia");
			return false;
		}

		return true;
	}

	public static void editarPessoa(Pessoa pessoa, int identificador) {

		switch (identificador) {
		case 1: {
			System.out.println("Digite o novo nome: ");

			String nome = Cadastro.confirmaNome();

			pessoa.setNome(nome);
			System.out.println("Edição de nome feita");

			break;
		}
		case 2: {
			System.out.println("Digite o novo CPF: ");
			String cpf = leitor.next();
			while (!Cadastro.validaCPF(cpf)) {
				System.out.println("CPF invalido, favor informar CPF valido:");
				cpf = leitor.next();
			}
			pessoa.setCpf(cpf);
			System.out.println("Edição de CPF feita");
			break;
		}
		case 3: {
			System.out.println("Informe seu e-mail:");
			String email = leitor.next();
			while (!Cadastro.validaFormatoDeEmail(email)) {
				System.out.println("E-mail invalido, favor informar e-mail valido:");
				email = leitor.next();
			}
			pessoa.setEmail(email);
			System.out.println("Edição de E-MAIL feita");
			break;
		}
		case 4: {
			System.out.println("Informe seu novo Telefone:");
			String telefone = leitor.next();
			while (!Cadastro.validaTelefone(telefone)) {
				System.out.println("E-mail invalido, favor informar e-mail valido:");
				telefone = leitor.next();
			}
			pessoa.setEmail(telefone);
			System.out.println("Edição de Telefone feita");
			break;
		}
		case 5: {
			System.out.println("Informeo novo nome da cidade");
			String cidade = leitor.nextLine();
			pessoa.getEndereco().setCidade(cidade);
			System.out.println("Edição de Cidade feita");
			break;
		}
		case 6: {
			System.out.println("Informe o nome do novo bairro");
			String bairro = leitor.nextLine();
			pessoa.getEndereco().setBairro(bairro);

			System.out.println("Edição de Bairro feita");
			break;
		}
		case 7: {
			System.out.println("Informe o nome da nova rua");
			String rua = leitor.nextLine();
			pessoa.getEndereco().setRua(rua);

			System.out.println("Edição de Rua feita");
			break;
		}
		case 8: {

			int quadra = Cadastro.preencherQuadra();
			pessoa.getEndereco().setQuadra(quadra);
			System.out.println("Edição de Quadra feita");
			break;
		}
		case 9: {

			int lote = Cadastro.preencherLote();
			pessoa.getEndereco().setLote(lote);
			System.out.println("Edição de Lote feita");
			break;
		}
		default:
			System.out.println("Essa opção não está disponivel");
		}

		Agenda.save(pessoa);
	}

	public static void editarContato(Contato contato, int identificador) {
		switch (identificador) {
		case 1: {
			System.out.println("Digite o novo nome: ");

			String nome = Cadastro.confirmaNome();

			contato.setNome(nome);
			System.out.println("Edição de nome feita");

			break;
		}
		case 2: {
			System.out.println("Informe seu e-mail:");
			String email = leitor.next();
			while (!Cadastro.validaFormatoDeEmail(email)) {
				System.out.println("E-mail invalido, favor informar e-mail valido:");
				email = leitor.next();
			}
			contato.setEmail(email);
			System.out.println("Edição de E-MAIL feita");
			break;
		}
		case 3: {
			System.out.println("Informe seu novo Telefone:");
			String telefone = leitor.next();
			while (!Cadastro.validaTelefone(telefone)) {
				System.out.println("Telefone invalido, favor informar telefone valido:");
				telefone = leitor.next();
			}
			contato.setEmail(telefone);
			System.out.println("Edição de Telefone feita");
			break;
		}
		default: {
			System.out.println("Opção selecionada invalida");
			break;
		}
		
		}
		Agenda.save(contato);
	}
	
	public static void excluirContato(Integer id) {
		Contato contato = Agenda.findContatoById(id);
		
		if (contato != null && contato.getPessoa().getContatos().size()>2) {
			Agenda.delete(contato, id);
		} else if(contato == null){
			System.out.println("Contato não encontrado");
		} else if(contato.getPessoa().getContatos().size() <=2) {
			System.out.println("O usuario não pode ter menos de 2 contatos");
		}
	}

}