package br.edu.utfpr.statement;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.model.Carro;
import br.edu.utfpr.model.TipoVeiculo;
import br.edu.utfpr.model.Vaga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CarroStatement implements Statement<Carro> {

    @Override
    public String sqlCreateTable() {
        return "CREATE TABLE IF NOT EXISTS carro (" +
                "id SERIAL PRIMARY KEY, " +
                "placa VARCHAR(15) NOT NULL, " +
                "tipo VARCHAR(15) NOT NULL);";
    }

    @Override
    public String sqlDropTable() {
        return "DROP TABLE IF EXISTS carro";
    }

    @Override
    public PreparedStatement findAll(Connection conn) throws SQLException {
        return conn.prepareStatement(
                "SELECT * FROM carro;"
        );
    }

    @Override
    public PreparedStatement salvar(Connection conn, Carro carro) throws SQLException {
        PreparedStatement psSalvar = conn.prepareStatement(
                "INSERT INTO carro(placa, tipo) VALUES(?, ?);", RETURN_GENERATED_KEYS
        );
        psSalvar.setString(1, carro.getPlaca());
        psSalvar.setString(2, carro.getTipo().toString());
        return psSalvar;
    }

    @Override
    public Carro convertResultToObject(ResultSet resultSet) throws SQLException {
        return Carro.builder()
                .id(resultSet.getLong(1))
                .placa(resultSet.getString(2))
                .tipo(TipoVeiculo.castString(resultSet.getString(3)))
                .build();
    }

    @Override
    public List<Carro> convertResultToObjectList(ResultSet resultSet) throws SQLException {
        List<Carro> result = new ArrayList<>();
        while (resultSet.next()) {
            Carro carro = convertResultToObject(resultSet);
            result.add(carro);
        }
        return result;
    }

    public Carro findByPlaca(String placa) {
        Connection conn = ConnectDataBase.connect();
        Carro result = new Carro();
        try {
            PreparedStatement psFind = conn.prepareStatement("select * from carro where placa = ?;", RETURN_GENERATED_KEYS);
            psFind.setString(1, placa);
            psFind.executeQuery();
            ResultSet resultSet = psFind.getResultSet();
            resultSet.next();
            result = convertResultToObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
