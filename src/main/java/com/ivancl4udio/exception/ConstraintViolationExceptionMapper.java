package com.ivancl4udio.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        ResponseError erro = new ResponseError();
        erro.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        erro.setMensagem(e.getMessage());

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(erro)
                .build();
    }
}
