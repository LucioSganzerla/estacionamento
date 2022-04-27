package br.edu.utfpr.model;

public enum TipoVeiculo {

    CARRO("CARRO"),
    MOTO("MOTO");

    private final String descricao;

    private TipoVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public static String toString(TipoVeiculo tipoVeiculo) {
        switch (tipoVeiculo) {
            case CARRO:
                return "CARRO";
            case MOTO:
                return "MOTO";
            default:
                return "";
        }
    }

    public static TipoVeiculo castString(String tipoVeiculo) {
        switch (tipoVeiculo) {
            case "CARRO":
                return CARRO;
            case "MOTO":
                return MOTO;
            default:
                return null;
        }
    }

    public String getDescricao() {
        return descricao;
    }
}
