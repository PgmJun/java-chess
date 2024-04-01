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

    public CommandInfoDto readCommand() {
        String[] commandText = scanner.nextLine().split(" ");
        Command command = CommandMatcher.matchByText(commandText[0]);

        return CommandInfoDto.of(command, commandText);
    }

    public GameOption readGameOption() {
        System.out.println("> 게임 생성 : game new");
        System.out.println("> 기존 게임 이어하기 : game continue");
        System.out.print("> ");
        List<String> gameOption = Arrays.asList(scanner.nextLine().split(" "));
        validateGameOption(gameOption);
        return GameOption.findByText(gameOption.get(1));
    }

    private void validateGameOption(final List<String> gameCommand) {
        if (gameCommand.size() != 2 || !gameCommand.get(0).equals("game")) {
            throw new IllegalArgumentException("게임 명령어 입력 형식이 올바르지 않습니다.");
        }
    }
}
