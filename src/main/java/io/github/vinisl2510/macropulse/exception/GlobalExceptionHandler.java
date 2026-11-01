package io.github.vinisl2510.macropulse.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 503: o Banco Central não respondeu
    @ExceptionHandler(BcbIndisponivelException.class)
    public ProblemDetail tratarBcbIndisponivel(BcbIndisponivelException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    // 404: não existe dado salvo para o que foi pedido
    @ExceptionHandler(DadosNaoEncontradosException.class)
    public ProblemDetail tratarDadosNaoEncontrados(DadosNaoEncontradosException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 400: alguém mandou um parâmetro que não dá para converter (ex.: inicio=abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tratarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Parâmetro '" + ex.getName() + "' inválido. Use datas no formato yyyy-MM-dd.");
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ProblemDetail tratarParametroInvalido(HandlerMethodValidationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "O parâmetro 'dias' deve ser maior ou igual a 1.");
    }
}