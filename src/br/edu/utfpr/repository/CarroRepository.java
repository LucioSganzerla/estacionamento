package br.edu.utfpr.repository;

import br.edu.utfpr.model.Carro;
import br.edu.utfpr.statement.CarroStatement;

public class CarroRepository extends RepositoryImpl<CarroStatement, Carro> {

    @Override
    public CarroStatement getInstanceOfT() {
        return new CarroStatement();
    }

    public Carro findByPlaca(String placa) {
        return getInstanceOfT().findByPlaca(placa);
    }

}
