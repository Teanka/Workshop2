package pl.coderslab.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserGroup {
    private int id;
    private String name;

    public UserGroup(){}

    public UserGroup(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void saveToDB(Connection connection) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO user_group(name) VALUES (?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        }
    }

    static public UserGroup loadUserGroupById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM user_group where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.id = resultSet.getInt("id");
            loadedUserGroup.name = resultSet.getString("name");
            return loadedUserGroup;
        }
        return null;
    }

    static public UserGroup[] loadAllUserGroups(Connection connection) throws SQLException {
        ArrayList<UserGroup> userGroups = new ArrayList<UserGroup>();
        String sql = "SELECT * FROM user_group";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            UserGroup loadedUserGroup = new UserGroup();
            loadedUserGroup.id = resultSet.getInt("id");
            loadedUserGroup.name = resultSet.getString("name");
            userGroups.add(loadedUserGroup);}
        UserGroup[] uArray = new UserGroup[userGroups.size()]; uArray = userGroups.toArray(uArray);
        return uArray;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
