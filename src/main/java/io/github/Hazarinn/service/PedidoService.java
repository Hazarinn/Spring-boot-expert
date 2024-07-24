package io.github.Hazarinn.service;


import io.github.Hazarinn.domain.entity.Pedido;
import io.github.Hazarinn.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
}
