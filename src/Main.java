import Banco.Banco;
import Conta.Poupanca;
import Pessoa.*;
import Conta.*;

import java.util.Scanner;

public class Main {

    static Banco banco = new Banco("NuBanco", "Rua das Palmeiras - 27", 2727, 0.06, 6);
    static Pessoa user = new Fisica("1", 1, "1");
    static Conta poupanca = new Poupanca(1, "1", user, banco.getContas());
    static Conta corrente = new Corrente(1, "1", user, 50, banco.getContas());

    static Conta contaLogada = null;
    static Scanner sc = new Scanner(System.in);
    static int dia = 1;

    public static void main(String[] args) {

        menuPrincipal();

//            criarConta();
//            logarPessoa();
//            criarPessoa();

    }

    private static void menuPrincipal() {
        System.out.println("Bem vindo ao " + banco.getNome());
        int opcao;
        do {
            System.out.println("""
                    [0] - Criar Conta
                    [1] - Logar com Conta
                    [2] - Passar dias
                    [3] - Sair
                    """);
            opcao = sc.nextInt();

            switch (opcao) {
                case 0 -> menuCriarConta();
                case 1 -> logarConta();
                case 2 -> passarDia();
                case 3 -> System.out.println("Adeus! Volte Sempre!");
                default -> System.out.println("Valor Inválido");
            }
        } while (opcao != 4);
    }

    private static void menuCriarConta() {
        int opcao;
        Pessoa pessoa;
        do {
            System.out.println("""
                    A pessoa já esta cadastrada no sistema?
                                 [0] - Sim
                                 [1] - Não                    
                    """);
            opcao = sc.nextInt();
            pessoa = switch(opcao){
                case 0 -> logarPessoa();
                case 1 -> cadastrarPessoa();
                default -> System.out.println("Valor inválido!");
            };
        } while(pessoa == null);
    }

    private static Pessoa cadastrarPessoa() {
        Pessoa pessoa;
        do{
            System.out.println("""
                    Voce deseja cadastrar uma pessoa...
                            [0] Fisica
                           [1] Juridica
                    """);
            int opcao = sc.nextInt();
            pessoa = switch (opcao){
                case 0 -> cadastrarFisica();
                case 1 -> cadastrarJuridica();
                default -> System.out.println("Valor Inválido");;
            };
        }while(pessoa == null);
        return pessoa;
    }

    private static Pessoa cadastrarFisica() {
        long cpf;
        String endereco;
        String nome;


        do{
            System.out.println("Qual o seu cpf?");
            cpf = sc.nextLong();
            if (banco.procurarPessoaFisica(cpf) == null) {
                System.out.println("Qual o seu endereco?");
                endereco = sc.nextLine();
                System.out.println("Qual o seu nome?");
                nome = sc.nextLine();
                return new Fisica(endereco, cpf, nome);
            }else{
                System.out.println("Essa pessoa já existe");
            }
        }while(true);
    }
    private static Pessoa cadastrarJuridica() {
        long cnpj;
        String endereco;
        String razaoSocial;
        do{
            System.out.println("Qual o seu cnpj?");
            cnpj = sc.nextLong();
            if (banco.procurarPessoaJuridica(cnpj) == null) {
                System.out.println("Qual o seu endereco?");
                endereco = sc.nextLine();
                System.out.println("Qual a sua razão social?");
                razaoSocial = sc.nextLine();
                return new Juridica(endereco, cnpj, razaoSocial);
            }else{
                System.out.println("Essa pessoa já existe");
            }
        }while(true);
    }

    private static void logarConta() {

        Conta conta;
        String senha;

        do {
            System.out.println("Qual o numero da conta: ");
            conta = banco.procurarConta(sc.nextInt());
            if (conta == null) {
                System.out.println("Essa conta não existe!");
            } else {
                System.out.println("Qual a senha da conta: ");
                if (banco.validarEntrada(conta, sc.next())) {
                    menuConta();
                }
            }
        } while (conta == null);
    }

