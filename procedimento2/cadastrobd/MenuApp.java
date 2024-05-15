package cadastrobd;

import cadastro.model.util.SequenceManager;
import cadastrodb.model.PessoaFisica;
import cadastrodb.model.PessoaFisicaDAO;
import cadastrodb.model.PessoaJuridica;
import cadastrodb.model.PessoaJuridicaDAO;
import java.util.List;
import java.util.Scanner;


public class MenuApp {
    Scanner scanner = new Scanner(System.in);
    SequenceManager sequenceManager = new SequenceManager();
   
    PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();  
    PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
    
    private int opcao, id;
    private String tipo;
    
    private String tipoOpcao = "Escolha o tipo (F - Pessoa Fisica, J - Pessoa Juridica):";

    public MenuApp() { }

    public void exibirMenu() {
        do {
            System.out.println("===============================================");
            System.out.println("1 - Incluir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Buscar pelo ID");
            System.out.println("5 - Exibir todos");
            System.out.println("");
            System.out.println("0 - Sair");
            System.out.println("===============================================");
            
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    this.cadastrarPessoa();
                    break;
                case 2:
                    this.alterarPessoa();
                    break;
                case 3:
                    this.exlcuirPessoa();
                    break;
                case 4:
                    this.buscarPorID();
                    break;
                case 5:
                    this.exibirTodos();
                    break;
                case 0:
                    System.out.println("Finalizando app...");
                    System.out.println("Realizado por Fernando Ely");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }
    
    // Metodo para deixar os campos obrigarios.
    private String lerInputUsuario(Scanner scanner){
        String texto;
        do {
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Campo obrigatorio, favor preencher!");
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Métodos para cada opção do menu
    private void cadastrarPessoa() {
        System.out.println(tipoOpcao);
        String tipo = scanner.nextLine().toLowerCase();
        if ("f".equals(tipo)) {
            System.out.println("Informe o nome:");
            String nome = lerInputUsuario(scanner);
            System.out.println("Informe o logradouro:");
            String logradouro = lerInputUsuario(scanner);
            System.out.println("Informe o cidade:");
            String cidade = lerInputUsuario(scanner);
            System.out.println("Informe o estado (sigla):");
            String estado = lerInputUsuario(scanner);
            System.out.println("Informe o telefone:");
            String telefone = lerInputUsuario(scanner);
            System.out.println("Informe o email:");
            String email = lerInputUsuario(scanner);
            System.out.println("Informe o CPF:");
            String cpf = lerInputUsuario(scanner);
                     
            id = sequenceManager.getValue("pessoa_id_seq");
            
            PessoaFisica pessoaFisica = new PessoaFisica(id, nome, logradouro, cidade, estado, telefone, email, cpf);

            pessoaFisicaDAO.incluir(pessoaFisica);

            System.out.println("Pessoa fisica incluida com sucesso.");

        } else if ("j".equals(tipo)) {
           System.out.println("Informe o nome:");
            scanner.useDelimiter("\n");
            String nome = lerInputUsuario(scanner);
            System.out.println("Informe o logradouro:");
            String logradouro = lerInputUsuario(scanner);
            System.out.println("Informe o cidade:");
            String cidade = lerInputUsuario(scanner);
            System.out.println("Informe o estado (sigla):");
            String estado = lerInputUsuario(scanner);
            System.out.println("Informe o telefone:");
            String telefone = lerInputUsuario(scanner);
            System.out.println("Informe o email:");
            String email = lerInputUsuario(scanner);
            System.out.println("Informe o cnpj:");
            String cnpj = lerInputUsuario(scanner);
            
            id = sequenceManager.getValue("pessoa_id_seq");

            PessoaJuridica pessoaJuridica = new PessoaJuridica(id, nome, logradouro, cidade, estado, telefone, email, cnpj);

            pessoaJuridicaDAO.incluir(pessoaJuridica);

            System.out.println("Pessoa juridica incluida com sucesso.");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void alterarPessoa() {
        System.out.println(tipoOpcao);
        tipo = scanner.nextLine().toLowerCase();
        if ("f".equals(tipo)) {
            System.out.println("Escolha o ID");
            id = Integer.parseInt(scanner.nextLine());

            PessoaFisica pf = pessoaFisicaDAO.getPessoa(id);
            
            if(pf != null){
                pf.exibir();

                System.out.println("");

                System.out.println("Informe o nome:");
                String nome = lerInputUsuario(scanner);
                System.out.println("Informe o logradouro:");
                String logradouro = lerInputUsuario(scanner);
                System.out.println("Informe o cidade:");
                String cidade = lerInputUsuario(scanner);
                System.out.println("Informe o estado (sigla):");
                String estado = lerInputUsuario(scanner);
                System.out.println("Informe o telefone:");
                String telefone = lerInputUsuario(scanner);
                System.out.println("Informe o email:");
                String email = lerInputUsuario(scanner);
                System.out.println("Informe o CPF:");
                String cpf = lerInputUsuario(scanner);

                pf.setNome(nome);
                pf.setLogradouro(logradouro);
                pf.setCidade(cidade);
                pf.setEstado(estado);
                pf.setTelefone(telefone);
                pf.setEmail(email);
                pf.setCpf(cpf);

                pessoaFisicaDAO.alterar(pf);

                System.out.println("Pessoa Fisica alterada com sucesso.");
                
                System.out.println("\nPressione Enter para mostrar o menu novamente...");
                scanner.nextLine();
            } else {
                System.out.println("ID nao encontrado");
            }     
         
        } else if ("j".equals(tipo)) {
            System.out.println("Escolha o ID");
            id = Integer.parseInt(scanner.nextLine());

            PessoaJuridica pj = pessoaJuridicaDAO.getPessoa(id);
            
            if(pj != null) {
                pj.exibir();
                
                System.out.println("");

                System.out.println("Informe o nome:");
                String nome = lerInputUsuario(scanner);
                System.out.println("Informe o logradouro:");
                String logradouro = lerInputUsuario(scanner);
                System.out.println("Informe o cidade:");
                String cidade = lerInputUsuario(scanner);
                System.out.println("Informe o estado (sigla):");
                String estado = lerInputUsuario(scanner);
                System.out.println("Informe o telefone:");
                String telefone = lerInputUsuario(scanner);
                System.out.println("Informe o email:");
                String email = lerInputUsuario(scanner);
                System.out.println("Informe o cnpj:");
                String cnpj = lerInputUsuario(scanner);

                pj.setNome(nome);
                pj.setLogradouro(logradouro);
                pj.setCidade(cidade);
                pj.setEstado(estado);
                pj.setTelefone(telefone);
                pj.setEmail(email);
                pj.setCnpj(cnpj);

                pessoaJuridicaDAO.alterar(pj);
                System.out.println("Pessoa Juridica alterada com sucesso.");

                System.out.println("\nPressione Enter para mostrar o menu novamente...");
                scanner.nextLine();
                
            } else {
                System.out.println("ID nao encontrado");
            }

        } else {
            System.out.println("Opção inválida.");
        }
    }
    
    private void exlcuirPessoa() {
        System.out.println(tipoOpcao);
        tipo = scanner.nextLine().toLowerCase();
         if ("f".equals(tipo)) {
             System.out.println("Digite o ID: ");
             id = Integer.parseInt(scanner.nextLine());
             
             PessoaFisica pf = pessoaFisicaDAO.getPessoa(id);
             
             if(pf != null){
                pessoaFisicaDAO.excluir(id);
             } else {
                System.out.println("ID nao encontrado!");
             }
             
             
         } else if ("j".equals(tipo)) {
            System.out.println("Digite o ID: ");
            id = Integer.parseInt(scanner.nextLine());
            PessoaJuridica pj = pessoaJuridicaDAO.getPessoa(id);
             
             if(pj != null){
                pessoaFisicaDAO.excluir(id);
             } else {
                System.out.println("ID nao encontrado!");
             }

         } else {
            System.out.println("Opção inválida.");
         }
    }
    
    private void buscarPorID(){
        System.out.println(tipoOpcao);
        tipo = scanner.nextLine().toLowerCase();
        
        if ("f".equals(tipo)) {
            System.out.println("Digite o ID: ");
            id = Integer.parseInt(scanner.nextLine());
            PessoaFisica pf = pessoaFisicaDAO.getPessoa(id);
            
            if(pf != null) {
                pf.exibir();
            } else {
                System.out.println("ID nao encontrado");
            }
            
            System.out.println("\nPressione Enter para mostrar o menu novamente...");
            scanner.nextLine();

         } else if ("j".equals(tipo)) {
            System.out.println("Digite o ID: ");
            id = Integer.parseInt(scanner.nextLine());
            PessoaJuridica pj = pessoaJuridicaDAO.getPessoa(id);
            
            if(pj != null) {
                pj.exibir();
            } else {
                System.out.println("Registro nao encontrado");
            }
            
            System.out.println("\nPressione Enter para mostrar o menu novamente...");
            scanner.nextLine();
         } else {
            System.out.println("Opção inválida.");
         }
    }
    
    private void exibirTodos(){
        System.out.println(tipoOpcao);
        tipo = scanner.nextLine().toLowerCase();
 
         if ("f".equals(tipo)) {
            List<PessoaFisica> pessoasFisicas = pessoaFisicaDAO.getPessoas();
            System.out.println("--------------------------------");
            System.out.println("Pessoas Fisicas no Banco de Dados:");
            System.out.println("--------------------------------");
            for (PessoaFisica pf : pessoasFisicas) {
                pf.exibir();
                System.out.println("___________");
            }
         } else if ("j".equals(tipo)) {
            List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDAO.getPessoas();
            System.out.println("--------------------------------");
            System.out.println("Pessoas Jurídicas no Banco de Dados:");
            System.out.println("--------------------------------");
            for (PessoaJuridica pj : pessoasJuridicas) {
                pj.exibir();
                System.out.println("___________");
            }
         } else {
             System.out.println("Opção inválida.");
         }
    }
    
}
