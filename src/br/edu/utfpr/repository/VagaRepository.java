package br.edu.utfpr.repository;

import br.edu.utfpr.model.Vaga;
import br.edu.utfpr.statement.VagaStatement;

import java.util.List;

public class VagaRepository extends RepositoryImpl<VagaStatement, Vaga> {

    @Override
    public VagaStatement getInstanceOfT() {
        return new VagaStatement();
    }

    public Vaga findById(Long id) {
        return getInstanceOfT().findByID(id);
    }

    public Vaga findByCarro(Long idCarro) {
        return getInstanceOfT().findVagaByCarro(idCarro);
    }

    public List<Vaga> findAllOcupadas() {
        return getInstanceOfT().findAllOcupadas();
    }
}
