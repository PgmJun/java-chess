package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateQueryManager {
    private String query = "";
    private Connection conn;
    private PreparedStatement pstmt;

    public UpdateQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.conn = conn;
        this.query = query;
        this.pstmt = conn.prepareStatement(query);
    }

    public UpdateQueryManager setString(final int parameterIndex, final String value) throws SQLException {
        this.pstmt.setString(parameterIndex, value);
        return this;
    }

    public UpdateQueryManager setLong(final int parameterIndex, final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex, value);
        return this;
    }

    public UpdateQueryManager executeUpdate() throws SQLException {
        pstmt.executeUpdate();
        return this;
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return pstmt.getGeneratedKeys();
    }
}
