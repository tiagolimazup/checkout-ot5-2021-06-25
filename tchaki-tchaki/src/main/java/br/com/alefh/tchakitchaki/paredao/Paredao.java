package br.com.alefh.tchakitchaki.paredao;


import java.util.List;
import java.util.UUID;

public class Paredao {

    private UUID id;
    private List<Emparedado> emparedados;

    public Paredao(List<Emparedado> emparedados) {
        this.emparedados = emparedados;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public List<Emparedado> getEmparedados() {
        return emparedados;
    }


    public Emparedado emparedadoPor(UUID participanteId) {
        return emparedados.stream()
                .filter(emparedado -> emparedado.getParticipantes().id.equals(participanteId))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }
}
