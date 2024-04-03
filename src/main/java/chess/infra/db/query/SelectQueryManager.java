package chess.infra.db.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public final class SelectQueryManager extends CrudQueryManager {
    private static final Pattern SELECT_PATTERN = Pattern.compile("(?i)^select\s.*");

    public SelectQueryManager(Connection conn, String query) throws SQLException {
        super(conn.prepareStatement(query));
        validate(query);
    }

    private void validate(String query) {
        if (!SELECT_PATTERN.matcher(query).matches()) {
            throw new IllegalArgumentException("조회 쿼리는 SELECT 구문으로 시작해야합니다.");
        }
    }

    public SelectQueryManager setString(final int paramIndex, final String value) throws SQLException {
        setStringParameter(paramIndex, value);
        return this;
    }

    public SelectQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        setLongParameter(paramIndex, value);
        return this;
    }

    public ResultSet execute() throws SQLException {
        return super.executeQuery();
    }
}
