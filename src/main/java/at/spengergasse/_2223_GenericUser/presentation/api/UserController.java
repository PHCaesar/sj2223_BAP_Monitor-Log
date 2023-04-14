package at.spengergasse._2223_GenericUser.presentation.api;

import at.spengergasse._2223_GenericUser.presentation.AbstractRestController;
import at.spengergasse._2223_GenericUser.service.UserService;
import at.spengergasse._2223_GenericUser.service.dto.GenericUserDto;
import at.spengergasse._2223_GenericUser.service.dto.command.MutateGenericUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(UserController.baseURL)
public class UserController extends AbstractRestController {

    private final UserService userService;
    public static final String baseURL = "/api/user";
    public static final String PATH_INDEX = "/";


    @GetMapping({"", PATH_INDEX})
    public HttpEntity<List<GenericUserDto>> getAllUsers() {
        log.debug("Starting to fetch all users for HTTP request 'getAllUsers'");
        List<GenericUserDto> fetchedWoodWorkers = wrappedServiceExecution(userService::getUsers);

        return (fetchedWoodWorkers.isEmpty())
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(fetchedWoodWorkers);
    }

    @GetMapping({"/{username}", PATH_INDEX})
    public HttpEntity<GenericUserDto> getUserByUsername(@Valid String username) {
        log.debug("Starting to fetch the user for HTTP request 'getUserByUsername'");
        return wrappedServiceExecution(() -> userService.getUser(username))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PostMapping({"",PATH_INDEX})
    public HttpEntity<GenericUserDto> createUser(@Valid @RequestBody MutateGenericUserCommand command){
        log.debug("Starting to create the user for HTTP request 'createUser'");
        GenericUserDto userDto = new GenericUserDto(wrappedServiceExecution(command,userService::createUser));
        return ResponseEntity.created(URI.create(userDto.username())).body(userDto);
    }

}
