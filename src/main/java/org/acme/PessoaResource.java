package org.acme;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;



@Path("/pessoas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaResource {

    private static List<Pessoa> lista = new ArrayList<>();
    private static long contadorId = 1;

    @GET
    public List<Pessoa> listar() {
        return lista;
    }

    @POST
    public Response criar(Pessoa pessoa) {
        pessoa.setId(contadorId++);
        lista.add(pessoa);
        return Response.status(Response.Status.CREATED).entity(pessoa).build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") long id) {
        return lista.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") long id, Pessoa dados) {
        for (Pessoa p : lista) {
            if (p.getId() == id) {
                p.setNome(dados.getNome());
                p.setIdade(dados.getIdade());
                return Response.ok(p).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") long id) {
        boolean removed = lista.removeIf(p -> p.getId() == id);
        if (removed) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

@Inject
@RestClient
AulaClient aulaClient;

