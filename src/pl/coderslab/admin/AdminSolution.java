package pl.coderslab.admin;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class AdminSolution{

        static Scanner scanner = new Scanner(System.in);

        public static void main(String[] args) {

            try (Connection connection = DbUtil.getConnection()) {
                boolean flag = true;
                String answer;
                int id;
                Solution solution;
                User user;
                Exercise exercise;
                while (flag) {
                    System.out.println("\nWybierz jedną z opcji: " +
                            "\n \n" +
                            "add – przypisywanie zadań do użytkowników,\n" +
                            "view – przeglądanie rozwiązań danego użytkownika,\n" +
                            "quit – zakończenie programu.");
                    answer = scanner.nextLine();
                    switch (answer) {
                        case "add":
                            System.out.println(" Lista wszystkich użytkowników : " + Arrays.toString(User.loadAllUsers(connection)));
                            System.out.println("Podaj id użytkownika: ");
                            while (!scanner.hasNextInt()) {
                                scanner.nextLine();
                                System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                            }
                            id = scanner.nextInt();
                            scanner.nextLine();
                            user = User.loadUserById(connection,id);
                            System.out.println(" Lista wszystkich zadań : " + Arrays.toString(Exercise.loadAllExercises(connection)));
                            System.out.println("Podaj id zadania, rozwiązanego przez tego użytkownika:");
                            while (!scanner.hasNextInt()) {
                                scanner.nextLine();
                                System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                            }
                            id = scanner.nextInt();
                            scanner.nextLine();
                            exercise =Exercise.loadExerciseById(connection,id);
                            solution = new Solution(exercise,user);
                            solution.saveToDB(connection);
                            break;
                        case "view":
                            System.out.println("Podaj id użytkownika, którego rozwiązania chcesz zobaczyć: ");
                            while (!scanner.hasNextInt()) {
                                scanner.nextLine();
                                System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                            }
                            id = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Rozwiązania użytkownika o numerze id " +id + ":");
                            System.out.println(Arrays.toString(Solution.loadAllSolutionsByUserId(connection,id)));
                            break;
                        case "quit":
                            flag = false;
                            break;
                        default:
                            System.out.println("Musisz wybrać jedną z wymienionych opcji:");
                    }

                }
            } catch (SQLException |NullPointerException e) {
                e.printStackTrace();
            }
        }




//    Zadanie 4
//    Program 4 – przypisywanie zadań
//
//    Program po uruchomieniu wyświetli w konsoli napis
//
//"Wybierz jedną z opcji:
//
//    add – przypisywanie zadań do użytkowników,
//    view – przeglądanie rozwiązań danego użytkownika,
//    quit – zakończenie programu."
//
//    Po wpisaniu i zatwierdzeniu odpowiedniej opcji program odpyta o dane:
//
//    jeśli wybrano add – wyświetli listę wszystkich użytkowników, odpyta o id, następnie wyświetli listę wszystkich zadań i zapyta o id zadania, utworzy i zapisze obiekt typu Solution.
//
//    Pole created wypełni się automatycznie, a pola updated i description mają zostać puste.
//    view – zapyta o id użytkownika, którego rozwiązania chcemy zobaczyć.
//
//    Po wykonaniu dowolnej z opcji, program ponownie zada pytanie o wybór opcji.

}
