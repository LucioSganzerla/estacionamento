package br.edu.utfpr.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vaga implements Model {

    private Long id;
    private Long id_carro;
    private Timestamp datain;
    private Timestamp dataout;

    public Vaga(Long id_carro) {
        this.id_carro = id_carro;
    }

}
