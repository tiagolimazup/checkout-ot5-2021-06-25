package br.com.zup.edu.orange;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.ljtfreitas.restify.http.RestifyProxyBuilder;

import static java.util.stream.Collectors.joining;

public class Main {

    public static void main(String[] args) {
        BigBrother bigBrother = new RestifyProxyBuilder()
                .target(BigBrother.class)
                .build();

        List<String> participantes = List.of("d32ecbc7-fa02-4d9e-9bca-73b3b4a1f337",
                "17b161de-06c9-4e3b-9141-3892c2321309",
                "47d471df-9cfb-4519-a019-33e0f6141669",
                "9d1c6f71-e96d-4bf8-82e6-72436aba420d");

        Random escolhaDeParticipantes = new Random();
        Random escolhaDeVotos = new Random();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleWithFixedDelay(() -> {

            int numeroDeVotos = escolhaDeVotos.nextInt(30000);

            System.out.println("Numero de votos: " + numeroDeVotos);

            for (int i = 0; i < numeroDeVotos; i++) {
                String participante = participantes.get(escolhaDeParticipantes.nextInt(participantes.size()));

                bigBrother.votar("8797d6e5-cc8c-4e0f-9ef8-caa1b6239b47", participante)
                    .thenAccept(paredao -> {
                        System.out.println("Estado atual do paredao " + paredao.id + ": " +
                                paredao.emparedados.stream().map(e -> e.nome + " - " + e.votos + " votos")
                                        .collect(joining(", ")));
                    });
            }

        }, 1000, 3000, TimeUnit.MILLISECONDS);

    }
}
