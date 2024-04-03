package chess.infra.db.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfterExecuteQueryManager {

    private final PreparedStatement pstmt;

    public AfterExecuteQueryManager(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return pstmt.getGeneratedKeys();
    }
}
