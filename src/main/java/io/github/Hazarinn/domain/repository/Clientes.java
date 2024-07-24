package io.github.Hazarinn.domain.repository;


import io.github.Hazarinn.domain.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {



    @Query(value= "select c from Cliente c where c.nome like :nome ")
    List<Cliente> encontrarPorNome( @Param("nome") String nome);


    // Query Method
    // Para fazer atualização ou delete, obrigatoriamente tem que colocar Modifying
    @Modifying
    void deleteByNome(String nome);
//
//    List<Cliente> finByNomeOrIdOrderById(String nome, Long id);




    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos p where c.id = :id ")
    Cliente finClienteFetchPedidos (@Param("id") Integer id);

}
