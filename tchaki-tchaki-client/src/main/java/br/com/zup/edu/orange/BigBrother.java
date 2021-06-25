package br.com.zup.edu.orange;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ljtfreitas.restify.http.contract.Get;
import com.github.ljtfreitas.restify.http.contract.Path;
import com.github.ljtfreitas.restify.http.contract.PathParameter;
import com.github.ljtfreitas.restify.http.contract.Post;

@Path("http://localhost:8080/v1")
interface BigBrother {

    @Get
    @Path("/participantes")
    List<Participante> participantes();

    @Post
    @Path("/paredoes/{paredao}/participantes/{participante}/votar")
    CompletableFuture<Paredao> votar(@PathParameter("paredao") String paredao, @PathParameter("participante") String participante);

    class Paredao {

        final String id;
        final List<Emparedado> emparedados;

        @JsonCreator
        Paredao(@JsonProperty("id") String id, @JsonProperty("emparedados") List<Emparedado> emparedados) {
            this.id = id;
            this.emparedados = emparedados;
        }
    }

    class Emparedado {

        final String nome;
        final int votos;

        @JsonCreator
        Emparedado(@JsonProperty("participantes") String nome, @JsonProperty("votos") int votos) {
            this.nome = nome;
            this.votos = votos;
        }
    }

    class Participante {

        final String id;
        final String nome;

        @JsonCreator
        Participante(@JsonProperty("id") String id, @JsonProperty("nome") String nome) {
            this.id = id;
            this.nome = nome;
        }

        @Override
        public String toString() {
            return id + " - " + nome;
        }
    }
}
