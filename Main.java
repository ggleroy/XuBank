
import java.util.Scanner;

/**
 * Classe principal que contém o método main e lida com a interface do usuário
 * para operações bancárias.
 */
public class Main {

    private static final SistemaBancario banco = new SistemaBancario();  // Objeto central do sistema bancário
    private static final Scanner scanner = new Scanner(System.in);  // Scanner para entrada de dados do usuário

    /**
     * Método main que gerencia o menu principal do sistema bancário. Este
     * método oferece várias opções para interação com o sistema, como adicionar
     * clientes, abrir contas, realizar depósitos e saques, entre outras
     * operações financeiras.
     */
    public static void main(String[] args) {
        boolean executando = true; // Controle de loop para o menu
        while (executando) {
            System.out.println("\nBem-vindo ao XuBank. Escolha uma opção:");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Abrir Conta");
            System.out.println("3. Depositar");
            System.out.println("4. Sacar");
            System.out.println("5. Ver Saldo");
            System.out.println("6. Aplicar Rendimento");
            System.out.println("7. Cliente com Maior e Menor Saldo");
            System.out.println("8. Valor em Custódia por Tipo de Conta");
            System.out.println("9. Calcular Saldo Médio das Contas");
            System.out.println("10. Visualizar Extratos");
            System.out.println("0. Sair\n");

            int opcao = scanner.nextInt(); // Usuário escolhe uma opção
            scanner.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1 ->
                    cadastrarCliente(); // Cadastro de um novo cliente
                case 2 ->
                    abrirConta(); // Abertura de uma nova conta
                case 3 ->
                    realizarDeposito(); // Depósito em uma conta existente
                case 4 ->
                    realizarSaque(); // Saque de uma conta existente
                case 5 ->
                    verSaldo(); // Consulta o saldo de uma conta
                case 6 ->
                    aplicarRendimento(); // Aplica rendimento em uma conta rendável
                case 7 -> {
                    Cliente maior = banco.clienteComMaiorSaldo(); // Encontra o cliente com o maior saldo
                    Cliente menor = banco.clienteComMenorSaldo(); // Encontra o cliente com o menor saldo
                    System.out.println("Cliente com maior saldo: " + maior.getNome() + " - R$" + String.format("%.2f", maior.calcularSaldoTotal()));
                    System.out.println("Cliente com menor saldo: " + menor.getNome() + " - R$" + String.format("%.2f", menor.calcularSaldoTotal()));
                }
                case 8 -> {
                    System.out.println("Escolha o tipo de conta:\n1. Corrente\n2. Poupança\n3. Renda Fixa\n4. Investimento");
                    int tipo = scanner.nextInt();
                    double total = 0;
                    switch (tipo) {
                        case 1 ->
                            total = banco.calcularCustodiaPorTipo(ContaCorrente.class);
                        case 2 ->
                            total = banco.calcularCustodiaPorTipo(ContaPoupanca.class);
                        case 3 ->
                            total = banco.calcularCustodiaPorTipo(ContaRendaFixa.class);
                        case 4 ->
                            total = banco.calcularCustodiaPorTipo(ContaInvestimento.class);
                    }
                    System.out.println("Total em custódia para o tipo escolhido: R$" + String.format("%.2f", total));
                }
                case 9 -> {
                    double media = banco.calcularSaldoMedio(); // Calcula o saldo médio de todas as contas
                    System.out.println("Saldo médio das contas: R$" + String.format("%.2f", media));
                }
                case 10 ->
                    visualizarExtratos(); // Exibe extratos de contas de um cliente específico
                case 0 ->
                    executando = false; // Encerra o loop e o programa
                default ->
                    System.out.println("Opção inválida. Tente novamente."); // Trata opções inválidas
            }
        }
    }

    /**
     * Método para cadastrar um novo cliente no sistema bancário. Solicita ao
     * usuário que forneça o nome e CPF do cliente, cria um objeto Cliente e o
     * adiciona ao sistema.
     */
    private static void cadastrarCliente() {
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine(); // Recebe o nome do cliente do usuário
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.nextLine(); // Recebe o CPF do cliente do usuário
        Cliente cliente = new Cliente(nome, cpf); // Cria um novo objeto Cliente
        banco.adicionarCliente(cliente); // Adiciona o cliente ao banco
        System.out.println("Cliente cadastrado com sucesso."); // Confirmação do cadastro
    }

    /**
     * Método para abrir uma nova conta para um cliente existente. Solicita ao
     * usuário o CPF do cliente, o tipo de conta e o número da conta, e então
     * cria a conta correspondente adicionando-a ao cliente.
     */
    private static void abrirConta() {
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.nextLine(); // Recebe o CPF do cliente do usuário
        Cliente cliente = banco.buscarCliente(cpf); // Busca o cliente pelo CPF
        if (cliente == null) {
            System.out.println("Cliente não encontrado."); // Mensagem de erro se o cliente não for encontrado
            return;
        }
        System.out.println("Escolha o tipo de conta a ser aberta:\n1. Corrente\n2. Poupança\n3. Renda Fixa\n4. Investimento\n");
        int tipo = scanner.nextInt(); // Recebe o tipo de conta
        System.out.println("Digite o número da conta:");
        int numero = scanner.nextInt(); // Recebe o número da conta
        Conta conta;
        switch (tipo) {
            case 1 -> {
                System.out.println("Digite o limite de crédito:");
                double limite = scanner.nextDouble();
                conta = new ContaCorrente(numero, cliente, limite);
            }
            case 2 ->
                conta = new ContaPoupanca(numero, cliente);
            case 3 ->
                conta = new ContaRendaFixa(numero, cliente);
            case 4 ->
                conta = new ContaInvestimento(numero, cliente);
            default -> {
                System.out.println("Tipo de conta inválido.");
                return;
            }
        }
        cliente.adicionarConta(conta); // Adiciona a nova conta ao cliente
        System.out.println("Conta aberta com sucesso."); // Confirmação da abertura da conta
    }

    /**
     * Método para realizar um depósito em uma conta existente. Solicita ao
     * usuário o número da conta e o valor a ser depositado, e então efetua o
     * depósito se a conta for encontrada.
     */
    private static void realizarDeposito() {
        System.out.println("Digite o número da conta:");
        int numero = scanner.nextInt(); // Recebe o número da conta
        System.out.println("Digite o valor a ser depositado:");
        double valor = scanner.nextDouble(); // Recebe o valor a ser depositado
        for (Cliente cliente : banco.getClientes()) { // Itera sobre todos os clientes do banco
            for (Conta conta : cliente.getContas()) { // Itera sobre todas as contas do cliente
                if (conta.getNumero() == numero) {
                    conta.depositar(valor); // Realiza o depósito
                    System.out.println("Depósito realizado com sucesso. Saldo atual: R$" + String.format("%.2f", conta.getSaldo()));
                    return;
                }
            }
        }
        System.out.println("Conta não encontrada."); // Mensagem de erro se a conta não for encontrada
        }
    /**
     * Método para realizar um saque de uma conta específica.
     * Solicita ao usuário o número da conta e o valor a ser sacado, e realiza o saque se a conta for encontrada e o saldo for suficiente.
     */
    private static void realizarSaque() {
        System.out.println("Digite o número da conta:");
        int numero = scanner.nextInt(); // Recebe o número da conta do usuário
        System.out.println("Digite o valor a ser sacado:");
        double valor = scanner.nextDouble(); // Recebe o valor a ser sacado

        for (Cliente cliente : banco.getClientes()) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumero() == numero) {
                    conta.sacar(valor); // Realiza o saque
                    System.out.println("Saque realizado. Saldo atual: R$" + String.format("%.2f", conta.getSaldo()));
                    return;
                }
            }
        }
        System.out.println("Conta não encontrada ou saldo insuficiente."); // Informa ao usuário se a conta não for encontrada ou o saldo for insuficiente
    }

    /**
     * Método para verificar o saldo de uma conta específica.
     * Solicita ao usuário o número da conta e exibe o saldo se a conta for encontrada.
     */
    private static void verSaldo() {
        System.out.println("Digite o número da conta:");
        int numero = scanner.nextInt(); // Recebe o número da conta do usuário

        for (Cliente cliente : banco.getClientes()) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumero() == numero) {
                    System.out.println("Saldo da conta: R$" + String.format("%.2f", conta.getSaldo())); // Exibe o saldo da conta
                    return;
                }
            }
        }
        System.out.println("Conta não encontrada."); // Informa ao usuário se a conta não for encontrada
    }

    /**
     * Método para aplicar rendimento em uma conta rendável.
     * Solicita ao usuário o número da conta e aplica rendimento se a conta for rendável.
     */
    private static void aplicarRendimento() {
        System.out.println("Digite o número da conta para aplicar rendimento:");
        int numero = scanner.nextInt(); // Recebe o número da conta do usuário

        for (Cliente cliente : banco.getClientes()) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumero() == numero && conta instanceof IRentavel) {
                    ((IRentavel) conta).aplicarRendimento(); // Aplica rendimento se a conta for rendável
                    System.out.println("Rendimento aplicado. Saldo atual: R$" + String.format("%.2f", conta.getSaldo()));
                    return;
                }
            }
        }
        System.out.println("Conta não encontrada ou não é rendável."); // Informa ao usuário se a conta não for encontrada ou não for rendável
    }

    /**
     * Método para visualizar os extratos bancários de um cliente.
     * Solicita ao usuário o CPF do cliente e exibe os extratos de todas as suas contas se o cliente for encontrado.
     */
    private static void visualizarExtratos() {
        System.out.println("Digite o CPF do cliente para visualizar extratos:");
        String cpf = scanner.nextLine(); // Recebe o CPF do cliente do usuário
        Cliente cliente = banco.buscarCliente(cpf); // Busca o cliente pelo CPF

        if (cliente != null) {
            cliente.visualizarExtratos(); // Exibe os extratos das contas do cliente
        } else {
            System.out.println("Cliente não encontrado."); // Informa ao usuário se o cliente não for encontrado
        }
    }
}