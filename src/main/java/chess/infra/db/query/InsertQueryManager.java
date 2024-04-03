package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertQueryManager {
    private PreparedStatement pstmt;

    public InsertQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public InsertQueryManager setString(final int paramIndex, final String value) throws SQLException {
        this.pstmt.setString(paramIndex, value);
        return this;
    }

    public InsertQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        this.pstmt.setLong(paramIndex, value);
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
