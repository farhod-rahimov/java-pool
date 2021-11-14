package school21.spring.service.repositories; 

import school21.spring.service.models.*;
import org.springframework.jdbc.core.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();

            user.setId(resultSet.getLong(1));
            user.setEmail(resultSet.getString(2));
            return user;
        }
    }

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    };

    @Override
    public User findById(Long id) {
        return this.jdbcTemplate.query("SELECT * FROM users WHERE id = ?", new Object[]{id}, new UserMapper())
                        .stream().findAny().orElse(null);
    };

    @Override
    public List<User> findAll() {
        return this.jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    };

    @Override
    public void save(User entity) {
        this.jdbcTemplate.update("INSERT INTO users (id, email) VALUES(?, ?)", entity.getId(), entity.getEmail());
    };

    @Override
    public void update(User entity) {
        this.jdbcTemplate.update("UPDATE users SET email = ? WHERE id = ?", entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    };

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users;

        users = this.jdbcTemplate.query("SELECT * FROM users WHERE email = ?", new Object[]{email}, new UserMapper());

        if (users.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    };
}