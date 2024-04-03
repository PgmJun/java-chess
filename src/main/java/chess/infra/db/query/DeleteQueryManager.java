package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQueryManager {
    private PreparedStatement pstmt;

    public DeleteQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.pstmt = conn.prepareStatement(query);
    }

    public DeleteQueryManager setString(final int paramIndex, final String value) throws SQLException {
        this.pstmt.setString(paramIndex, value);
        return this;
    }

    public DeleteQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        this.pstmt.setLong(paramIndex, value);
        return this;
    }

    public DeleteQueryManager executeUpdate() throws SQLException {
        pstmt.executeUpdate();
        return this;
    }
}
