package br.com.alefh.tchakitchaki.paredao;

import br.com.alefh.tchakitchaki.participantes.Participantes;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@RestController
@RequestMapping("/v1/paredoes")
public class ParedaoController {

    @Autowired
    private ParedaoDB paredaoDB;

    @Autowired
    private MeterRegistry metrics;

    @PostMapping
    public ResponseEntity<ParedaoResponse> criaParedao(@RequestBody ParedaoRequest paredaoRequest) {

        List<Emparedado> emparedados = paredaoRequest.
                getParticipantes()
                .stream()
                .map(Participantes::peloId)
                .map(Emparedado::new)
                .collect(Collectors.toList());

        Paredao paredao = new Paredao(emparedados);
        paredaoDB.adiciona(paredao);
        UriComponentsBuilder uriComponentsBuilder = fromUriString("/v1/paredao/{id}");
        return ResponseEntity.created(uriComponentsBuilder.buildAndExpand(paredao.getId()).toUri())
                .body(new ParedaoResponse(paredao));

    }

    // preicsa de enpoint
    // precisa identificar pessoa(emparedado)
    // precisa identificar o paredao
    // Mapping "/v1/paredoes"

     @Timed(value = "votacao", extraTags = {
        "region", "us-west-2"
     })
     @PostMapping("/{id}/participantes/{idParticipante}/votar")
     public ResponseEntity<ParedaoResponse> votar(@PathVariable UUID id, @PathVariable UUID idParticipante) {
         Paredao paredao = paredaoDB.peloId(id);
         Emparedado emparedado = paredao.emparedadoPor(idParticipante);
         emparedado.incluiVoto();
         URI uri = fromUriString("/v1/paredao/{id}").buildAndExpand(paredao.getId()).toUri();

         //criar
         Counter contadorParedao = metrics.counter("votacao", "paredao", paredao.getId().toString());
         Counter contadorEmparedado = metrics.counter("emparedado", "paredao", paredao.getId().toString(), "participante", emparedado.getParticipantes().nome);

         contadorParedao.increment(); // contador = contador + 1 contador++
         contadorEmparedado.increment();

         return ResponseEntity.created(uri).body(new ParedaoResponse(paredao));
     }

    @GetMapping("/{id}")
    public ResponseEntity<ParedaoResponse> peloId(@PathVariable UUID id) {
        Paredao paredao = paredaoDB.peloId(id);
        return ResponseEntity.ok(new ParedaoResponse(paredao));
    }
}


class ParedaoRequest {

    private List<UUID> participantes;

    public List<UUID> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<UUID> participantes) {
        this.participantes = participantes;
    }
}

class ParedaoResponse {

    private UUID id;
    private List<Emparedado> emparedados;

    public ParedaoResponse(Paredao paredao) {
        this.emparedados = paredao.getEmparedados();
        this.id = paredao.getId();
    }

    public List<Emparedado> getEmparedados() {
        return emparedados;
    }

    public UUID getId() {
        return id;
    }
}
