package chess.dto;

import chess.domain.Command;

public record CommandInfoDto(
        Command command,
        String source,
        String target) {
    public static CommandInfoDto fromNonMovable(final Command command) {
        return new CommandInfoDto(command, "", "");
    }

    public static CommandInfoDto ofMovable(final Command command, final String source, final String target) {
        return new CommandInfoDto(command, source, target);
    }
}
