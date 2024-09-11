import java.util.Random;
/**
 * Classe ContaRendaFixa que herda de Conta e implementa a interface IRentavel.
 * Esta classe representa uma conta de investimento de renda fixa, que acumula rendimentos
 * com base em uma taxa variável e cobra uma taxa administrativa fixa mensal.
 */
public class ContaRendaFixa extends Conta implements IRentavel {
    private static final double TAXA_MIN = 0.005;  // Taxa de rendimento mínima de 0.5%.
    private static final double TAXA_MAX = 0.0085; // Taxa de rendimento máxima de 0.85%.
    private static final double IMPOSTO_SOBRE_RENDIMENTO = 0.15; // Imposto sobre rendimento de 15%.
    private static final double TAXA_ADMINISTRATIVA = 20; // Taxa administrativa de R$20 mensal.

    /**
     * Construtor para criar uma conta de renda fixa.
     * @param numero O número da conta.
     * @param cliente O cliente titular da conta.
     */
    public ContaRendaFixa(int numero, Cliente cliente) {
        super(numero, cliente);
    }

    /**
     * Deposita um valor na conta, verificando se o valor é positivo.
     * @param valor O valor a ser depositado.
     */
    @Override
    public void depositar(double valor) {
        if (valor < 0) {
            System.out.println("Digite um valor positivo.");
        } else {
            alterarSaldo(valor);
            System.out.println("Depósito de R$" + String.format("%.2f", valor) + " realizado com sucesso.");
        }
    }

    /**
     * Realiza um saque da conta, verificando se o saldo é suficiente para o saque desejado.
     * @param valor O valor a ser sacado.
     */
    @Override
    public void sacar(double valor) {
        if (valor < 0) {
            System.out.println("Operação inválida. Digite um valor de saque positivo.");
            return;
        }
        if (valor <= getSaldo()) {
            alterarSaldo(-valor);
            System.out.println("Saque de R$" + String.format("%.2f", valor) + " realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    /**
     * Aplica rendimento à conta com base em uma taxa aleatória entre TAXA_MIN e TAXA_MAX, menos uma taxa administrativa.
     */
    @Override
    public void aplicarRendimento() {
        Random rand = new Random();
        double taxaRendimento = TAXA_MIN + (TAXA_MAX - TAXA_MIN) * rand.nextDouble();
        double rendimento = getSaldo() * taxaRendimento;
        alterarSaldo(rendimento - TAXA_ADMINISTRATIVA); // Desconta a taxa administrativa do rendimento
        System.out.println("Rendimento de R$" + String.format("%.2f", rendimento) +
                           " aplicado ao saldo após deduzir taxa administrativa de R$" + 
                           String.format("%.2f", TAXA_ADMINISTRATIVA) + ".");
    }

    /**
     * Calcula e exibe o imposto a ser pago sobre um montante especificado em caso de saque.
     * @param montante O valor do saque para o qual o imposto é calculado.
     * @return O valor do imposto calculado.
     */
    public double calcularImpostoNoSaque(double montante) {
        double imposto = montante * IMPOSTO_SOBRE_RENDIMENTO;
        System.out.println("Imposto de R$" + String.format("%.2f", imposto) + " calculado sobre o saque.");
        return imposto;
    }
}
