package Pessoa;

public class Juridica extends Pessoa {

    private long cnpj;
    private String razaoSocial;

    public Juridica(String endereco, long cnpj, String razaoSocial) {
        super(endereco);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
    }

    public long getCnpj() {
        return cnpj;
    }
}
