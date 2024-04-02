package chess.infra.db.query;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectedQueryManager {
    private Connection conn;

    public ConnectedQueryManager(Connection conn) {
        this.conn = conn;
    }

    public InsertQueryManager insert(String query) throws SQLException {
        return new InsertQueryManager(conn, query);
    }

    public SelectQueryManager select(String query) throws SQLException {
        return new SelectQueryManager(conn, query);
    }

    public UpdateQueryManager update(String query) throws SQLException {
        return new UpdateQueryManager(conn, query);
    }

    public DeleteQueryManager delete(String query) throws SQLException {
        return new DeleteQueryManager(conn, query);
    }
}
