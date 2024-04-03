package chess.infra.db.query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

sealed class CrudQueryManager permits InsertQueryManager, SelectQueryManager, UpdateQueryManager, DeleteQueryManager {
    private final PreparedStatement pstmt;

    protected CrudQueryManager(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    protected void setStringParameter(final int paramIndex, final String value) throws SQLException {
        this.pstmt.setString(paramIndex, value);
    }

    protected void setLongParameter(final int paramIndex, final Long value) throws SQLException {
        this.pstmt.setLong(paramIndex, value);
    }

    protected void setIntParameter(final int paramIndex, final int value) throws SQLException {
        this.pstmt.setInt(paramIndex, value);
    }

    protected ResultSet executeQuery() throws SQLException {
        return pstmt.executeQuery();
    }

    protected void executeUpdate() throws SQLException {
        pstmt.executeUpdate();
    }

    public PreparedStatement getPreparedStatement() {
        return pstmt;
    }
}
