package Conta;

import Pessoa.Pessoa;

import java.util.ArrayList;

public class Poupanca extends Conta{

    public Poupanca(int numero, String senha, Pessoa titular, ArrayList<Conta> contas) {
        super(numero, senha, titular, contas);
    }

    public void saque(double valor){
        super.mudarSaldo(-valor);
        super.incQtdeTransacoes(1);
    }

    @Override
    public void pagamento(double valor){
        if (this.saldo() > valor){
            this.mudarSaldo(-valor);
        }
    }
    @Override
    public String mostrarOpcoes(){
        return """
                [1] - Pagar
                [2] - Creditar
                [3] - Verificar Informações
                [4] - Sacar
                """;
    }

    @Override
    public void aplicarJuros(int dias, double juros){
        mudarSaldo(saldo()*juros);
    }
}
