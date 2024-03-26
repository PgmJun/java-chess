package chess.view;

public enum Command {
    START,
    END,
    MOVE,
    STATUS,
    ;

    public boolean isType(Command command) {
        return this == command;
    }

    public boolean isNotType(Command command) {
        return this != command;
    }
}
