package pl.coderslab.admin;

import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class AdminUserGroup {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = DbUtil.getConnection()) {
            boolean flag = true;
            String answer;
            String name;
            int id;
            UserGroup userGroup;
            while (flag) {
                System.out.println(" Lista wszystkich grup użytkowników : " + Arrays.toString(UserGroup.loadAllUserGroups(connection)));
                System.out.println("\nWybierz jedną z opcji: " +
                        "\n \n" +
                        "add – dodanie grupy,\n" +
                        "edit – edycja grupy,\n" +
                        "delete – edycja grupy,\n" +
                        "quit – zakończenie programu.");
                answer = scanner.nextLine();
                switch (answer) {
                    case "add":
                        System.out.println("Podaj nazwę nowej grupy: ");
                        name = scanner.nextLine();
                        userGroup = new UserGroup(name);
                        userGroup.saveToDB(connection);
                        System.out.println("Nowa grupa użytkowników dopisana do bazy.");
                        break;
                    case "edit":
                        System.out.println("Podaj id grupy, której nazwę chcesz edytować: ");
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Podaj zmienioną nazwę grupy: ");
                        name = scanner.nextLine();
                        userGroup = UserGroup.loadUserGroupById(connection, id);
                        userGroup.setName(name);
                        userGroup.saveToDB(connection);
                        System.out.println("Nazwa grupy została zmieniona w bazie.");
                        break;
                    case "delete":
                        System.out.println("Podaj id grupy, którą chcesz wykasować z bazy: ");
                        while (!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        id = scanner.nextInt();
                        scanner.nextLine();
                        userGroup = UserGroup.loadUserGroupById(connection, id);
                        userGroup.delete(connection);
                        System.out.println("Wybrana grupa została usunięta z bazy");
                        break;
                    case "quit":
                        flag = false;
                        break;
                    default:
                        System.out.println("Musisz wybrać jedną z wymienionych opcji:");
                }

            }
        } catch (SQLException|NullPointerException e) {
            e.printStackTrace();
        }
    }
}
