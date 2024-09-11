import java.util.Random;
/**
 * Classe ContaInvestimento que herda de Conta e implementa a interface IRentavel.
 * Esta classe é especializada em operações de investimento, incluindo a aplicação de rendimentos com base em taxas variáveis
 * e a imposição de impostos sobre os saques quando houver ganhos.
 */
public class ContaInvestimento extends Conta implements IRentavel {
    private static final double TAXA_MIN = -0.006;  // Taxa mínima de rendimento, podendo ser negativa (-0.60%).
    private static final double TAXA_MAX = 0.015;   // Taxa máxima de rendimento (+1.50%).
    private static final double IMPOSTO_RENDIMENTO = 0.225; // Imposto sobre o rendimento de 22.5%.

    /**
     * Construtor para criar uma conta de investimento.
     * @param numero O número da conta.
     * @param cliente O cliente titular da conta.
     */
    public ContaInvestimento(int numero, Cliente cliente) {
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
     * Realiza um saque da conta, aplicando imposto sobre o montante sacado se houver rendimentos.
     * @param valor O valor a ser sacado.
     */
    @Override
    public void sacar(double valor) {
        if (valor < 0) {
            System.out.println("Operação inválida. Digite um valor de saque positivo.");
            return;
        }
        if (valor <= getSaldo()) {
            double imposto = valor * IMPOSTO_RENDIMENTO;
            alterarSaldo(-(valor + imposto));
            System.out.println("Saque de R$" + String.format("%.2f", valor) + 
                               " realizado com sucesso. Imposto de R$" + 
                               String.format("%.2f", imposto) + 
                               " sobre o rendimento incluído.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    /**
     * Aplica rendimento à conta baseado em uma taxa aleatória entre TAXA_MIN e TAXA_MAX.
     * Rendimentos positivos também sofrem uma dedução de uma taxa de gestão de 1%.
     */
    @Override
    public void aplicarRendimento() {
        Random rand = new Random();
        double taxaRendimento = TAXA_MIN + (TAXA_MAX - TAXA_MIN) * rand.nextDouble();
        double rendimento = getSaldo() * taxaRendimento;
        double taxaGestao = rendimento > 0 ? rendimento * 0.01 : 0;
        alterarSaldo(rendimento - taxaGestao);
        System.out.println("Rendimento de R$" + String.format("%.2f", rendimento) + 
                           " aplicado ao saldo, taxa de gestão de R$" + 
                           String.format("%.2f", taxaGestao) + " deduzida.");
    }
}
