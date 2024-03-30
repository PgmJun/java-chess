package chess.view;

import java.util.Arrays;

public enum GameOption {
    NEW("new"),
    CONTINUE("continue");

    private final String text;

    GameOption(String text) {
        this.text = text;
    }

    public static GameOption findByText(final String inputText) {
        return Arrays.stream(values())
                .filter(gameOption -> gameOption.text.equals(inputText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게임 옵션입니다."));
    }
}
