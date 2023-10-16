package com.ivancl4udio;


import com.ivancl4udio.exception.ResponseError;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @POST
    @Transactional // The built-in method persist() does not have the annotation set, so even the quarkus default method wont work without a wrapped @Transactional
    public Response criarUsuario(Usuario usuario) {
        if (usuario.nome == null || usuario.email == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new ResponseError(400, "Os campos 'nome' e 'email' são obrigatórios."))
                    .build();
        }

        Usuario.persist(usuario);
        return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    @GET
    public List<Usuario> listarUsuarios() {
        return Usuario.listAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarUsuario(@PathParam("id") Long id) {
        Usuario usuario = Usuario.findById(id);
        if (usuario == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ResponseError(404, "Usuário não encontrado."))
                    .build();
        }

        return Response.ok(usuario).build();
    }


    @PUT
    @Transactional// The built-in method persist() does not have the annotation set, so even the quarkus default method wont work without a wrapped @Transactional
    @Path("/{id}")
    public Response atualizarUsuario(@PathParam("id") Long id, Usuario usuario) {
        Usuario usuarioExistente = Usuario.findById(id);
        if (usuarioExistente == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ResponseError(404, "Usuário não encontrado."))
                    .build();
        }

        usuarioExistente.nome = usuario.nome;
        usuarioExistente.email = usuario.email;

        usuarioExistente.persistAndFlush();
        return Response.ok(usuarioExistente).build();
    }

    @DELETE
    @Transactional// The built-in method persist() does not have the annotation set, so even the quarkus default method wont work without a wrapped @Transactional
    @Path("/{id}")
    public Response deletarUsuario(@PathParam("id") Long id) {
        Usuario usuario = Usuario.findById(id);
        if (usuario == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new ResponseError(404, "Usuário não encontrado."))
                    .build();
        }

        usuario.delete();
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
