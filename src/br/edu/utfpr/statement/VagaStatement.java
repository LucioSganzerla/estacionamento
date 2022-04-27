package br.edu.utfpr.statement;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.model.Vaga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class VagaStatement implements Statement<Vaga> {

    @Override
    public String sqlCreateTable() {
        return "CREATE TABLE IF NOT EXISTS vaga (" +
                "id SERIAL PRIMARY KEY, " +
                "id_carro INT8 NULL, " +
                "datain timestamp NULL, " +
                "dataout timestamp null);";
    }

    @Override
    public String sqlDropTable() {
        return "DROP TABLE IF EXISTS vaga";
    }

    @Override
    public PreparedStatement findAll(Connection conn) throws SQLException {
        return conn.prepareStatement(
                "SELECT * FROM vaga;"
        );
    }

    public List<Vaga> findAllOcupadas() {
        Connection conn = ConnectDataBase.connect();
        List<Vaga> result = new ArrayList<>();
        try {
            PreparedStatement psFind = conn.prepareStatement("select * from vaga where dataout is null;", RETURN_GENERATED_KEYS);
            psFind.executeQuery();
            ResultSet resultSet = psFind.getResultSet();
            result = convertResultToObjectList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public PreparedStatement salvar(Connection conn, Vaga vaga) throws SQLException {
        if (vaga.getId() == null) {
            vaga.setDatain(new Timestamp(System.currentTimeMillis()));
            return insert(conn, vaga);
        } else {
            vaga.setDataout(new Timestamp(System.currentTimeMillis()));
            return update(conn, vaga);
        }
    }

    @Override
    public Vaga convertResultToObject(ResultSet resultSet) throws SQLException {
        return Vaga.builder()
                .id(resultSet.getLong(1))
                .id_carro(resultSet.getLong(2))
                .datain(resultSet.getTimestamp(3))
                .dataout(resultSet.getTimestamp(4))
                .build();
    }

    @Override
    public List<Vaga> convertResultToObjectList(ResultSet resultSet) throws SQLException {
        List<Vaga> result = new ArrayList<>();
        while (resultSet.next()) {
            Vaga vaga = convertResultToObject(resultSet);
            result.add(vaga);
        }
        return result;
    }

    private PreparedStatement insert(Connection conn, Vaga vaga) throws SQLException {
        PreparedStatement psSalvar = conn.prepareStatement(
                "INSERT INTO vaga(id_carro, datain) VALUES(?, ?);", RETURN_GENERATED_KEYS
        );
        psSalvar.setLong(1, vaga.getId_carro());
        psSalvar.setTimestamp(2, vaga.getDatain());
        return psSalvar;
    }

    private PreparedStatement update(Connection conn, Vaga vaga) throws SQLException {
        PreparedStatement psSalvar = conn.prepareStatement(
                "UPDATE vaga SET datain = ?, dataout = ? WHERE id = ?;", RETURN_GENERATED_KEYS
        );
        psSalvar.setTimestamp(1, vaga.getDatain());
        psSalvar.setTimestamp(2, vaga.getDataout());
        psSalvar.setLong(3, vaga.getId());
        return psSalvar;
    }

    public Vaga findByID(Long id) {
        Connection conn = ConnectDataBase.connect();
        Vaga result = new Vaga();
        try {
            PreparedStatement psFind = conn.prepareStatement("select * from vaga where id = ?;", RETURN_GENERATED_KEYS);
            psFind.setLong(1, id);
            psFind.executeQuery();
            ResultSet resultSet = psFind.getResultSet();
            resultSet.next();
            result = convertResultToObject(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Vaga findVagaByCarro(Long id_carro) {
        Connection conn = ConnectDataBase.connect();
        Vaga result = new Vaga();
        try {
            PreparedStatement psFind = conn.prepareStatement("select * from vaga where id_carro = ? and dataout is null;", RETURN_GENERATED_KEYS);
            psFind.setLong(1, id_carro);
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
