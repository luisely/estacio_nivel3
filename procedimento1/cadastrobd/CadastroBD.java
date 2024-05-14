package cadastrobd;
import cadastro.model.util.SequenceManager;
import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import java.util.List;

public class CadastroBD {
    public static void main(String[] args) {
        SequenceManager sequenceManager = new SequenceManager();
        int id = sequenceManager.getValue("pessoa_id_seq");
        
        //6.a - Instanciar uma pessoa física e persistir no banco de dados.
        PessoaFisica pessoaFisica = new PessoaFisica(id, "Flavio", "Rua Osvaldo Aranha, 982", "Rio de Janeiro", "RJ", "123456789", "flavio@gmail.com", "123.456.789-00");
        
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();       
        pessoaFisicaDAO.incluir(pessoaFisica);
        
        System.out.println("-----------------------");
        
        //6.b - Alterar os dados da pessoa física no banco.
        pessoaFisica.setNome("Pietro Medeiros");
        pessoaFisica.setCidade("Porto Alegre");
        pessoaFisica.setCpf("123.999.555-01");
        pessoaFisica.setEmail("pietro@gmail.com");
        pessoaFisica.setEstado("RS");
        pessoaFisica.setLogradouro("Rua Cruz, 789");
        pessoaFisica.setTelefone("999545454");
      
        pessoaFisicaDAO.alterar(pessoaFisica);

        //6.c - Consultar todas as pessoas físicas do banco de dados e listar no console.
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
        System.out.println("--------------------------------");
        System.out.println("Pessoas Fisicas no Banco de Dados:");
        System.out.println("--------------------------------");
        for (PessoaFisica pf : pessoasFisicas) {
            pf.exibir();
            System.out.println("___________");
        }
        
        System.out.println("-----------------------");

        //6.d - Excluir a pessoa física criada anteriormente no banco
        pessoaFisicaDAO.excluir(pessoaFisica.getId());  
        
        //6.e - Instanciar uma pessoa jurídica e persistir no banco de dados
        PessoaJuridica pessoaJuridica = new PessoaJuridica(id, "Empresa Z", "Av. Cinco", "Praia", "RJ", "987654321", "pao@gmail.com", "12345678901234");

        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        pessoaJuridicaDAO.incluir(pessoaJuridica);

        //6.f - Alterar os dados da pessoa jurídica no banco
        pessoaJuridica.setNome("Padaria Pao Dourado");
        pessoaJuridica.setLogradouro("Rua Beira Mar, 910");
        pessoaJuridica.setCidade("Rainha do Mar");
        pessoaJuridica.setEstado("RS");
        pessoaJuridica.setTelefone("9988998899");
        pessoaJuridica.setEmail("paodourado@gmail.com");
        pessoaJuridica.setCnpj("000016789000014");
        
        pessoaJuridicaDAO.alterar(pessoaJuridica);

        //6.g - Consultar todas as pessoas jurídicas do banco de dados e listar no console
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
        System.out.println("--------------------------------");
        System.out.println("Pessoas Jurídicas no Banco de Dados:");
        System.out.println("--------------------------------");
        for (PessoaJuridica pj : pessoasJuridicas) {
            pj.exibir();
            System.out.println("___________");
        }

        //6.h - Excluir a pessoa jurídica criada anteriormente no banco
        pessoaJuridicaDAO.excluir(pessoaJuridica.getId());

    }
    
}
