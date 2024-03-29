package chess.view;

import chess.dto.CommandInfoDto;
import chess.view.matcher.CommandMatcher;

import java.util.Arrays;
import java.util.List;
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

    // TODO: 분기처리 리팩토링
    public CommandInfoDto readCommand() {
        String[] commandText = scanner.nextLine().split(" ");
        Command command = CommandMatcher.matchByText(commandText[0]);
        if (command.isType(Command.START) || command.isType(Command.END) || command.isType(Command.STATUS)) {
            return CommandInfoDto.noneOptions(command);
        }
        if (command.isType(Command.MOVE)) {
            validatePosition(commandText[1], commandText[2]);
            return CommandInfoDto.Options(command, List.of(commandText[1], commandText[2]));
        }
        throw new IllegalArgumentException("명령 입력 형식이 올바르지 않습니다.");
    }

    private void validatePosition(final String source, final String target) {
        if (source.length() != 2 || target.length() != 2) {
            throw new IllegalArgumentException("위치 입력 형식이 올바르지 않습니다.");
        }
    }

    public List<String> readGameCommand() {
        System.out.println("게임 생성 (game new)");
        System.out.println("기존 게임 이어하기 (game continue)");
        System.out.print("> ");

        return Arrays.asList(scanner.nextLine().split(" "));
    }

    private void validateLongType(final String input) {
        try {
            Long.parseLong(input);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("입력 형식이 정수형이 아닙니다.");
        }
    }
}
