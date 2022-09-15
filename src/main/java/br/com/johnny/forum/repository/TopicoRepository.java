package br.com.johnny.forum.repository;

import br.com.johnny.forum.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Passamos no Jpa a classe e o tipo da chave primaria
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    //Para acessar um atributo dentro de outra classe que faz parte dos atributos da classe atual,
    //colocamos o nome do atributo + o nome do campo que o atributo possui
    //Podemos também utilizar o "_" caso tenhamos um atributo com o mesmo nome do atributo é uma classe e possui um atributo com o mesmo nome
    //assim evitamos ambiguidade.
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);


    //Podemos criar um query fora do padrão do spring, seguindo essa forma.
    //@Query("Select T from topico t where t.curso.nome = : nomeCurso")
    //List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);

}
