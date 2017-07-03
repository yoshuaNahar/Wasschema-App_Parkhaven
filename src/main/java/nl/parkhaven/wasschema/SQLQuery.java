package nl.parkhaven.wasschema;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("unused")
final class SQLQuery {

    public static void main(String[] args) {
        SQLQuery pg = new SQLQuery();
        pg.addHouseNumbersQuery();
    }

    private void addHouseNumbersQuery() {
        Path path = FileSystems.getDefault().getPath("/home/yoshua/Desktop", "allHouseNumbersAbleToMakeAccount.txt");
        System.out.println(
                path.toString());
        try {
            Files.lines(path).forEach(i ->
                System.out.println(
                        "INSERT INTO " +
                                "house_numbers (house_number)" +
                                "VALUES ('" + i.replaceAll("\\s", "").toUpperCase() + "');")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dag_tijd_insert_query() {
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 8; j++) {
                for (int k = 1; k < 14; k++) {
                    System.out.println("INSERT INTO week_dag_tijd (dag_id, tijd_id, week_id) VALUES (" + j + ", " + k
                            + ", " + i + ");");
                }
            }
        }
    }

    private void dag_tijd_insert() {
        int id = 1;
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 8; j++) {
                for (int k = 1; k < 14; k++) {
                    System.out.println(id++ + ", " + j + ", " + k + ", " + i);
                }
            }
        }
    }

    private void wasschema_insert_query() {
        for (int i = 1; i < 13; i++) {
            for (int j = 1; j < 183; j++) {
                System.out.println(
                        "INSERT INTO wasschema (week_dag_tijd_id, wasmachine_id) VALUES (" + j + ", " + i + ");");
            }
        }
    }

    private void wasschema_insert_query2() {
        for (int i = 1; i < 13; i++) {
            for (int j = 1; j < 183; j++) {
                System.out.println(
                        j + ", " + i + ", ");
            }
        }
    }

    private void testRegex() {
        String huisnummer = "200a";
        System.out.println(huisnummer.toUpperCase().matches("[1-9]{1}[0-9]{0,2}[A-F]?"));
    }

}
