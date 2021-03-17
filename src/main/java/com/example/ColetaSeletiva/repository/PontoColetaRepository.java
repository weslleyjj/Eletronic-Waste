package com.example.ColetaSeletiva.repository;

import com.example.ColetaSeletiva.model.PontoColeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PontoColetaRepository extends MongoRepository<PontoColeta, Integer> {

    List<PontoColeta> getAllById(Integer id);

    List<PontoColeta> getAllByCep(String cep);

    List<PontoColeta> getPontoColetaByTipoLixo(String tipoLixo);

}
