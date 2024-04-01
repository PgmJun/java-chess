package chess.infra.db;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionFunction<T> {
    T execute(Connection conn) throws SQLException;
}
