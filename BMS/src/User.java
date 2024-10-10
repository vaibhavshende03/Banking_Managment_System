import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Connection;

public class User {
    private Connection connection;
    private Scanner scanner;

    public User(Connection connection,Scanner scanner){
        this.connection=connection;
        this.scanner=scanner;
    }

    public String login(){
        scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return email;
            }else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void register(){
        scanner.nextLine();
        System.out.println("Full Name: ");
        String full_name=scanner.nextLine();
        System.out.println("Email:");
        String email= scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (user_exit(email)){
            System.out.println("User Already Exists for this Email Address!!");
        }
        else {
            String registration_query ="insert into user(full_name,email,password) values (?,?,?);";

            try{
                PreparedStatement preparedStatement=connection.prepareStatement(registration_query);
                preparedStatement.setString(1,full_name);
                preparedStatement.setString(2,email);
                preparedStatement.setString(3,password);
                int rowsAffected=preparedStatement.executeUpdate();
                if (rowsAffected>0){
                    System.out.println("Registered Successfully.");
                }
                else {
                    System.out.println("registration Failed.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public boolean user_exit(String email){
        String query="select * from user where email=(?)";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,email);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return  false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


}
