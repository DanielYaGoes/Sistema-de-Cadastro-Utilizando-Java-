package codigoprincipal;

import conection.ConnectionFactory;

import java.util.Scanner;

import javax.persistence.EntityManager;

import java.sql.SQLException;

import java.util.InputMismatchException;

public class Menu {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner leitor = new Scanner(System.in);

		EntityManager em = ConnectionFactory.getConnection();

		try {
			// Connection conexao = ConnectionUtil.getIntance().getConnection();
			iniciarMenu();
		} catch (InputMismatchException a) {
			System.out.println("Favor digitar um numero");
			iniciarMenu();
		}
		em.close();
	}

	public static void iniciarMenu() {
		Scanner leitor = new Scanner(System.in);
		boolean controledomenu = true;

		while (controledomenu) {

			System.out.println("--Bem vindo ao Menu digite o codigo correspondente a ação que deseja fazer--");
			System.out.println("0 - Sair");
			System.out.println("1 - Listar pessoas");
			System.out.println("2 - Cadastrar pessoa");
			System.out.println("3 - Excluir pessoa");
			System.out.println("4 - Editar pessoa");
			System.out.println("----Area de contatos-----");
			System.out.println("5 - Listar Contatos");
			System.out.println("6 - Editar Contato");
			System.out.println("7 - Excluir Contato");
			System.out.println("8 - Adicionar Contato");
			int varivaeldasopcoes = leitor.nextInt();

			switch (varivaeldasopcoes) {
			case 0: {
				controledomenu = false;
				break;
			}
			case 1: {
				try {
					Agenda.listarPessoas();
				} catch (Exception e) {

					e.printStackTrace();
				}
				break;
			}
			case 2: {
				try {
					Cadastro.iniciarcadastro();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				break;
			}
			case 3: {

				if (Agenda.verificaSeTemPessoaNaAgenda()) {
					Agenda.listarSomenteInformacoesDaPessoa();
					System.out.println("Digite o Id do usuario que deseja excluir");
					Integer id = leitor.nextInt();
					Pessoa pessoa = Agenda.findPessoaById(id);
					if (pessoa != null) {
						Agenda.delete(pessoa, id);
					} else {
						System.out.println("Pessoa não encontrada");
					}
				}

				break;
			}
			case 4: {
				if (Agenda.verificaSeTemPessoaNaAgenda()) {
					Agenda.listarSomenteInformacoesDaPessoa();
					System.out.println("Digite o Id do usuario que deseja editar");
					Integer id = leitor.nextInt();
					Pessoa pessoa = Agenda.findPessoaById(id);
					if (pessoa != null) {
						System.out.println("Digite o numero do que deseja editar: ");
						System.out.println("1 - Nome");
						System.out.println("2 - CPF");
						System.out.println("3 - E-mail");
						System.out.println("4 - Telefone ");
						System.out.println("5 - Cidade");
						System.out.println("6 - Bairro");
						System.out.println("7 - Rua");
						System.out.println("8 - Quadra");
						System.out.println("9 - Lote");
						int opcao = leitor.nextInt();
						Agenda.editarPessoa(pessoa, opcao);

					} else {
						System.out.println("Pessoa não encontrada");
					}
				}

				break;
			}
			case 5: {
				Agenda.listarContatosDaAgenda();
				break;
			}
			case 6: {
				if (Agenda.verificaSeTemPessoaNaAgenda()) {
					Agenda.listarContatosDaAgenda();
					System.out.println("Digite o Id do contato que deseja editar");
					Integer id = leitor.nextInt();
					Contato contato = Agenda.findContatoById(id);
					if (contato != null) {
						System.out.println("Digite o numero do que deseja editar: ");
						System.out.println("1 - Nome");
						System.out.println("2 - E-mail");
						System.out.println("3 - Telefone");

						int opcao = leitor.nextInt();
						Agenda.editarContato(contato, opcao);

					} else {
						System.out.println("Contato não encontrada");
					}
				}

				break;
			}
			case 7: {

				if (Agenda.verificaSeTemPessoaNaAgenda()) {
					Agenda.listarContatosDaAgenda();
					System.out.println("Digite o Id do contato que deseja excluir");
					Integer id = leitor.nextInt();
					Agenda.excluirContato(id);
				}

				break;
			}
			case 8: {
				if (Agenda.verificaSeTemPessoaNaAgenda()) {
					Agenda.listarSomenteInformacoesDaPessoa();
					System.out.println("Digite o Id do usuario que deseja adicionar um novo contato");
					Integer id = leitor.nextInt();

					Pessoa pessoa = Agenda.findPessoaById(id);
					if (pessoa != null) {
						pessoa.adicionarnovocontato(Cadastro.cadastroDeContatosParaUsuarioJaExistente(pessoa));
						Agenda.save(pessoa);
						System.out.println("Contato Salvo");

					} else {
						System.out.println("Pessoa não encontrada");
					}
				}
				break;
			}

			default: {
				System.out.println("opção invalida");
			}

			}
		}
	}
}
