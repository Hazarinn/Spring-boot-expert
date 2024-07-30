package io.github.Hazarinn.exception;

public class PedidoNaoEncontradoException extends  RuntimeException{
    public PedidoNaoEncontradoException(){
        super("Pedido não encontrado");
    }
}
