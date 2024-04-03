package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectQueryManager {
    private PreparedStatement pstmt;

    public SelectQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.pstmt = conn.prepareStatement(query);
    }

    public SelectQueryManager setString(final int paramIndex, final String value) throws SQLException {
        this.pstmt.setString(paramIndex, value);
        return this;
    }

    public SelectQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        this.pstmt.setLong(paramIndex, value);
        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        return pstmt.executeQuery();
    }
}
