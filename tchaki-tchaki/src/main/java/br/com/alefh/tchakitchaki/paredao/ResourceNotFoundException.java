package br.com.alefh.tchakitchaki.paredao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

}