package br.com.johnny.forum.controller.dto;

import br.com.johnny.forum.modelo.StatusTopico;
import br.com.johnny.forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class DetalhesDoTopicoDto {
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDTO> respostas;

    public DetalhesDoTopicoDto(Topico t) {
        this.id = t.getId();
        this.titulo = t.getTitulo();
        this.mensagem = t.getMensagem();
        this.dataCriacao = t.getDataCriacao();
        this.nomeAutor = t.getAutor().getNome();
        this.status = t.getStatus();
        this.respostas = new ArrayList<>();
        this.respostas.addAll(t.getRespostas().stream().map(RespostaDTO::new).toList());
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDTO> getRespostas() {
        return respostas;
    }
}
