package br.edu.utfpr.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Carro implements Model {

    private Long id;
    private String placa;
    private TipoVeiculo tipo;

    public Carro(String placa, TipoVeiculo tipo) {
        this.placa = placa;
        this.tipo = tipo;
    }


}
