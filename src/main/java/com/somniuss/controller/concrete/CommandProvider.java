package com.somniuss.controller.concrete;

import java.util.HashMap;
import java.util.Map;

import com.somniuss.service.UserService;

public class CommandProvider {
    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider(UserService userService) {
        // Передача зависимости в команды
        commands.put(CommandName.DO_AUTH, new com.somniuss.controller.concrete.impl.DoAuth(userService));
        commands.put(CommandName.DO_REGISTRATION, new com.somniuss.controller.concrete.impl.DoRegistration(userService));

        commands.put(CommandName.GO_TO_REGISTRATION_PAGE, new com.somniuss.controller.concrete.impl.GoToRegistrationPage());
        commands.put(CommandName.GO_TO_INDEX_PAGE, new com.somniuss.controller.concrete.impl.GoToIndexPage());
        commands.put(CommandName.GO_TO_GLITCH_PAGE, new com.somniuss.controller.concrete.impl.GoToGlitchPage());
        commands.put(CommandName.GO_TO_ATMOSPHERE_PAGE, new com.somniuss.controller.concrete.impl.GoToAtmospherePage());

        commands.put(CommandName.NO_SUCH_COMMAND, new com.somniuss.controller.concrete.NoSuchCommand());
    }

    public Command takeCommand(String userCommand) {
        CommandName commandName;
        try {
            commandName = CommandName.valueOf(userCommand.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            commandName = CommandName.NO_SUCH_COMMAND;
        }

        return commands.getOrDefault(commandName, new com.somniuss.controller.concrete.NoSuchCommand());
    }
}
