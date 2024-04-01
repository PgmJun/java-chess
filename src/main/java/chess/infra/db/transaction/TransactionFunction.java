package chess.infra.db.transaction;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface TransactionFunction<T> {
    T execute(Connection conn) throws SQLException;
}
