package chess.view;

import chess.dto.CommandInfo;
import chess.view.matcher.CommandMatcher;

import java.util.Scanner;

public class InputView {
    private static final InputView INSTANCE = new InputView(new Scanner(System.in));

    private final Scanner scanner;

    private InputView(final Scanner scanner) {
        this.scanner = scanner;
    }

    public static InputView getInstance() {
        return INSTANCE;
    }

    public CommandInfo readCommand() {
        String[] commandText = scanner.nextLine().split(" ");
        if (commandText.length == 1) {
            return CommandInfo.fromNonMovable(CommandMatcher.matchByText(commandText[0]));
        }
        if (commandText.length == 3) {
            validatePosition(commandText[1], commandText[2]);
            return CommandInfo.ofMovable(
                    CommandMatcher.matchByText(commandText[0]), commandText[1], commandText[2]);
        }
        throw new IllegalArgumentException("명령 입력 형식이 올바르지 않습니다.");
    }

    private void validatePosition(final String source, final String target) {
        if (source.length() != 2 || target.length() != 2) {
            throw new IllegalArgumentException("위치 입력 형식이 올바르지 않습니다.");
        }
    }
}
