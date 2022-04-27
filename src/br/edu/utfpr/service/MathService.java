package br.edu.utfpr.service;

import br.edu.utfpr.Constants;
import br.edu.utfpr.model.Carro;

import java.sql.Timestamp;

public class MathService {

    public double calcularTempo(Timestamp datain, Timestamp dataout) {
        long diff = dataout.getTime() - datain.getTime();
        return (double) diff / (60 * 1000);
    }

    public double calculaValor(Carro carro, double minutos) {
        switch (carro.getTipo()) {
            case MOTO:
                return (minutos * Constants.PRICE_PER_MINUTE) * 0.5;
            default:
                return minutos * Constants.PRICE_PER_MINUTE;
        }
    }

    public long getMathRandom(double min, double max) {
        return (long) ((Math.random() * (max - min)) + min);
    }

}