    private static void menuConta() {
        String complemento = contaLogada instanceof Credito ? " Devedor: " : ": ";
        int opcao;
        do {
            System.out.println("Saldo" + complemento + contaLogada.saldo());
            System.out.println("[0] - Sair\n" + contaLogada.mostrarOpcoes());
            opcao = sc.nextInt();
            switch (opcao) {
                case 0 -> System.out.println("Volte Sempre!");
                case 1 -> pagamento();
                case 2 -> creditar();
                case 3 -> verificarInformações();
                case 4 -> {
                    if (!(contaLogada instanceof Credito)) {
                        sacar();
                    } else {
                        System.out.println("Valor inválido!");
                    }
                }
                case 5 -> {
                    if (contaLogada instanceof Corrente) {
                        transferir();
                    } else {
                        System.out.println("Valor inválido!");
                    }
                }
                default -> System.out.println("Valor inválido!");
            }
        } while (opcao != 0);
    }

    private static void passarDia() {
        int diasPedidos;
        do {
            System.out.println("Quantos dias voce quer passar?");
            diasPedidos = sc.nextInt();

            if (diasPedidos < 0) {
                System.out.println("Voce não pode voltar no tempo!");
            }
        } while (diasPedidos < 0);
        aplicarOsJuros(diasPedidos);

    }

    private static void aplicarOsJuros(int diasPedidos) {

        for (int i = 0; i < diasPedidos; i++) {
            dia = dia == 30 ? 1 : dia + 1;
            for (Conta conta : banco.getContas()) {
                conta.aplicarJuros(dia, banco.getJuros());
            }
        }
    }

    private static void creditar() {
        double valor;
        do {
            if (contaLogada instanceof Credito) {
                System.out.println("O quanto da fatura você deseja pagar? R$");
            } else {
                System.out.println("Quanto voce deseja depositar? R$");
            }

            valor = sc.nextDouble();

            if (valor < 0) {
                System.out.println("Insira um valor positivo!");
            } else {
                contaLogada.credito(valor);
            }
        } while (valor < 0);
    }

    private static void pagamento() {
        double valorPago;
        do {
            System.out.println("Quanto custou seu pagamento? R$");
            valorPago = sc.nextDouble();

            if (valorPago < 0) {
                System.out.println("Insira um valor positivo!");
            } else {
                contaLogada.pagamento(valorPago);
            }
        } while (valorPago < 0);
    }

    private static void verificarInformações() {
        System.out.println("Numero: " + contaLogada.getNumero());
        System.out.println("Agencia: " + banco.getAgencia());
        System.out.println("Saldo: " + contaLogada.saldo());
        System.out.println("Titular: " + contaLogada.getTitular());
        System.out.println("Quantidade de Transações: " + contaLogada.getQtdTransacoes());


        if (contaLogada instanceof Corrente) {
            System.out.println("Limite: " + ((Corrente) contaLogada).getLimite());

        } else if (contaLogada instanceof Credito) {
            Credito credito = (Credito) contaLogada;
            System.out.println("Limite: " + credito.getLimite());
            System.out.println("Dia da Fatura: " + credito.getDiaFatura());
            System.out.println("Status da Fatura: " + (credito.estaPaga() ? "Em dia" : "Atrasada"));
        }
    }

    private static void sacar() {
        double valor;
        do {
            System.out.println("Quanto voce quer sacar? R$");
            valor = sc.nextDouble();

            if (valor < 0) {
                System.out.println("Insira um valor positivo!");
            } else {
                if (contaLogada instanceof Poupanca) {
                    ((Poupanca) contaLogada).saque(valor);
                } else {
                    ((Corrente) contaLogada).saque(valor);
                }
            }
        } while (valor < 0);
    }

    private static void transferir() {
        Conta conta;
        double valor;

        do {
            System.out.println("Qual o numero da conta: ");
            conta = banco.procurarConta(sc.nextInt());
            if (conta == null) {
                System.out.println("Essa conta não existe!");
            } else if (!(conta instanceof Corrente)) {
                System.out.println("Você não pode transferir para essa conta!");
            }
        } while (conta == null);

        do {
            System.out.println("Quanto você deseja transferir? R$");
            valor = sc.nextDouble();

            if (valor < 0) {
                System.out.println("Insira um valor positivo!");
            } else {
                ((Corrente) contaLogada).transferencia(valor, conta);
            }
        } while (valor < 0);
    }


}
