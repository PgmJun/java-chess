package chess.infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionGenerator {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "chess"; //  MySQL 서버 아이디
    private static final String PASSWORD = "chess"; // MySQL 서버 비밀번호

    static Connection generate() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            throw new RuntimeException("DB 연결에 실패하였습니다.");
        }
    }
}
