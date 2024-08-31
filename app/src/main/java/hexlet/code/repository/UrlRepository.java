package hexlet.code.repository;

import hexlet.code.model.Url;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (url, created_at) VALUES (?, ?)";
        try (var conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong("id"));
                url.setCreatedAt(timestamp);
            } else {
                throw new SQLException("error");
            }
        }
    }

    public static Optional<Url> find(Long id) throws SQLException {
        var sql = "SELECT * FROM urls WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("url");
                Timestamp timestamp = resultSet.getTimestamp("created_at");
                Url url = new Url();
                url.setName(name);
                url.setId(id);
                url.setCreatedAt(timestamp);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM urls";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("url");
                Timestamp timestamp = resultSet.getTimestamp("created_at");
                Url url = new Url();
                url.setName(name);
                url.setId(id);
                url.setCreatedAt(timestamp);
                result.add(url);
            }
            return result;
        }
    }
}
