package com.example.ColetaSeletiva.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Classe que representa um documento no banco de dados MongoDB para que o spring processe e transforme em possíveis dados no banco
 * */
@Document(collection = "Ponto_coleta")
public class PontoColeta {

    @Id
    private Integer id;

    private String nomeLocal;

    private String cep;

    private String tipoLixo;

    private String contato;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoLixo() {
        return tipoLixo;
    }

    public void setTipoLixo(String tipoLixo) {
        this.tipoLixo = tipoLixo;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
