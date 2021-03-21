package com.example.ColetaSeletiva.service;


import com.example.ColetaSeletiva.model.PontoColeta;
import com.example.ColetaSeletiva.repository.PontoColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.PolicyNode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PontoColetaService{

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    public Optional<PontoColeta> findById(int id){
        return pontoColetaRepository.findById(id);
    }

    public PontoColeta findColetaMaisProxima(PontoColeta local){

        List<PontoColeta> locais = pontoColetaRepository.findAll();
        PontoColeta maisProximo = null;
        int pontuacaoAnterior = -1;
        int pontuacaoAtual;


        for(PontoColeta ponto: locais){
            pontuacaoAtual = verificarProximidade(ponto.getCep(), local.getCep());
            if(((pontuacaoAtual < pontuacaoAnterior) || pontuacaoAnterior == -1) && pontuacaoAtual >= 0){
                maisProximo = ponto;
                pontuacaoAnterior = pontuacaoAtual;
            }
        }

        return maisProximo;
    }

    public void inserir(PontoColeta local){
        local.setId(getLastId() + 1);
        pontoColetaRepository.save(local);
    }

    private int getLastId(){
        return pontoColetaRepository.findAll().size();
    }

    private int verificarProximidade(String cep, String cepDeBusca){

        int nivelDeProximidade = -1;

        if(cep.substring(0,5).equals(cepDeBusca.substring(0,5))){
            int sufixoAtual = Integer.parseInt(cep.substring(5,8));
            int sufixoBusca = Integer.parseInt(cepDeBusca.substring(5,8));

            nivelDeProximidade = Math.abs(sufixoAtual - sufixoBusca);
        }else if(cep.substring(0,4).equals(cepDeBusca.substring(0,4))){
            int bairroAtual = Integer.parseInt(cep.substring(4,5));
            int bairroBusca = Integer.parseInt(cepDeBusca.substring(4,5));

            nivelDeProximidade = Math.abs((bairroAtual - bairroBusca) * 1000);
            if(nivelDeProximidade == 0){
                nivelDeProximidade = 1000;
            }

        }else if(cep.substring(0,3).equals(cepDeBusca.substring(0,3))){
            int cidadeAtual = Integer.parseInt(cep.substring(2,3));
            int cidadeBusca = Integer.parseInt(cepDeBusca.substring(2,3));

            nivelDeProximidade = Math.abs((cidadeAtual - cidadeBusca) * 2000);
            if(nivelDeProximidade == 0){
                nivelDeProximidade = 2000;
            }
        }

        return nivelDeProximidade;
    }
}
