package chess.dto;

import chess.view.Command;

import java.util.List;

public record CommandInfoDto(
        Command command,
        List<String> options) {
    public static CommandInfoDto noneOptions(final Command command) {
        return new CommandInfoDto(command, List.of());
    }

    public static CommandInfoDto Options(final Command command, final List<String> options) {
        return new CommandInfoDto(command, options);
    }
}
