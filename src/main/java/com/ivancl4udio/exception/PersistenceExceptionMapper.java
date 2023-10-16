package com.ivancl4udio.exception;

import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceExceptionMapper.class);

    @Override
    public Response toResponse(PersistenceException e) {
        ResponseError erro = new ResponseError();
        erro.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        erro.setMensagem("Ocorreu um erro ao processar a solicitação no servidor.");

        LOGGER.error("Erro de persistência detectado", e);

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(erro)
                .build();
    }
}
