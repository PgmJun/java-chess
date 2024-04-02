package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQueryManager {
    private String query = "";
    private Connection conn;
    private PreparedStatement pstmt;

    public SelectQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.conn = conn;
        this.query = query;
        this.pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public SelectQueryManager setString(final int parameterIndex, final String value) throws SQLException {
        this.pstmt.setString(parameterIndex, value);
        return this;
    }

    public SelectQueryManager setLong(final int parameterIndex, final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex, value);
        return this;
    }

    public ResultSet executeQuery() throws SQLException {
        return pstmt.executeQuery();
    }
}
