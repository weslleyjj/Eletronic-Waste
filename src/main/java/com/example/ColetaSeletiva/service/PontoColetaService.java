package com.example.ColetaSeletiva.service;


import com.example.ColetaSeletiva.model.PontoColeta;
import com.example.ColetaSeletiva.repository.PontoColetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe de serviços, onde há a conexão com o banco e o tratamento de dados
 * */

@Service
public class PontoColetaService{

    //Conexão do banco já configurada pelo spring
    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    /**
     * @param local Objeto de PontoColeta que possui os filtros de pesquisa do usuário
     * @return local de coleta mais próximo do cep informado
     * */
    public PontoColeta findColetaMaisProxima(PontoColeta local){

        //Variáveis que irão trabalhar para encontrar o ponto mais próximo
        List<PontoColeta> locais = pontoColetaRepository.findAll();
        PontoColeta maisProximo = null;
        int pontuacaoAnterior = -1;
        int pontuacaoAtual;

        // Laço que irá percorrer todos os locais cadastrados
        for(PontoColeta ponto: locais){
            // Se o tipo de lixo for equivalente irá verificar o nível de proximidade dele
            if(ponto.getTipoLixo().equals(local.getTipoLixo())){
                pontuacaoAtual = verificarProximidade(ponto.getCep(), local.getCep());
                //Armazena o local processado apenas se ele for mais próximo que o local processado anterior
                if(((pontuacaoAtual < pontuacaoAnterior) || pontuacaoAnterior == -1) && pontuacaoAtual >= 0){
                    maisProximo = ponto;
                    pontuacaoAnterior = pontuacaoAtual;
                }
            }
        }

        return maisProximo;
    }

    /**
     * @param local Recebe o ponto de coleta para cadastrar
     * */
    public void inserir(PontoColeta local){
        //método getLastId para saber qual o id do próximo item cadastrado
        local.setId(getLastId() + 1);
        pontoColetaRepository.save(local);
    }

    /**
     * @return quantidade total de dados ativos no banco
     * */
    private int getLastId(){
        return pontoColetaRepository.findAll().size();
    }


    /**
     * @param cep CEP do ponto que está sendo verificado
     * @param cepDeBusca CEP inserido pelo usuário para buscar o local mais próximo
     * */
    private int verificarProximidade(String cep, String cepDeBusca){

        //variável para reconhecer o quão próximo o local está está do cep inserido, quanto menor, melhor
        int nivelDeProximidade = -1;

        //verifica se o cep é do mesmo bairro, e irá buscar a proximidade pelas ruas
        if(cep.substring(0,5).equals(cepDeBusca.substring(0,5))){
            //métodos substring que irão pegar os 3 ultimos dígitos do CEP
            int sufixoAtual = Integer.parseInt(cep.substring(5,8));
            int sufixoBusca = Integer.parseInt(cepDeBusca.substring(5,8));

            //calculo de distancia de uma rua para a outra
            nivelDeProximidade = Math.abs(sufixoAtual - sufixoBusca);
        }
        //Else if caso não exista ponto de coleta no mesmo bairro, irá procurar pela mesma zona ( oeste, leste, centro, sul, norte e etc)
        else if(cep.substring(0,4).equals(cepDeBusca.substring(0,4))){
            int bairroAtual = Integer.parseInt(cep.substring(4,5));
            int bairroBusca = Integer.parseInt(cepDeBusca.substring(4,5));

            //Faz o calculo de distância entre as zonas da cidade e multiplica por 1000
            // que é uma constante para definir a distância média de locais de zonas da cidade
            nivelDeProximidade = Math.abs((bairroAtual - bairroBusca) * 1000);
            if(nivelDeProximidade == 0){
                nivelDeProximidade = 1000;
            }

        }
        //Último else if que irá efetuar a busca por cidades próximas
        else if(cep.substring(0,3).equals(cepDeBusca.substring(0,3))){
            //Métodos substring pegando apenas o código da cidade
            int cidadeAtual = Integer.parseInt(cep.substring(2,3));
            int cidadeBusca = Integer.parseInt(cepDeBusca.substring(2,3));

            //o valor de multiplicação do calculo de cidades é 2000
            // devido a alta distância entre cidades, para ser considerado a maior distância dentre as calculadas
            nivelDeProximidade = Math.abs((cidadeAtual - cidadeBusca) * 2000);
            if(nivelDeProximidade == 0){
                nivelDeProximidade = 2000;
            }
        }

        // Se retornar -1 significa que não encontrou nenhum lugar próximo
        return nivelDeProximidade;
    }
}
