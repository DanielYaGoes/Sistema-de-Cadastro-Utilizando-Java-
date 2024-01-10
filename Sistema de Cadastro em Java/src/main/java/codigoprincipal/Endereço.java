package codigoprincipal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "endereco")
public class Endereço {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne 
	private Pessoa pessoa;

	private String cidade;
	
	private String bairro;

	private String rua;

	private int quadra;
	
	private int lote;
	
	
	public Endereço () {
		
	}
	
	public Endereço(String cidade, String Bairro, String rua, int quadra, int lote) {
		this.cidade=cidade;
		this.bairro = Bairro;
		this.rua=rua;
		this.quadra =quadra;
		this.lote = lote;
	
	}
	
	public Endereço(int id_endereco,String cidade, String Bairro, String rua, int quadra, int lote) {
		this.cidade=cidade;
		this.bairro = Bairro;
		this.rua=rua;
		this.quadra =quadra;
		this.lote = lote;
		
		this.id = id_endereco;
	}

	@OneToOne (mappedBy = "Pessoa")
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return  "Cidade: "+ this.getCidade()+ "| Bairro: "+ this.getBairro()+"| Rua: "+ this.getRua()+"| Quadra: "+this.getQuadra() +"| Lote: "+ this.getLote();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getQuadra() {
		return quadra;
	}

	public void setQuadra(int quadra) {
		this.quadra = quadra;
	}

	public int getLote() {
		return lote;
	}

	public void setLote(int lote) {
		this.lote = lote;
	}
	
}
