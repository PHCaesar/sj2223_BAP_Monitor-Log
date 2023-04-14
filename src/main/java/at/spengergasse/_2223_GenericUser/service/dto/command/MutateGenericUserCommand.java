package at.spengergasse._2223_GenericUser.service.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MutateGenericUserCommand {

    private String name;
    private String mutateFirstname;
    private String mutateLastname;
    private String mutatePassword;
    private LocalDateTime registerDate;
    private LocalDateTime mutateBirthDate;
    private Integer version;

}
