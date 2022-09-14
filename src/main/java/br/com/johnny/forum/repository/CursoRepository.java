package br.com.johnny.forum.repository;

import br.com.johnny.forum.modelo.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository  extends JpaRepository<Curso,Long> {

    Curso findByNome(String nome);

}
