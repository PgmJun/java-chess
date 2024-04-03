package chess.infra.db;

import java.sql.Connection;
import java.util.ArrayDeque;
import java.util.Deque;

public class DBConnectionPool {
    private static final int MAX_CONNECTION_SIZE = 3;
    private static final Deque<Connection> CONNECTION_POOL;

    static {
        CONNECTION_POOL = new ArrayDeque<>();
        for (int i = 0; i < MAX_CONNECTION_SIZE; i++) {
            CONNECTION_POOL.add(DBConnectionGenerator.generate());
        }
    }

    synchronized public static Connection getConnection() {
        if (!CONNECTION_POOL.isEmpty()) {
            return CONNECTION_POOL.pop();
        }
        throw new RuntimeException("현재 남아있는 커넥션이 없습니다.");
    }

    synchronized public static void releaseConnection(final Connection connection) {
        CONNECTION_POOL.add(connection);
    }
}
