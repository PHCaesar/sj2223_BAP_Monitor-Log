package at.spengergasse._2223_GenericUser.service;

import at.spengergasse._2223_GenericUser.domain.GenericUser;
import at.spengergasse._2223_GenericUser.persistence.UserRepository;
import at.spengergasse._2223_GenericUser.service.dto.GenericUserDto;
import at.spengergasse._2223_GenericUser.service.dto.command.MutateGenericUserCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public List<GenericUserDto> getUsers(){
        return userRepository.findAll().stream().map(GenericUserDto::new).toList();
    }

    public Optional<GenericUserDto> getUser(String username){
        return userRepository.findByUsername(username).map(GenericUserDto::new);
    }

    @Transactional(readOnly = false)
    public GenericUserDto partiallyUpdateUser(String username, MutateGenericUserCommand newUserInformation){
        log.debug("updated GenericUser with the username: {}",username);
        Objects.requireNonNull(username);
        Objects.requireNonNull(newUserInformation);

        Optional<GenericUser> user = userRepository.findByUsername(username);
        Objects.requireNonNull(user);

        userRepository.deleteByUsername(username);

        GenericUser updatingEntity = GenericUser.builder()
                .birthDate(newUserInformation.getMutateBirthDate()==null ? user.get().getBirthDate() : newUserInformation.getMutateBirthDate())
                .firstname(newUserInformation.getMutateFirstname()==null ? user.get().getFirstname() : newUserInformation.getMutateFirstname())
                .lastname(newUserInformation.getMutateLastname()==null ? user.get().getLastname() : newUserInformation.getMutateLastname())
                .password(newUserInformation.getMutatePassword()==null ? user.get().getPassword() : newUserInformation.getMutatePassword())
                .registerDate(newUserInformation.getRegisterDate()==null ? user.get().getRegisterDate() : newUserInformation.getRegisterDate())
                .username(newUserInformation.getName()==null ? user.get().getUsername() : newUserInformation.getName())
                .version(newUserInformation.getVersion()==null ? user.get().getVersion() : newUserInformation.getVersion())
                .build();

        userRepository.save(updatingEntity);
        log.info("GenericUser with the name {} has been updated", updatingEntity.getUsername());

        return new GenericUserDto(updatingEntity);
    }

    @Transactional(readOnly = false)
    public GenericUserDto deleteUser(String username)
    {
        Objects.requireNonNull(username);

        GenericUserDto deletable = userRepository.findByUsername(username).map(GenericUserDto::new).get();

        userRepository.deleteByUsername(username);
        log.info("GenericUser with the name {} has been deleted successfully", username);

        return deletable;
    }

    @Transactional
    public GenericUser createUser(MutateGenericUserCommand userCommand){
        log.debug("Try to create a new User with the following params: {}",userCommand);

        Objects.requireNonNull(userCommand);
        GenericUser creatable = GenericUser.builder()
                .birthDate(userCommand.getMutateBirthDate())
                .registerDate(userCommand.getRegisterDate())
                .version(userCommand.getVersion())
                .firstname(userCommand.getMutateFirstname())
                .lastname(userCommand.getMutateLastname())
                .password(userCommand.getMutatePassword())
                .username(userCommand.getName())
                .build();

        log.info("newly created GenericUser Entity {}", creatable);

        return userRepository.save(creatable);

    }


}
