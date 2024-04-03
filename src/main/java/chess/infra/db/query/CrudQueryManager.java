package chess.infra.db.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

sealed class CrudQueryManager permits InsertQueryManager, SelectQueryManager, UpdateQueryManager, DeleteQueryManager {
    final PreparedStatement pstmt;

    protected CrudQueryManager(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    protected void setStringParameter(final int paramIndex, final String value) throws SQLException {
        this.pstmt.setString(paramIndex, value);
    }

    protected void setLongParameter(final int paramIndex, final Long value) throws SQLException {
        this.pstmt.setLong(paramIndex, value);
    }
}
