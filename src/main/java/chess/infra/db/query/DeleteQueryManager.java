package chess.infra.db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQueryManager {
    private PreparedStatement pstmt;
    private int parameterIndex;

    public DeleteQueryManager(Connection conn, String query) throws SQLException {
        super();
        this.pstmt = conn.prepareStatement(query);
        this.parameterIndex = 1;
    }

    public DeleteQueryManager setString(final String value) throws SQLException {
        this.pstmt.setString(parameterIndex++, value);
        return this;
    }

    public DeleteQueryManager setLong(final Long value) throws SQLException {
        this.pstmt.setLong(parameterIndex++, value);
        return this;
    }

    public DeleteQueryManager executeUpdate() throws SQLException {
        pstmt.executeUpdate();
        return this;
    }
}
