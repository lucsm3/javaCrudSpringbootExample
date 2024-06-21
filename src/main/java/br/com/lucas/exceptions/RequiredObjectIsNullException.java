package br.com.lucas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

    public RequiredObjectIsNullException(String ex) {
        super(ex);
    }
    public RequiredObjectIsNullException() {
        super("nao e possivel atualizar um objeto nulo");
    }
    private static final long serialversionUID = 1L;
}
