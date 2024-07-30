package io.github.Hazarinn.service;


import io.github.Hazarinn.domain.entity.Pedido;
import io.github.Hazarinn.domain.enums.StatusPedido;
import io.github.Hazarinn.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

   Optional<Pedido> obterPedidoCompleto(Integer id);

   void atualizaStatus(Integer id, StatusPedido statusPedido);



}
