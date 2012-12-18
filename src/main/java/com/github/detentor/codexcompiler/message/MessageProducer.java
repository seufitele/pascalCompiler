package com.github.detentor.codexcompiler.message;

/**
 * Essa interface deve ser assinada por todas as classes que enviam mensagens. <br/>
 * Essas mensagens podem ser mensagens de erro de sintaxe, mensagens de debug, etc.
 * 
 * @author Vinicius Seufitele
 *
 */
public interface MessageProducer
{
    /**
     * Envia a mensagem passada como parâmetro para os listeners
     * @param message A mensagem a ser enviada
     */
    void sendMessage(Message message);
}
