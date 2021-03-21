package com.example.ColetaSeletiva.repository;

import com.example.ColetaSeletiva.model.PontoColeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface repository que irá possuir os métodos de acesso ao banco de dados
 * */

@Repository
// A extensão da classe MongoRepository faz com que o hibernate utilize os métodos próprios para MongoDB
public interface PontoColetaRepository extends MongoRepository<PontoColeta, Integer> {

    List<PontoColeta> getAllById(Integer id);

    List<PontoColeta> getAllByCep(String cep);

    List<PontoColeta> getPontoColetaByTipoLixo(String tipoLixo);

}
