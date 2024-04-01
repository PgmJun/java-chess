package chess.dto;

import chess.view.Command;

import java.util.ArrayList;
import java.util.List;

public record CommandInfoDto(
        Command command,
        List<String> options) {

    public static CommandInfoDto of(final Command command, final String[] commandText) {
        List<String> options = new ArrayList<>();
        for (int i = 1; i < commandText.length; i++) {
            options.add(commandText[i]);
        }

        return new CommandInfoDto(command, options);
    }
}
