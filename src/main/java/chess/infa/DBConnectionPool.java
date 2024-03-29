package chess.infa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class DBConnectionPool {

    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=KTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private static final int MAX_CONNECTION_SIZE = 3;

    private static final Deque<Connection> CONNECTION_POOL;

    static {
        CONNECTION_POOL = new ArrayDeque<>();
        for (int i = 0; i < MAX_CONNECTION_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
                CONNECTION_POOL.add(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection() {
        if (!CONNECTION_POOL.isEmpty()) {
            return CONNECTION_POOL.pop();
        }
        throw new RuntimeException("현재 남아있는 커넥션이 없습니다.");
    }

    public static void releaseConnection(final Connection connection) {
        CONNECTION_POOL.add(connection);
    }
}
