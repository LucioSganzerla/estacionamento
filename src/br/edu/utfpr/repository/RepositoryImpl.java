package br.edu.utfpr.repository;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.model.Model;
import br.edu.utfpr.statement.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositoryImpl<T extends Statement, R extends Model> implements Repository<T, R> {

    public abstract T getInstanceOfT();

    @Override
    public void createTable() {
        try {
            Connection conn = ConnectDataBase.connect();
            System.out.println("Criando tabela " + this.getClass().getSimpleName());
            PreparedStatement psCreate = conn.prepareStatement(
                    getInstanceOfT().sqlCreateTable());
            PreparedStatement psDrop = conn.prepareStatement(
                    getInstanceOfT().sqlDropTable());

            psDrop.executeUpdate();
            psCreate.executeUpdate();

            psCreate.close();
            psDrop.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("ERRO AO CRIAR TABELA " + this.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

    @Override
    public void salvar(Model r) {
        Connection conn = ConnectDataBase.connect();
        try {
            PreparedStatement psSalvar = getInstanceOfT().salvar(conn, r);
            int linhasAfetadas = psSalvar.executeUpdate();
            if (linhasAfetadas > 0) {
                psSalvar.getGeneratedKeys().next();
                r.setId(psSalvar.getGeneratedKeys().getLong(1));
            }
            psSalvar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO CADASTRAR CARRO");
        }
    }

    @Override
    public List<R> findAll() {
        Connection conn = ConnectDataBase.connect();
        List<R> result = new ArrayList<>();
        try {
            PreparedStatement psFind = getInstanceOfT().findAll(conn);
            psFind.executeQuery();
            ResultSet resultSet = psFind.getResultSet();
            result = getInstanceOfT()
                    .convertResultToObjectList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
