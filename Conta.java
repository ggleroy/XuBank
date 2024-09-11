import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe abstrata que define a estrutura e o comportamento básicos de uma conta bancária.
 * Inclui operações comuns como depósitos e saques, além de manter um registro de transações.
 */
public abstract class Conta {
    private int numero; // Número da conta
    private double saldo; // Saldo atual da conta
    private Cliente cliente; // Cliente titular da conta
    private List<String> transacoes; // Lista de transações realizadas na conta
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Formatador de data para transações

    /**
     * Constrói uma conta bancária com número e cliente especificados.
     * @param numero O número da conta.
     * @param cliente O cliente titular desta conta.
     */
    public Conta(int numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 0.0; // Inicia com saldo zero
        this.transacoes = new ArrayList<>(); // Lista de transações inicialmente vazia
    }

    /**
     * Deposita um valor na conta, se o valor for positivo, e registra a transação.
     * @param valor O valor a ser depositado.
     */
    public void depositar(double valor) {
        if (valor < 0) {
            System.out.println("Digite um valor positivo.");
        } else {
            alterarSaldo(valor);
            registrarTransacao(valor, "Depósito");
        }
    }

    /**
     * Saca um valor da conta se houver saldo suficiente e o valor for positivo, e registra a transação.
     * @param valor O valor a ser sacado.
     */
    public void sacar(double valor) {
        if (valor < 0) {
            System.out.println("Operação inválida. Digite um valor do saque positivo.");
            return;
        }
        if (valor <= getSaldo()) {
            alterarSaldo(-valor);
            registrarTransacao(-valor, "Saque");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    /**
     * Modifica o saldo da conta.
     * Este método é protegido para que apenas a conta ou suas subclasses possam alterar o saldo diretamente.
     * @param valor O valor a ser adicionado ou subtraído do saldo.
     */
    protected void alterarSaldo(double valor) {
        this.saldo += valor;
    }

    /**
     * Registra uma transação na lista de transações da conta.
     * @param valor O valor da transação.
     * @param tipo O tipo de transação (por exemplo, "Depósito" ou "Saque").
     */
    private void registrarTransacao(double valor, String tipo) {
        LocalDateTime agora = LocalDateTime.now();
        String timestamp = agora.format(formatter);
        transacoes.add(String.format("%s: %s R$ %.2f | Saldo: R$ %.2f", timestamp, tipo, valor, getSaldo()));
    }

    /**
     * Retorna o saldo atual da conta.
     * @return O saldo da conta.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Retorna o número da conta.
     * @return O número da conta.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Retorna o cliente titular da conta.
     * @return O cliente associado a esta conta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna a lista de todas as transações realizadas na conta.
     * @return A lista de transações.
     */
    public List<String> getTransacoes() {
        return transacoes;
    }
}
