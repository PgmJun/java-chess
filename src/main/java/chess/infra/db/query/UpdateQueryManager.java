package chess.infra.db.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public final class UpdateQueryManager extends CrudQueryManager {
    private static final Pattern UPDATE_PATTERN = Pattern.compile("(?i)^update\s.*");

    public UpdateQueryManager(Connection conn, String query) throws SQLException {
        super(conn.prepareStatement(query));
        validate(query);
    }

    private void validate(String query) {
        if (!UPDATE_PATTERN.matcher(query).matches()) {
            throw new IllegalArgumentException("수정 쿼리는 UPDATE 구문으로 시작해야합니다.");
        }
    }

    public UpdateQueryManager setString(final int paramIndex, final String value) throws SQLException {
        setStringParameter(paramIndex, value);
        return this;
    }

    public UpdateQueryManager setLong(final int paramIndex, final Long value) throws SQLException {
        setLongParameter(paramIndex, value);
        return this;
    }

    public void execute() throws SQLException {
        super.executeUpdate();
    }
}
