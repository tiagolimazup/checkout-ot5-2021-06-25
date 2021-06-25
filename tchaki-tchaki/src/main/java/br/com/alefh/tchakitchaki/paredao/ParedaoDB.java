package br.com.alefh.tchakitchaki.paredao;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@ApplicationScope
public class ParedaoDB {

    private List<Paredao> db = new ArrayList<>();

    public void adiciona(Paredao paredao) {
        this.db.add(paredao);
    }

    public Paredao peloId(UUID id) {
        return db.stream()
                .filter(paredao -> paredao.getId().equals(id))
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }
}
