package br.com.alefh.tchakitchaki.paredao;

import br.com.alefh.tchakitchaki.participantes.Participantes;

public class Emparedado {

    private Participantes participantes;
    private Integer votos;

    public Emparedado(Participantes participantes) {
        this.participantes = participantes;
        this.votos = 0;
    }

    public Participantes getParticipantes() {
        return participantes;
    }

    public Integer getVotos() {
        return votos;
    }

    public void incluiVoto() {
        this.votos++;
    }
}
