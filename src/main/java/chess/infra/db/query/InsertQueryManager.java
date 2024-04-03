package chess.infra.db.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public final class InsertQueryManager extends CrudQueryManager {
    private static final Pattern INSERT_PATTERN = Pattern.compile("(?i)^insert\s.*");

    public InsertQueryManager(Connection conn, String query) throws SQLException {
        super(conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS));
        validate(query);
    }

    private void validate(String query) {
        if (!INSERT_PATTERN.matcher(query).matches()) {
            throw new IllegalArgumentException("삽입 쿼리는 INSERT 구문으로 시작해야합니다.");
        }
    }

    public InsertQueryManager setString(final int paramIndex, final String value) throws SQLException {
        setStringParameter(paramIndex, value);
        return this;
    }

    public InsertQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        setLongParameter(paramIndex, value);
        return this;
    }

    public AfterExecuteQueryManager execute() throws SQLException {
        super.executeUpdate();
        return new AfterExecuteQueryManager(super.getPreparedStatement());
    }
}
