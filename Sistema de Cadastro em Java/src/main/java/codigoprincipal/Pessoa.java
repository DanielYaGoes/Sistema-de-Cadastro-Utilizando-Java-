package codigoprincipal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private String cpf;

	private String email;

	private String telefone;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_endereco")
	private Endereço endereco;

	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Contato> contatos = new ArrayList<Contato>();

	public Pessoa() {
	}

	public Pessoa(String nome, String cpf, String email, String telefone) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.telefone = telefone;

	}

	public void adicionarnovocontato(Contato contato) {
		this.contatos.add(contato);
	}

	public void listarContatos() {
		for (Contato contato : contatos) {
			System.out.println("| Id: "+contato.getId() + " | Nome: " + contato.getNome() + " | E-mail: " + contato.getEmail() + " | Telefone: "
					+ contato.getTelefone());
		}
		;

	}

	@Override
	public String toString() {

		return "Id: " + this.getId() + "| Nome: " + this.getNome() + "| Cpf: " + this.getCpf() + "| E-mail: "
				+ this.getEmail() + "| Telefone: " + this.getTelefone() + "\nEndereço: " + this.getEndereco();
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereço getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereço endereco) {
		this.endereco = endereco;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

}
