package br.com.johnny.forum.controller;


import br.com.johnny.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.johnny.forum.controller.dto.TopicoDTO;
import br.com.johnny.forum.controller.form.AtualizacaoTopicoForm;
import br.com.johnny.forum.controller.form.TopicoForm;
import br.com.johnny.forum.modelo.Topico;
import br.com.johnny.forum.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.johnny.forum.repository.TopicoRepository;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    //commita o sql
    @Transactional
    //limpa todos os registros do cache com nome listadetopicos
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder){
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @GetMapping
    //cria um cache com o nome de listadetopicos
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDTO> listar(
            @RequestParam(required = false) String nomeCurso,@PageableDefault(
                    sort = "id" ,page = 0, size = 10, direction = Sort.Direction.DESC) Pageable paginacao){

        Page<Topico> topicos;
        if(nomeCurso == null){
              topicos = topicoRepository.findAll(paginacao);
        }else{
             topicos = topicoRepository.findByCurso_Nome(nomeCurso,paginacao);
        }
        return TopicoDTO.converter(topicos);
    }


    //Tambem podemos deixar os nomes todos iguais e nem a necessaidade de colocar o "CODIGO" no Path,
    @GetMapping("/{codigo}")
    public  ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("codigo") Long id){
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.map(value -> ResponseEntity.ok(new DetalhesDoTopicoDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if(optionalTopico.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDTO(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
