package chess.infra.db;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    public static <T> T read(Connection conn, TransactionFunction<T> logic) {
        try {
            return logic.execute(conn);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            DBConnectionPool.releaseConnection(conn);
        }
    }

    public static <T> T cud(Connection conn, TransactionFunction<T> logic) throws SQLException {
        try {
            conn.setAutoCommit(false);
            T result = logic.execute(conn);
            conn.commit();

            return result;
        } catch (Exception exception) {
            conn.rollback();
            throw new RuntimeException(exception);
        } finally {
            conn.setAutoCommit(true);
            DBConnectionPool.releaseConnection(conn);
        }
    }
}
