package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectQueryManager {
    private Connection conn;
    private PreparedStatement pstmt;
    private int parameterIndex;

    public SelectQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.conn = conn;
        this.pstmt = conn.prepareStatement(query);
        this.parameterIndex = 1;
    }

    public SelectQueryManager setString(final String value) throws SQLException {
        this.pstmt.setString(parameterIndex++, value);
        return this;
    }

    public SelectQueryManager setLong(final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex++, value);
        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        return pstmt.executeQuery();
    }
}
