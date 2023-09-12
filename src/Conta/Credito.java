package Conta;

import Pessoa.Pessoa;

import java.util.ArrayList;

public class Credito extends Conta{

    private int diaFatura;
    private boolean faturaPaga;
    private double limite;

    public Credito(int numero, String senha, Pessoa titular, int diaFatura, double limite, ArrayList<Conta> contas) {
        super(numero, senha, titular, contas);
        this.diaFatura = diaFatura;
        this.limite = limite;
        this.faturaPaga = true;
    }

    @Override
    public void pagamento(double valor){
        if((super.saldo() + valor) <= limite){
            super.mudarSaldo(+valor);
        }
    }
    @Override
    public String mostrarOpcoes(){
        return """
                [1] - Pagar
                [2] - Creditar
                [3] - Verificar Informações
                """;
    }
    @Override
    public void credito(double valor){

        if(saldo() - valor >= 0){
            super.mudarSaldo(-valor);
        }
        if (saldo() == 0){
            faturaPaga = true;
        }
    }
    @Override
    public void aplicarJuros(int dias, double juros){
        if(dias == diaFatura && saldo() > 0){
            faturaPaga = false;
        }
        if(!faturaPaga){
            mudarSaldo(saldo()*juros);
        }
    }

    public double getLimite() {
        return limite;
    }

    public int getDiaFatura() {
        return diaFatura;
    }

    public boolean estaPaga() {
        return faturaPaga;
    }
}
