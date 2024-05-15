package cadastrodb.model;

public class PessoaFisica extends Pessoa {
    private String cpf;
    
    // Construtor padrão
    public PessoaFisica() {
        super(); // Chama o construtor padrão da superclasse Pessoa
    }

    // Construtor completo
    public PessoaFisica(int id, String nome, String logradouro, String cidade, String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }
    
    // Método para exibir os dados no console 
    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + cpf);
    }

    // Getter e Setter para CPF
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
