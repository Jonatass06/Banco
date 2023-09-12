package Conta;

import Pessoa.Pessoa;
import java.util.ArrayList;

public class Corrente extends Conta{

    private double limite;

    public Corrente(int numero, String senha, Pessoa titular, double limite, ArrayList<Conta> contas) {
        super(numero, senha, titular, contas);
        this.limite = limite;
    }

    public void saque(double valor){
        super.mudarSaldo(-valor);
        super.incQtdeTransacoes(1);
    }

    public void transferencia(double valor, Conta conta){
        if(super.saldo() - valor > -this.limite){
            this.pagamento(valor);
            conta.credito(valor);
            super.incQtdeTransacoes(1);
        }
    }

    @Override
    public void pagamento( double valor){
        if(super.saldo() - valor > -limite){
            mudarSaldo(-valor);
        }
    }
    //Corrente - pagar, creditar, verificarInformações, saca, Transferir
    @Override
    public String mostrarOpcoes(){
        return """
                [1] - Pagar
                [2] - Creditar
                [3] - Verificar Informações
                [4] - Sacar
                [5] - Transferir
                """;
    }
    @Override
    public void aplicarJuros(int dias, double juros){
        if(saldo() < 0){
            mudarSaldo(-(saldo()*juros));
        }
    }

    public double getLimite() {
        return limite;
    }
}
