import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from users");
        System.out.println("-------------------------------------");
        System.out.println("Пользователи, которым больше 18 лет:");
        while (result.next()) {
            if(Integer.parseInt(result.getString("age")) > 18){
                System.out.println(result.getInt("Id") + " " + result.getString("name"));
            }
        }
        System.out.println("-------------------------------------");
        System.out.println("Введите 6 пользователей для добавления в таблицу:");
        Scanner scanner = new Scanner(System.in);
        List<String> queryParams = new ArrayList<>();
        int count = 0;
        while(count < 6){
            System.out.println("Enter user number " + (count + 1) + " params:");
            String firstName = scanner.nextLine();
            String secondName = scanner.nextLine();
            String age = scanner.nextLine();

            queryParams.add(firstName);
            queryParams.add(secondName);
            queryParams.add(age);
            count += 1;
        }

        String sqlInsertUser = "insert into users(name, surname, age) " +
                "values (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertUser);
        for(int i = 0;i < queryParams.size();i++){
            preparedStatement.setString(i+1, queryParams.get(i));
        }
        int affectedRows = preparedStatement.executeUpdate();

        System.out.println("Было добавлено " + affectedRows + "строк");
    }
}