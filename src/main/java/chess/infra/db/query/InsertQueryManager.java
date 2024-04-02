package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertQueryManager {
    private String query = "";
    private Connection conn;
    private PreparedStatement pstmt;

    public InsertQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.conn = conn;
        this.query = query;
        this.pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public InsertQueryManager setString(final int parameterIndex, final String value) throws SQLException {
        this.pstmt.setString(parameterIndex, value);
        return this;
    }

    public InsertQueryManager setLong(final int parameterIndex, final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex, value);
        return this;
    }

    public InsertQueryManager executeUpdate() throws SQLException {
        pstmt.executeUpdate();
        return this;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return pstmt.getGeneratedKeys();
    }
}
