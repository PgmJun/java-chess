package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQueryManager {
    private String query = "";
    private Connection conn;
    private PreparedStatement pstmt;

    public DeleteQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.conn = conn;
        this.query = query;
        this.pstmt = conn.prepareStatement(query);
    }

    public DeleteQueryManager setString(final int parameterIndex, final String value) throws SQLException {
        this.pstmt.setString(parameterIndex, value);
        return this;
    }

    public DeleteQueryManager setLong(final int parameterIndex, final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex, value);
        return this;
    }

    public DeleteQueryManager executeUpdate() throws SQLException {
        pstmt.executeUpdate();
        return this;
    }
}
