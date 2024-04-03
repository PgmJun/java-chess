package chess.infra.db.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public final class DeleteQueryManager extends CrudQueryManager {
    private static final Pattern DELETE_PATTERN = Pattern.compile("(?i)^delete\s.*");

    public DeleteQueryManager(Connection conn, String query) throws SQLException {
        super(conn.prepareStatement(query));
        validate(query);
    }

    private void validate(String query) {
        if (!DELETE_PATTERN.matcher(query).matches()) {
            System.out.println(query);
            throw new IllegalArgumentException("삭제 쿼리는 DELETE 구문으로 시작해야합니다.");
        }
    }

    public DeleteQueryManager setString(final int paramIndex, final String value) throws SQLException {
        setStringParameter(paramIndex, value);
        return this;
    }

    public DeleteQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        setLongParameter(paramIndex, value);
        return this;
    }

    public void execute() throws SQLException {
        super.executeUpdate();
    }
}
