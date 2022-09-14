package br.com.johnny.forum.controller.form;

import br.com.johnny.forum.modelo.Curso;
import br.com.johnny.forum.modelo.Topico;
import br.com.johnny.forum.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;


public class TopicoForm {

    @NotNull @NotEmpty @Length(min = 5)
    private String titulo;

    @NotNull @NotEmpty @Length(min = 10)
    private String mensagem;

    @NotNull @NotEmpty
    private String nomeCurso;



    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public Topico converter(CursoRepository cursorepository) {
        Curso curso = cursorepository.findByNome(nomeCurso);
        System.out.println(nomeCurso);
        return new Topico(titulo,mensagem,curso);
    }
}
