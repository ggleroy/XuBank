/**
 * Classe ContaPoupanca que herda de Conta e implementa a interface IRentavel.
 * Esta classe representa uma conta poupança que acumula juros com base em uma taxa de rendimento mensal fixa.
 */
public class ContaPoupanca extends Conta implements IRentavel{
    private static final double TAXA_RENDIMENTO_MENSAL = 0.006; // Taxa de rendimento mensal fixa de 0.6%.

    /**
     * Construtor para criar uma conta poupança.
     * @param numero O número da conta.
     * @param cliente O cliente titular da conta.
     */
    public ContaPoupanca(int numero, Cliente cliente) {
        super(numero, cliente);
    }

    /**
     * Aplica rendimento mensal à conta com base na taxa de rendimento mensal definida.
     * O rendimento é calculado como um percentual do saldo atual e adicionado ao mesmo.
     */
    @Override
    public void aplicarRendimento() {
        double rendimento = getSaldo() * TAXA_RENDIMENTO_MENSAL; // Calcula o rendimento com base no saldo atual
        alterarSaldo(rendimento);  // Adiciona o rendimento ao saldo
        System.out.println("Rendimento de R$" + String.format("%.2f", rendimento) + " aplicado ao saldo.");
    }
}
