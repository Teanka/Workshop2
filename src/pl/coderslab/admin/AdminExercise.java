package pl.coderslab.admin;

import pl.coderslab.model.Exercise;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class AdminExercise {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection connection = DbUtil.getConnection()) {
            boolean flag = true;
            String answer;
            String title;
            String description;
            int id;
            Exercise exercise;
            while (flag) {
                System.out.println(" Lista wszystkich zadań : " + Arrays.toString(Exercise.loadAllExercises(connection)));
                System.out.println("\nWybierz jedną z opcji: " +
                        "\n \n" +
                        "add – dodanie zadania,\n" +
                        "edit – edycja zadania,\n" +
                        "delete – usunięcie zadania z bazy danych,\n" +
                        "quit – zakończenie programu.");
                answer = scanner.nextLine();
                switch(answer){
                    case "add":
                        System.out.println("Podaj tytuł nowego zadania: ");
                        title = scanner.nextLine();
                        System.out.println("Podaj opis nowego zadania: ");
                        description = scanner.nextLine();
                        exercise = new Exercise(title,description);
                        exercise.saveToDB(connection);
                        System.out.println("Zadanie zapisane do bazy.");
                        break;
                    case "edit":
                        System.out.println("Podaj id zadania, które chcesz edytować: ");
                        while(!scanner.hasNextInt()){
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        id= scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Podaj zmieniony tytuł zadania: ");
                        title = scanner.nextLine();
                        System.out.println("Podaj zmieniony opis zadania: ");
                        description = scanner.nextLine();
                        exercise = Exercise.loadExerciseById(connection,id);
                        exercise.setTitle(title);
                        exercise.setDescription(description);
                        exercise.saveToDB(connection);
                        System.out.println("Zadanie zostało zmienione w  bazie.");
                        break;
                    case "delete":
                        System.out.println("Podaj id zadania, które chcesz wykasować z bazy: ");
                        while(!scanner.hasNextInt()){
                            scanner.nextLine();
                            System.out.println("Podany numer jest nieprawidłowy, spróbuj jeszcze raz: ");
                        }
                        id= scanner.nextInt();
                        scanner.nextLine();
                        exercise = Exercise.loadExerciseById(connection,id);
                        exercise.delete(connection);
                        System.out.println("Wybrane zadanie zostało usunięte z bazy");
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


//    Program po uruchomieniu wyświetli na konsoli listę wszystkich zadań.
//
//    Następnie wyświetli w konsoli napis
//
//"Wybierz jedną z opcji:
//
//    add – dodanie zadania,
//    edit – edycja zadania,
//    delete – edycja zadania,
//    quit – zakończenie programu."
//
//    Po wpisaniu i zatwierdzeniu odpowiedniej opcji program odpyta o następujące dane:
//
//    w przypadku add – o wszystkie dane występujące w klasie pl.coderslab.model.Exercise bez id,
//    po wybraniu edit – wszystkie dane występujące w klasie Exercise oraz id,
//    jeśli wybrano delete – zapyta o id zadania które należy usunąć.
//
//    Po wykonaniu dowolnej z opcji, program ponownie wyświetli listę danych i zada pytanie o wybór opcji.

}
