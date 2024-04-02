package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateQueryManager {
    private Connection conn;
    private PreparedStatement pstmt;
    private int parameterIndex;

    public UpdateQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.conn = conn;
        this.pstmt = conn.prepareStatement(query);
        this.parameterIndex = 1;
    }

    public UpdateQueryManager setString(final String value) throws SQLException {
        this.pstmt.setString(parameterIndex++, value);
        return this;
    }

    public UpdateQueryManager setLong(final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex++, value);
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
