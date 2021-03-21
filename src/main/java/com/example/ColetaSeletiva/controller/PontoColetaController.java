package com.example.ColetaSeletiva.controller;

import com.example.ColetaSeletiva.model.PontoColeta;
import com.example.ColetaSeletiva.service.PontoColetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


/**
 * Controlador que irá receber as requisições do usuário
 * */

@Controller
@RequestMapping("/coletaSeletiva")
public class PontoColetaController {

    @Autowired
    private PontoColetaService service;

    /**
     * @return página inicial do sistema
     * */
    @GetMapping("/home")
    public String paginaInicial(){
        return "/home";
    }


    /**
     * @return Retorna a página de busca, para ser preenchida com os requisitos do usuário
     * */
    @GetMapping("/buscar")
    public ModelAndView buscartipoLixo(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("PontoColeta", new PontoColeta());
        mv.setViewName("/buscar");

        return mv;
    }


    /**
     * @return Página que irá possuir o formulário de cadastramento de um novo local
     * */
    @GetMapping("/cadastrar")
    public ModelAndView cadastrarLocal(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("PontoColeta", new PontoColeta());
        mv.setViewName("/cadastrar");
        return mv;
    }

    /**
     * @return controlador que irá receber o local cadastrado pelo usuário e inserir no banco
     * */
    @PostMapping("/inserirLocal")
    public ModelAndView inserirLocal(PontoColeta local){
        ModelAndView mv = new ModelAndView();

        try{
            service.inserir(local);
            //Caso salve com sucesso irá direcionar para a página inicial
            mv.setViewName("/home");
        }catch (Exception e){
            e.printStackTrace();
        }

        return mv;
    }

    /**
     * @return Processa os dados de filtro que o usuário inseriu e faz o processamento do local mais próximo
     * */
    @GetMapping("/resultado")
    public ModelAndView buscarLocal(PontoColeta localDeBusca){
        ModelAndView mv = new ModelAndView();
        PontoColeta proximo = service.findColetaMaisProxima(localDeBusca);

        if(!Objects.isNull(proximo)){

            //Passa a url do mapa do google com o CEP do local mais próximo encontrado
            String mapa = "https://www.google.com.br/maps?q=" + proximo.getCep() + ",%20Brasil&output=embed";

            //Adição das variáveis que o thymeleaf irá processar no html
            mv.addObject("nomeLocal", proximo.getNomeLocal());
            mv.addObject("mapa", mapa);
            mv.addObject("naoEncontrou", false);
        }else{
            mv.addObject("nomeLocal", "Nenhum ponto nas proximidades");
            mv.addObject("naoEncontrou", true);
        }
        // Definindo qual html será a página de retorno
        mv.setViewName("/resultado");
        return mv;
    }





}
