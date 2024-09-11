import java.util.ArrayList;
import java.util.List;
/**
 * Classe que representa um sistema bancário, responsável pela gestão de clientes e suas contas.
 * Oferece funcionalidades para adicionar clientes, buscar clientes por CPF, e calcular informações financeiras agregadas.
 */
public class SistemaBancario {
    private List<Cliente> clientes; // Lista de clientes no sistema bancário

    /**
     * Construtor que inicializa a lista de clientes do sistema bancário.
     */
    public SistemaBancario() {
        this.clientes = new ArrayList<>();
    }

    /**
     * Adiciona um novo cliente ao sistema bancário.
     * @param cliente O cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Busca um cliente pelo CPF. O CPF é limpo de caracteres não numéricos antes da busca.
     * @param cpf O CPF do cliente a ser buscado.
     * @return O cliente se encontrado; caso contrário, retorna null.
     */
    public Cliente buscarCliente(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", ""); // Remove tudo que não é dígito
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().replaceAll("[^0-9]", "").equals(cpfLimpo)) {
                return cliente;
            }
        }
        return null; // Retorna null se o cliente não for encontrado
    }
    
    /**
     * Retorna uma cópia da lista de clientes para evitar modificações externas diretamente na lista original.
     * @return Uma lista nova contendo todos os clientes.
     */
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Calcula o valor total em custódia para um tipo específico de conta em todo o sistema bancário.
     * @param tipoConta A classe do tipo de conta a ser calculado.
     * @return O valor total dos saldos para o tipo de conta especificado.
     */
    public double calcularCustodiaPorTipo(Class<? extends Conta> tipoConta) {
        double total = 0;
        for (Cliente cliente : clientes) {
            for (Conta conta : cliente.getContas()) {
                if (tipoConta.isInstance(conta)) {
                    total += conta.getSaldo();
                }
            }
        }
        return total;
    }    

    /**
     * Calcula o saldo médio das contas de todos os clientes.
     * @return O saldo médio ou zero se não houver contas.
     */
    public double calcularSaldoMedio() {
        double total = 0;
        int count = 0;
        for (Cliente cliente : clientes) {
            for (Conta conta : cliente.getContas()) {
                total += conta.getSaldo();
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }    

    /**
     * Identifica o cliente com o maior saldo total em todas as suas contas.
     * @return O cliente com o maior saldo; se não houver clientes, retorna null.
     */
    public Cliente clienteComMaiorSaldo() {
        Cliente clienteMaiorSaldo = null;
        double maiorSaldo = Double.MIN_VALUE;
        for (Cliente cliente : clientes) {
            double saldo = cliente.calcularSaldoTotal();
            if (saldo > maiorSaldo) {
                maiorSaldo = saldo;
                clienteMaiorSaldo = cliente;
            }
        }
        return clienteMaiorSaldo;
    }    

    /**
     * Identifica o cliente com o menor saldo total em todas as suas contas.
     * @return O cliente com o menor saldo; se não houver clientes, retorna null.
     */
    public Cliente clienteComMenorSaldo() {
        Cliente clienteMenorSaldo = null;
        double menorSaldo = Double.MAX_VALUE;
        for (Cliente cliente : clientes) {
            double saldo = cliente.calcularSaldoTotal();
            if (saldo < menorSaldo) {
                menorSaldo = saldo;
                clienteMenorSaldo = cliente;
            }
        }
        return clienteMenorSaldo;
    }
}
