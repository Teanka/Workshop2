package pl.coderslab.model;

import pl.coderslab.util.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private UserGroup userGroup;

    public User() {
    }

    public User(String userName, String email, String password, UserGroup userGroup) {
        this.userName = userName;
        this.email = email;
        setPassword(password);
        this.userGroup = userGroup;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }


    public void saveToDB(Connection connection, int userGroupId) throws SQLException {
        UserGroup userGroup = UserGroup.loadUserGroupById(connection, userGroupId);
        if (userGroup != null) {
            if (this.id == 0) {
                String sql = "INSERT INTO users(user_name, email, password, user_group_id) VALUES (?, ?, ?,?)";
                String[] generatedColumns = {"ID"};
                PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
                preparedStatement.setString(1, this.userName);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.setInt(4, userGroup.getId());
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
            } else {
                String sql = "UPDATE users SET user_name=?, email=?, password=?, user_group_id =? where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, this.userName);
                preparedStatement.setString(2, this.email);
                preparedStatement.setString(3, this.password);
                preparedStatement.setInt(4, userGroup.getId());
                preparedStatement.setInt(5, this.id);
                preparedStatement.executeUpdate();
            }
        } else {
            System.out.println("Wrong User Group id, new user not saved to database. Please try again.");
        }
    }


    static public User loadUserById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM users where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getUser(resultSet, connection);
        }
        return null;
    }

    static public User loadUserByEmail(Connection connection, String email) throws SQLException {
        String sql = "SELECT * FROM users where email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getUser(resultSet, connection);
        }
        return null;
    }

    private static User getUser(ResultSet resultSet, Connection connection) throws SQLException {
        User loadedUser = new User();
        loadedUser.id = resultSet.getInt("id");
        loadedUser.userName = resultSet.getString("user_name");
        loadedUser.password = resultSet.getString("password");
        loadedUser.email = resultSet.getString("email");
        int userGroupId = resultSet.getInt("user_group_id");
        loadedUser.userGroup = UserGroup.loadUserGroupById(connection,userGroupId);
        return loadedUser;
    }

    static public User[] loadAllUsers(Connection connection) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User loadedUser = getUser(resultSet, connection);
            users.add(loadedUser);
        }
        User[] uArray = new User[users.size()];
        uArray = users.toArray(uArray);
        return uArray;
    }

    public void delete(Connection connection) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM users WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ",\n password='" + password + '\'' +
                ",\n userGroup=" + userGroup.toString() +
                '}';
    }
}
