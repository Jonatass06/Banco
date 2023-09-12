package Conta;

import Pessoa.Pessoa;
import Banco.Banco;

import java.util.ArrayList;

public class Conta {

    private int numero;
    private double saldo;
    private int qtdTransacoes;
    private String senha;
    private Pessoa titular;

    public Conta(int numero, String senha, Pessoa titular, ArrayList<Conta> contas) {
        this.numero = contas.get(contas.size()-1).numero +1;
        this.senha = senha;
        this.titular = titular;
        this.saldo = 0;
        this.qtdTransacoes = 0;
    }

    public void credito(double valor){
        this.saldo += +valor;
    }
    public void pagamento(double valor){
        saldo+=valor;
    }
    public double saldo(){return this.saldo;}

    public void mudarSaldo(double valor){
        this.saldo += valor;
    }

    public void aplicarJuros(int dias, double juros){}

    public int getNumero() {
        return numero;
    }
    public String mostrarOpcoes(){return "";}

    public boolean validaSenha(String senha) {
        return this.senha.equals(senha);
    }

    public Pessoa getTitular() {
        return titular;
    }

    public int getQtdTransacoes() {
        return qtdTransacoes;
    }

    public void incQtdeTransacoes(int i) {
        this.qtdTransacoes+=i;
    }

}
