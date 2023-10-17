package com.ivancl4udio.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionMapper.class);


    @Override
    public Response toResponse(ConstraintViolationException e) {
        ResponseError erro = new ResponseError();
        erro.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        erro.setMensagem(e.getMessage());

        LOGGER.error("Violação de constraint detectada", e);

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(erro)
                .build();
    }
}
