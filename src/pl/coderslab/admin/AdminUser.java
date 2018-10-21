package pl.coderslab.admin;

import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class AdminUser {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = DbUtil.getConnection()) {
            boolean flag = true;
            String answer;
            int id;
            String userName;
            String email;
            String password;
            int userGroupId;
            UserGroup userGroup;
            User user;
            while (flag) {
                System.out.println(" Lista wszystkich użytkowników : " + Arrays.toString(User.loadAllUsers(connection)));
                System.out.println("\nWybierz jedną z opcji: " +
                        "\n \n" +
                        "add – dodanie nowego użytkownika,\n" +
                        "edit – edycja danych istniejącego użytkownika,\n" +
                        "delete – usunięcie użytkownika z bazy,\n" +
                        "quit – zakończenie programu.");
                answer = scanner.nextLine();
                switch (answer) {
                    case "add":
                        System.out.println("Podaj nazwę nowego użytkownika: ");
                        userName = scanner.nextLine();
                        System.out.println("Podaj email: ");
                        email = scanner.nextLine();
                        System.out.println("Podaj hasło: ");
                        password = scanner.nextLine();
                        System.out.println("Podaj nr id grupy, do której chcesz należeć. " +
                                "\n Istniejące grupy, do których możesz się dopisać:" +
                                "\n " + Arrays.toString(UserGroup.loadAllUserGroups(connection)));
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        userGroupId = scanner.nextInt();
                        scanner.nextLine();
                        userGroup = UserGroup.loadUserGroupById(connection, userGroupId);
                        user = new User(userName, email, password, userGroup);
                        user.saveToDB(connection);
                        System.out.println("Nowy użytkownik został zapisany do bazy.");
                        break;
                    case "edit":
                        System.out.println("Podaj id użytkownika, którego chcesz edytować: ");
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Podaj nową nazwę użytkownika: ");
                        userName = scanner.nextLine();
                        System.out.println("Podaj nowy email: ");
                        email = scanner.nextLine();
                        System.out.println("Podaj nowe hasło: ");
                        password = scanner.nextLine();
                        System.out.println("Podaj nr id grupy, do której chcesz należeć. " +
                                "\n Istniejące grupy, do których możesz się dopisać:" +
                                "\n " + Arrays.toString(UserGroup.loadAllUserGroups(connection)));
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        userGroupId = scanner.nextInt();
                        scanner.nextLine();
                        userGroup = UserGroup.loadUserGroupById(connection, userGroupId);
                        user = User.loadUserById(connection, id);
                        user.setUserName(userName);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setUserGroup(userGroup);
                        user.saveToDB(connection);
                        System.out.println("Dane użytkownika zostały zmienione.");
                        break;
                    case "delete":
                        System.out.println("Podaj id użytkownika, którego chcesz wykasować z bazy: ");
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        id = scanner.nextInt();
                        scanner.nextLine();
                        user = User.loadUserById(connection, id);
                        user.delete(connection);
                        System.out.println("Wybrany użytkownik został usunięty z bazy");
                        break;
                    case "quit":
                        flag = false;
                        break;
                    default:
                        System.out.println("Musisz wybrać jedną z wymienionych opcji:");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
