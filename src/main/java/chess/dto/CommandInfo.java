package chess.dto;

import chess.domain.Command;

public record CommandInfo(
        Command command,
        String source,
        String target) {
    public static CommandInfo fromNonMovable(final Command command) {
        return new CommandInfo(command, "", "");
    }

    public static CommandInfo ofMovable(final Command command, final String source, final String target) {
        return new CommandInfo(command, source, target);
    }
}
