package chess.domain.game;

import java.util.function.Function;

public enum ScoreRule {
    DEFAULT(s -> s),
    HALF(s -> s / 2),
    ;

    private final Function<Double, Double> function;

    ScoreRule(Function<Double, Double> function) {
        this.function = function;
    }

    public double calculate(final double score) {
        return function.apply(score);
    }
}
