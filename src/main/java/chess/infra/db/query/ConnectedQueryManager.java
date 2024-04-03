package chess.infra.db.query;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectedQueryManager {
    private Connection conn;

    public ConnectedQueryManager(final Connection conn) {
        this.conn = conn;
    }

    public InsertQueryManager insert(final String query) throws SQLException {
        return new InsertQueryManager(conn, query);
    }

    public SelectQueryManager select(final String query) throws SQLException {
        return new SelectQueryManager(conn, query);
    }

    public UpdateQueryManager update(final String query) throws SQLException {
        return new UpdateQueryManager(conn, query);
    }

    public DeleteQueryManager delete(final String query) throws SQLException {
        return new DeleteQueryManager(conn, query);
    }
}
