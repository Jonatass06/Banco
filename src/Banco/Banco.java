package Banco;

import Conta.Conta;
import Pessoa.*;

import java.util.ArrayList;

public class Banco {

    private String nome;
    private String endereco;
    private int agencia;
    private ArrayList<Conta> contas;
    private double juros;
    private double taxaDeServico;

    public Banco(String nome, String endereco, int agencia, double juros, double taxaDeServico) {
        this.nome = nome;
        this.endereco = endereco;
        this.agencia = agencia;
        this.juros = juros;
        this.taxaDeServico = taxaDeServico;
        this.contas = new ArrayList<>();
    }

    public ArrayList<Conta> getContas() {
        return contas;
    }

    private void cadastrarConta(Conta conta){
        contas.add(conta);
    }

    public String getNome() {
        return nome;
    }

    public double getJuros() {
        return juros;
    }

    public Conta procurarConta(int numero){
        for(Conta conta : this.contas){
            if(conta.getNumero() == numero){
                return conta;
            }
        }
        return null;
    }

    public int getAgencia() {
        return agencia;
    }

    public boolean validarEntrada(Conta conta, String senha) {
        return conta.validaSenha(senha);
    }

    public Pessoa procurarPessoaFisica(long cpf){
        for(Conta conta : contas){
            if(conta.getTitular() instanceof Fisica
                    && ((Fisica) conta.getTitular()).getCpf() == cpf){
                return conta.getTitular();
            }
        }
        return null;
    }
    public Pessoa procurarPessoaJuridica(long cnpj){
        for(Conta conta : contas){
            if(conta.getTitular() instanceof Juridica
                    && ((Juridica) conta.getTitular()).getCnpj() == cnpj){
                return conta.getTitular();
            }
        }
        return null;
    }

}
