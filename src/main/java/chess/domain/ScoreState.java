package chess.domain;

import java.util.function.Function;

public enum ScoreState {
    DEFAULT(s -> s),
    HALF(s -> s / 2),
    ;

    private final Function<Double, Double> function;

    ScoreState(Function<Double, Double> function) {
        this.function = function;
    }

    public double calculate(final double score) {
        return function.apply(score);
    }
}
