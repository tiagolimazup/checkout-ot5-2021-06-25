package br.com.alefh.tchakitchaki.participantes;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

public enum Participantes {

    Arthur("Arthur", UUID.randomUUID()),
    Arcrebiano("Arcrebiano", UUID.randomUUID()),
    Joao("João", UUID.randomUUID()),
    Juliette("Juliette", UUID.randomUUID()),
    Kerline("Kerline", UUID.randomUUID()),
    Lumena("Lumena", UUID.randomUUID()),
    Gilberto("Gilberto", UUID.randomUUID()),
    Thais("Thais", UUID.randomUUID()),
    Sarah("Sarah", UUID.randomUUID()),
    Karol("Karol Conka", UUID.randomUUID()),
    Carla("Carla Diaz", UUID.randomUUID()),
    Camila("Camila de Lucas", UUID.randomUUID()),
    Pocah("Pocah", UUID.randomUUID()),
    NegoDi("Nego Di", UUID.randomUUID()),
    Lucas("Lucas Penteado", UUID.randomUUID()),
    Rodolfo("Rodolfo", UUID.randomUUID()),
    VihTube("Viih Tube", UUID.randomUUID()),
    Projota("Projota", UUID.randomUUID()),
    Fiuk("Fiuk", UUID.randomUUID()),
    Yuri("Yuri", UUID.fromString("9d1c6f71-e96d-4bf8-82e6-72436aba420d")),
    Ponte("Rafael Ponte", UUID.fromString("47d471df-9cfb-4519-a019-33e0f6141669"));


    public final String nome;
    public final UUID id;

    private Participantes(String nome, UUID id) {
        this.nome = nome;
        this.id = id;
    }

    public static Participantes peloId(UUID uuid) {
        return Arrays.stream(Participantes.values()).filter(participante -> participante.id.equals(uuid)).findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    public static Stream<Participantes> valores () {
        return Arrays.stream(Participantes.values());
    }
}
