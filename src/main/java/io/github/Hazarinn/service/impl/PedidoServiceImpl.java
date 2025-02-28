package io.github.Hazarinn.service.impl;


import io.github.Hazarinn.domain.entity.Cliente;
import io.github.Hazarinn.domain.entity.ItemPedido;
import io.github.Hazarinn.domain.entity.Pedido;
import io.github.Hazarinn.domain.entity.Produto;
import io.github.Hazarinn.domain.enums.StatusPedido;
import io.github.Hazarinn.domain.repository.Clientes;
import io.github.Hazarinn.domain.repository.ItensPedido;
import io.github.Hazarinn.domain.repository.Pedidos;
import io.github.Hazarinn.domain.repository.Produtos;
import io.github.Hazarinn.exception.PedidoNaoEncontradoException;
import io.github.Hazarinn.exception.RegraNegocioException;
import io.github.Hazarinn.rest.dto.ItemPedidoDTO;
import io.github.Hazarinn.rest.dto.PedidoDTO;
import io.github.Hazarinn.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itensPedidoRepository;


    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itensPedidos = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidoRepository.saveAll(itensPedidos);
        pedido.setItens(itensPedidos);
        pedido.setStatus(StatusPedido.REALIZADO);
        return pedido;

    }

    public Optional<Pedido> obterPedidoCompleto(Integer id) {

        return repository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {

        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(() -> new PedidoNaoEncontradoException());

    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
        if(itens.isEmpty()){
            throw  new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }

        return itens
                .stream()
                .map(dto -> {

                    Integer idProduto = dto.getProduto();
                    Produto produto  = produtosRepository.findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;

                }).collect(Collectors.toList());


    }
}
