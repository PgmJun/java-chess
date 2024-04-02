package chess.infra.db.query;

import java.sql.Connection;

public class QueryManager {

    public static ConnectedQueryManager setConnection(final Connection conn) {
        return new ConnectedQueryManager(conn);
    }
}
