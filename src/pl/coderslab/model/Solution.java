package pl.coderslab.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution(){}

    public Solution(Exercise exercise, User user) {
        this.exercise = exercise;
        this.user = user;
    }

    public Solution(String description, Exercise exercise, User user) {
        this.description = description;
        this.exercise = exercise;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void saveToDB(Connection connection) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(description, exercise_id, users_id ) VALUES (?,?,?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns);
            preparedStatement.setString(1, this.description);
            preparedStatement.setInt(2, this.exercise.getId());
            preparedStatement.setInt(3, this.user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());// nie działają godziny tak samo jak w System.currentTimeMillis()
            String sql = "UPDATE solution SET updated = ?, description=?, exercise_id = ?, users_id =? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, this.description);
            preparedStatement.setInt(3,this.exercise.getId());
            preparedStatement.setInt(4,this.user.getId());
            preparedStatement.setInt(5,this.id);
            preparedStatement.executeUpdate();
        }
    }

    static public Solution loadSolutionById(Connection connection, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            int exerciseId = resultSet.getInt("exercise_id");
            loadedSolution.exercise = Exercise.loadExerciseById(connection,exerciseId);
            int userId = resultSet.getInt("users_id");
            loadedSolution.user = User.loadUserById(connection,userId);
            return loadedSolution;
        }
        return null;
    }

    static public Solution[] loadAllSolutions(Connection connection) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            int exerciseId = resultSet.getInt("exercise_id");
            loadedSolution.exercise = Exercise.loadExerciseById(connection,exerciseId);
            int userId = resultSet.getInt("users_id");
            loadedSolution.user = User.loadUserById(connection,userId);
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    static public Solution[] loadAllSolutionsByUserId(Connection connection, int userId) throws SQLException{
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution WHERE users_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            int exerciseId = resultSet.getInt("exercise_id");
            loadedSolution.exercise = Exercise.loadExerciseById(connection,exerciseId);
            userId = resultSet.getInt("users_id");
            loadedSolution.user = User.loadUserById(connection,userId);
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        return uArray;
    }

    public static Solution[] loadAllSolutionsByExerciseId(Connection connection, int exerciseId) throws SQLException{
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution WHERE exercise_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,exerciseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            exerciseId = resultSet.getInt("exercise_id");
            loadedSolution.exercise = Exercise.loadExerciseById(connection,exerciseId);
            int userId = resultSet.getInt("users_id");
            loadedSolution.user = User.loadUserById(connection,userId);
            solutions.add(loadedSolution);
        }
        Solution[] uArray = new Solution[solutions.size()];
        uArray = solutions.toArray(uArray);
        //TODO posortować datą od najnowszego do najstarszego
        return uArray;

    }

    public void delete(Connection connection) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                ", exercise=" + exercise +
                ", user=" + user +
                '}';
    }
}
