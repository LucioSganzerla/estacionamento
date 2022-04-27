package br.edu.utfpr;

import br.edu.utfpr.database.TableControl;
import br.edu.utfpr.model.Carro;
import br.edu.utfpr.model.TipoVeiculo;
import br.edu.utfpr.model.Vaga;
import br.edu.utfpr.repository.CarroRepository;
import br.edu.utfpr.repository.VagaRepository;
import br.edu.utfpr.service.MathService;

import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        TableControl.createTablesV1();
        CarroRepository carroRepository = new CarroRepository();
        VagaRepository vagaRepository = new VagaRepository();
        MathService mathService = new MathService();

        Carro carro1 = new Carro("ABC-1234", TipoVeiculo.CARRO);
        Carro carro2 = new Carro("DEF-5678", TipoVeiculo.CARRO);
        Carro carro3 = new Carro("GHI-9012", TipoVeiculo.CARRO);
        Carro carro4 = new Carro("JKL-3456", TipoVeiculo.CARRO);
        Carro moto1 = new Carro("MNO-7890", TipoVeiculo.MOTO);
        Carro moto2 = new Carro("PQR-1234", TipoVeiculo.MOTO);

        Vaga vaga1 = null;
        Vaga vaga2 = null;
        Vaga vaga3 = null;
        Vaga vaga4 = null;
        Vaga vaga5 = null;
        Vaga vaga6 = null;

        easyCarSave(carro1, vaga1, carroRepository, vagaRepository);
        easyCarSave(carro2, vaga2, carroRepository, vagaRepository);
        easyCarSave(carro3, vaga3, carroRepository, vagaRepository);
        easyCarSave(carro4, vaga4, carroRepository, vagaRepository);
        easyCarSave(moto1, vaga5, carroRepository, vagaRepository);
        easyCarSave(moto2, vaga6, carroRepository, vagaRepository);

        System.out.println("Carros Registrados");
        carroRepository.findAll().forEach(System.out::println);

        System.out.println("Registro de Ocupação das Vagas");
        vagaRepository.findAll().forEach(System.out::println);

        easyCarExit("GHI-9012", carroRepository, vagaRepository, mathService);
        System.out.println();
        easyCarExit("PQR-1234", carroRepository, vagaRepository, mathService);
        System.out.println();
        easyCarExit("ABC-1234", carroRepository, vagaRepository, mathService);
        System.out.println();

        System.out.println("Vagas Ocupadas");
        vagaRepository.findAllOcupadas().forEach(System.out::println);

        System.out.println("Fim");
    }

    public static void easyCarExit(String placa, CarroRepository carroRepository, VagaRepository vagaRepository, MathService mathService) {
        Carro carroSaiu = carroRepository.findByPlaca(placa);
        Vaga vagaSaiu = vagaRepository.findByCarro(carroSaiu.getId());
        vagaRepository.salvar(vagaSaiu);
        vagaSaiu = vagaRepository.findById(vagaSaiu.getId());

        /*
            Forçar aumentar o tempo que permaneceu na vaga
         */
        vagaSaiu.setDataout(new Timestamp(System.currentTimeMillis() * mathService.getMathRandom(3000, 100000.0)));

        double tempo = mathService.calcularTempo(vagaSaiu.getDatain(), vagaSaiu.getDataout());

        System.out.println("Vrum vrum, veiculo de placa " + placa + " saiu!");
        System.out.println("O veiculo era de tipo " + carroSaiu.getTipo() + " e saiu com o ID " + carroSaiu.getId());
        System.out.println("Ele estava na vaga de ID: " + vagaSaiu.getId());
        System.out.println("E permaneceu por " + mathService.calcularTempo(vagaSaiu.getDatain(), vagaSaiu.getDataout()) + " Minutos");
        System.out.println("O veiculo era um " + carroSaiu.getTipo() + " e o valor da passagem foi de R$" + mathService.calculaValor(carroSaiu, tempo));
    }

    public static void easyCarSave(Carro carro, Vaga vaga, CarroRepository carroRepository, VagaRepository vagaRepository) {
        System.out.println("Vrum, Carro " + carro.getPlaca() + " chegou!");
        carro = new Carro(carro.getPlaca(), carro.getTipo());
        carroRepository.salvar(carro);

        vaga = new Vaga(carro.getId());
        vagaRepository.salvar(vaga);
        System.out.println("Vaga " + vaga.getId() + " utilizada pelo o carro " + carro.getId());
    }
}
