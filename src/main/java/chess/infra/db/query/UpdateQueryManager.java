package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateQueryManager {
    private PreparedStatement pstmt;

    public UpdateQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.pstmt = conn.prepareStatement(query);
    }

    public UpdateQueryManager setString(final int paramIndex, final String value) throws SQLException {
        this.pstmt.setString(paramIndex, value);
        return this;
    }

    public UpdateQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        this.pstmt.setLong(paramIndex, value);
        return this;
    }

    public UpdateQueryManager executeUpdate() throws SQLException {
        pstmt.executeUpdate();
        return this;
    }
}
