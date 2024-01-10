package codigoprincipal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cadastro {

	private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	static Scanner leitor = new Scanner(System.in);
	
	public static void iniciarcadastro() throws SQLException {
	
		ArrayList<Contato> contatos = new ArrayList();
		// variaveis para o cadastro;
		String nome;
		String cpf;
		String email;
		String telefone;
		String cidade;
		String bairro;
		String rua;
		int quadra = 0;
		int lote = 0;

		Scanner leitor = new Scanner(System.in);
		System.out.println("-------Bem vindo a tela de cadastro---------");
		System.out.println("Favor informar o nome: ");
		nome = confirmaNome();
		System.out.println("Seja bem vindo(a) " + nome);
		System.out.println("Favor informe seu Cpf: ");
		cpf = leitor.next();
		while (!validaCPF(cpf)) {
			System.out.println("CPF invalido, favor informar CPF valido:");
			cpf = leitor.next();
		}
		System.out.println("Informe seu e-mail:");
		email = leitor.next();
		while (!validaFormatoDeEmail(email)) {
			System.out.println("E-mail invalido, favor informar e-mail valido:");
			email = leitor.next();
		}
		leitor.nextLine();
		System.out.println("Informe seu telefone");
		telefone = leitor.next();
		while (!validaTelefone(telefone)) {
			System.out.println("Telefone invalido, favor informar Telefone valido:");
			telefone = leitor.next();
		}
		leitor.nextLine();
		System.out.println("Agora vamos precisar do seu endereço, poderia informar o nome da sua cidade?");
		cidade = leitor.nextLine();
		System.out.println("Informe o nome do seu bairro:");
		bairro = leitor.nextLine();
		System.out.println("Informe o nome da sua rua:");
		rua = leitor.next();
		quadra = preencherQuadra();
		lote = preencherLote();
		leitor.nextLine();
		
		Pessoa pessoa = new Pessoa(nome, cpf, email, telefone);
		Endereço endereco = new Endereço(cidade,bairro,rua,quadra,lote);
		endereco.setPessoa(pessoa);
		pessoa.setEndereco(endereco); 
		cadastroDeContatos(pessoa);
		
		Agenda.save(pessoa);
		System.out.println("Pessoa Cadastrada");
	
	}

	public static void cadastroDeContatos(Pessoa pessoa) throws SQLException {
		ArrayList<Contato> contatos = new ArrayList<Contato>();
		int ctrl = 2;
		String nome;
		String email;
		String telefone;
		leitor.nextLine();
		System.out.println(
				"Agora será necessário cadastrar seus contatos, será necessário no minimo 2 contatos para prosseguir:");
		while (ctrl != 0) {
			System.out.println("Informe o nome do seu novo contato: ");
			nome = confirmaNome();
			System.out.println("Informe o e-mail de " + nome);
			email = leitor.next();
			while (!validaFormatoDeEmail(email)) {
				System.out.println("E-mail invalido, favor informar e-mail valido:");
				email = leitor.next();
			}
			System.out.println("Informe o telefone de " + nome);
			telefone = leitor.next();
			leitor.nextLine();
			while (!validaTelefone(telefone)) {
				System.out.println("Telefone invalido, favor informar Telefone valido:");
				telefone = leitor.next();
				leitor.nextLine();
			}
			System.out.println();
			Contato contato = new Contato(nome,email,telefone);
			contato.setPessoa(pessoa);
			
			contatos.add(contato);
			System.out.println("Contato cadastrado");
		
			if (ctrl - 1 == 0) {
				System.out.println("Deseja cadastrar um novo contato? Digite 1 para não ou digite 2 para sim");
				String valordecontrole = leitor.next();
		
				while(!valordecontrole.equals("1") && !valordecontrole.equals("2") ) {
					System.out.println("Favor digitir um dos numeros validos");
					System.out.println("Deseja cadastrar um novo contato? Digite 1 para não ou digite 2 para sim");
					valordecontrole = leitor.next();
				}
				
				ctrl = Integer.parseInt(valordecontrole);
			}
			
			ctrl--;
			
		}
		pessoa.setContatos(contatos);
	}

	public static Contato cadastroDeContatosParaUsuarioJaExistente(Pessoa pessoa) {
		String nome;
		String email;
		String telefone;

		System.out.println("Informe o nome do seu novo contato: ");
		nome = confirmaNome();
		System.out.println("Informe o e-mail de " + nome);
		email = leitor.next();
		while (!validaFormatoDeEmail(email)) {
			System.out.println("E-mail invalido, favor informar e-mail valido:");
			email = leitor.next();
		}
		leitor.nextLine();
		System.out.println("Informe o telefone de " + nome);
		telefone = leitor.nextLine();
		while (!validaTelefone(telefone)) {
			System.out.println("Telefone invalido, favor informar Telefone valido:");
			telefone = leitor.next();
		}
	
		Contato contato = new Contato(nome, email, telefone);
		contato.setPessoa(pessoa);
		return contato;
	}

	public static int preencherQuadra() {
		String quadra = null;
		int guardavalordaquadra = 0;

		try {
			System.out.println("Informe o numero da sua quadra: ");
			quadra = leitor.next();
			guardavalordaquadra = Integer.parseInt(quadra);

		} catch (NumberFormatException e) {
			quadra = null;
			System.out.println("Favor infomar um numero");
			preencherQuadra();
		} finally {

		}
		return guardavalordaquadra;
	}

	public static int preencherLote() {
		String lote = null;
		int guardavalordalote = 0;

		try {
			System.out.println("Informe o numero do seu lote: ");
			lote = leitor.next();
			guardavalordalote = Integer.parseInt(lote);

		} catch (NumberFormatException e) {
			lote = null;
			System.out.println("Favor infomar um numero");
			preencherLote();
		} finally {

		}
		return guardavalordalote;

	}

	public static boolean validaTelefone(String numero) {
		// Use uma expressão regular para verificar se o número está no formato desejado
		String regex = "\\d{11}"; // Significa exatamente 11 dígitos
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(numero);

		return matcher.matches();
	}

	public static boolean validaFormatoDeEmail(String email) {
		if (email == null) {
			return false;
		}
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	public static boolean validaCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static String confirmaNome() {
		int ctrl;
		String nome;
		nome = leitor.nextLine();
		if (nome == ""){
			nome = confirmaNome();

		} 
		return nome;

	}
}
