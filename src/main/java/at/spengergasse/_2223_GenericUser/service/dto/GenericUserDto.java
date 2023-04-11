package at.spengergasse._2223_GenericUser.service.dto;

import at.spengergasse._2223_GenericUser.domain.GenericUser;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public record GenericUserDto(String username, String firstname, String lastname, String password, LocalDateTime registerDate, LocalDateTime birthDate) {
    public GenericUserDto(GenericUser user){
        this(user.getUsername(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getRegisterDate(), user.getBirthDate());
        log.debug("Created GenericUserDto from {}",user);
    }
}
