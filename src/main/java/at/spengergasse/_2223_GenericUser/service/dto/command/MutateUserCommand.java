package at.spengergasse._2223_GenericUser.service.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MutateUserCommand {

    private String name;
    private String MutateFirstname;
    private String MutateLastname;
    private String mutatePassword;
    private LocalDateTime registerDate;
    private LocalDateTime MutateBirthDate;
    private Integer Version;

}
