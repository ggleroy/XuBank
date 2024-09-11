import java.util.ArrayList;
import java.util.List;
/**
 * Classe que representa um cliente no sistema bancário.
 * Armazena informações básicas do cliente, como nome e CPF, e gerencia suas contas.
 */
public class Cliente {
    private final String nome;  // Nome do cliente
    private final String cpf;   // CPF do cliente, utilizado como identificador único
    private List<Conta> contas; // Lista de contas bancárias associadas ao cliente

    /**
     * Constrói um novo cliente com o nome e CPF fornecidos.
     * @param nome O nome completo do cliente.
     * @param cpf O CPF do cliente, que deve ser único.
     */
    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }

    /**
     * Adiciona uma nova conta à lista de contas do cliente.
     * @param conta A conta a ser adicionada ao cliente.
     */
    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    /**
     * Remove uma conta do cliente com base no número da conta.
     * @param numeroConta O número da conta a ser removida.
     * @return true se a conta foi removida com sucesso, false caso a conta não seja encontrada.
     */
    public boolean removerConta(int numeroConta) {
        return contas.removeIf(conta -> conta.getNumero() == numeroConta);
    }

    /**
     * Busca por uma conta específica entre as contas do cliente usando o número da conta.
     * @param numeroConta O número da conta a ser buscada.
     * @return A conta correspondente ao número fornecido, ou null se não for encontrada.
     */
    public Conta buscarConta(int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numeroConta) {
                return conta;
            }
        }
        return null;
    }

    /**
     * Calcula o saldo total combinado de todas as contas do cliente.
     * @return O saldo total de todas as contas do cliente.
     */
    public double calcularSaldoTotal() {
        double total = 0;
        for (Conta conta : contas) {
            total += conta.getSaldo();
        }
        return total;
    }

    /**
     * Fornece uma lista das contas bancárias do cliente.
     * @return Uma lista de contas do cliente.
     */
    public List<Conta> getContas() {
        return new ArrayList<>(contas); // Retorna uma cópia para prevenir modificações externas
    }

    /**
     * Retorna o nome do cliente.
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o CPF do cliente.
     * @return O CPF do cliente.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Imprime os extratos de todas as contas do cliente.
     * Esta função lista todas as transações para cada conta, incluindo depósitos e saques.
     */
    public void visualizarExtratos() {
        System.out.println("Extratos das contas de " + nome + ":");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        for (Conta conta : contas) {
            System.out.println("\n---- Conta Número: " + conta.getNumero() + " ----");
            List<String> transacoes = conta.getTransacoes();
            if (transacoes.isEmpty()) {
                System.out.println("Sem transações neste período.");
            } else {
                for (String transacao : transacoes) {
                    System.out.println(transacao);
                }
            }
        }
    }
}
