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

@Controller
@RequestMapping("/coletaSeletiva")
public class PontoColetaController {

    @Autowired
    private PontoColetaService service;

    @GetMapping("/home")
    public String paginaInicial(){
        return "/home";
    }

    @GetMapping("/buscar")
    public ModelAndView buscartipoLixo(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("PontoColeta", new PontoColeta());
        mv.setViewName("/buscar");

        return mv;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrarLocal(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("PontoColeta", new PontoColeta());
        mv.setViewName("/cadastrar");
        return mv;
    }

    @PostMapping("/inserirLocal")
    public ModelAndView inserirLocal(PontoColeta local){
        ModelAndView mv = new ModelAndView();

        try{
            service.inserir(local);
            mv.setViewName("/home");
        }catch (Exception e){
            e.printStackTrace();
        }

        return mv;
    }

    @GetMapping("/resultado")
    public ModelAndView buscarLocal(PontoColeta localDeBusca){
        ModelAndView mv = new ModelAndView();
        PontoColeta proximo = service.findColetaMaisProxima(localDeBusca);

        if(!Objects.isNull(proximo)){
            String mapa = "https://www.google.com.br/maps?q=" + proximo.getCep() + ",%20Brasil&output=embed";
            mv.addObject("nomeLocal", proximo.getNomeLocal());
            mv.addObject("mapa", mapa);
            mv.addObject("naoEncontrou", false);
        }else{
            mv.addObject("nomeLocal", "Nenhum ponto nas proximidades");
            mv.addObject("naoEncontrou", true);
        }

        mv.setViewName("/resultado");
        return mv;
    }





}
