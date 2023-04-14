package at.spengergasse._2223_GenericUser.presentation;

import at.spengergasse._2223_GenericUser.presentation.exceptions.BadArgumentException;
import at.spengergasse._2223_GenericUser.presentation.exceptions.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractRestController {

    public <T> T wrappedServiceExecution(Supplier<T> supplier) {
        if(supplier == null) {
            log.warn("Null Supplier passed in wrappedServiceExecution");
            throw new InternalServerException("Passed Null SupplierFunction in wrappedServiceExecution", null);
        }
        try {
            return supplier.get();
        } catch (NullPointerException | IllegalArgumentException e) {
            log.warn("Encountered exception while updating player: Type: {} - Msg: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BadArgumentException(e.getMessage(), e);
        } catch (ServiceException e) {
            log.warn("Encountered exception while updating player: Type: {} - Msg: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
    }
    public <T, G> T wrappedServiceExecution(G dto, Function<G, T> func) {
        if(func == null)
            throw new InternalServerException("Passed Null Function in wrappedServiceExecution", null);

        try {
            return func.apply(dto);
        } catch (NullPointerException | IllegalArgumentException e) {
            log.warn("Encountered exception while updating player: Type: {} - Msg: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BadArgumentException(e.getMessage(), e);
        } catch (ServiceException e) {
            log.warn("Encountered exception while updating player: Type: {} - Msg: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
    }

    public <T, G, X> T wrappedServiceExecution(G dto, X otherParam, BiFunction<G, X, T> func) {
        if(func == null)
            throw new InternalServerException("Passed Null Function in wrappedServiceExecution", null);

        try {
            return func.apply(dto, otherParam);
        } catch (NullPointerException | IllegalArgumentException e) {
            log.warn("Encountered exception while updating player: Type: {} - Msg: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new BadArgumentException(e.getMessage(), e);
        } catch (ServiceException e) {
            log.warn("Encountered exception while updating player: Type: {} - Msg: {}", e.getClass().getSimpleName(), e.getMessage());
            throw new InternalServerException(e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}