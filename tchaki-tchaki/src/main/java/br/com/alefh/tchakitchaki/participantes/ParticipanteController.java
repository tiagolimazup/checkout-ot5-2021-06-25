package br.com.alefh.tchakitchaki.participantes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/participantes")
public class ParticipanteController {


    @GetMapping
    public ResponseEntity<List<ParticipanteResponse>> participantes() {
        return ResponseEntity.ok(Arrays.stream(Participantes.values()).map(ParticipanteResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ParticipanteResponse peloId(@PathVariable UUID id) {
        ParticipanteResponse participanteResponse = Participantes
                .valores()
                .filter(participantes -> participantes.id.equals(id))
                .findFirst()
                .map(ParticipanteResponse::new)
                .orElseThrow(ResourceNotFoundException::new);

        return participanteResponse;
    }

}


class ParticipanteResponse {
    private final String nome;
    private final UUID id;

    public ParticipanteResponse(Participantes participantes) {
        this.nome = participantes.nome;
        this.id = participantes.id;
    }

    public String getNome() {
        return nome;
    }

    public UUID getId() {
        return id;
    }
}

@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}