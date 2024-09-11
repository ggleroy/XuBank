/**
 * Classe ContaCorrente que herda de Conta e representa uma conta corrente bancária.
 * Inclui funcionalidades específicas como um limite de crédito e taxas especiais para depósitos quando o saldo é negativo.
 */
public class ContaCorrente extends Conta {
    private final double limiteCredito; // Limite de crédito permitido além do saldo disponível

    /**
     * Construtor para criar uma conta corrente.
     * @param numero O número da conta.
     * @param cliente O cliente titular da conta.
     * @param limiteCredito O limite de crédito disponível além do saldo.
     */
    public ContaCorrente(int numero, Cliente cliente, double limiteCredito) {
        super(numero, cliente);
        this.limiteCredito = limiteCredito;
    }

    /**
     * Deposita um valor na conta, aplicando uma taxa se o saldo atual for negativo.
     * @param valor O valor a ser depositado.
     */
    @Override
    public void depositar(double valor) {
        if (getSaldo() < 0) {
            // Se o saldo é negativo, calcula e aplica uma taxa antes de adicionar o valor do depósito ao saldo.
            double taxa = -0.03 * getSaldo() + 10; // Taxa baseada no saldo negativo mais uma taxa fixa de 10
            alterarSaldo(valor - taxa); // Aplica a taxa e atualiza o saldo
        } else {
            alterarSaldo(valor); // Se o saldo não é negativo, adiciona o valor diretamente ao saldo
        }
    }

    /**
     * Realiza um saque da conta, verificando se o valor não excede o saldo mais o limite de crédito.
     * @param valor O valor a ser sacado.
     */
    @Override
    public void sacar(double valor) {
        // Verifica se o valor de saque é permitido considerando o saldo atual e o limite de crédito.
        if (valor <= getSaldo() + limiteCredito) {
            alterarSaldo(-valor); // Subtrai o valor do saldo
        } else {
            System.out.println("Saldo insuficiente."); // Informa ao usuário que o saldo mais o crédito não cobrem o saque
        }
    }
}
