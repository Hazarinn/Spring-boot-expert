package io.github.Hazarinn.rest.controller;


import io.github.Hazarinn.domain.entity.Cliente;
import io.github.Hazarinn.domain.entity.Produto;
import io.github.Hazarinn.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value="/api/produtos")
public class ProdutoController {

    private Produtos repository;

    public ProdutoController(Produtos repository) {
        this.repository = repository;
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save( @RequestBody @Valid Produto produto){
        return repository.save(produto);

    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid Produto produto){

        repository.findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    repository.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));

    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {

        repository.findById(id)
                .map(p -> {
                    repository.deleteById(id);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return  repository.findAll(example);



    }

    }