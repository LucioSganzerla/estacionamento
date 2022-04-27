package br.edu.utfpr.database;

import br.edu.utfpr.repository.*;

import java.util.List;

public class TableControl {

    public static final List<Repository<?, ?>> NEW_TABLES_V1 = List.of(
            new CarroRepository(),
            new VagaRepository()

    );

    public static void createTablesV1() {
        System.out.println("INICIANDO CRIAÇÃO DE TABELAS");
        NEW_TABLES_V1.forEach(Repository::createTable);
        System.out.println("FIM DA CRIAÇÃO DE TABELAS");
    }

}
