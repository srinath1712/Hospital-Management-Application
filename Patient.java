package Hospital;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patient {
//    Data member
    private Connection connnection;
    private Scanner scanner;

//    Constructor
    public Patient(Connection connnection, Scanner scanner)
    {
        this.connnection = connnection;
        this.scanner = scanner;
    }

//    Add Patients

    public void addPatient()
    {
//        patient name
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
//        patient age
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
//        patient gender
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        try
        {
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connnection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows = preparedStatement.executeUpdate();

//            affected rows check
            if(affectedRows>0)
            {
                System.out.print("Patient Added Successfully!");
            }
            else
            {
                System.out.print("Failed to add Patient!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
//    View Patients

    public void viewPatients()
    {
//        view the patients list
        String query = "select * from patients";
        try
        {

            PreparedStatement preparedStatement = connnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients:");
            System.out.println("+------------+----------------+----------+----------------+");
            System.out.println("| Patient ID | Name           | Age      | Gender         |");
            System.out.println("+------------+----------------+----------+----------------+");

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("| %-10s | %-14s | %-8s | %-14s |\n", id, name,age,gender);
                System.out.println("+------------+----------------+----------+----------------+");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

//    Check Patient
    public boolean getPatientById(int id)
    {
         String query = "select * from patients where id = ?";
         try
         {
             PreparedStatement preparedStatement = connnection.prepareStatement(query);
             preparedStatement.setInt(1,id);
             ResultSet resultSet = preparedStatement.executeQuery();

             if(resultSet.next())
             {
                 return true;
             }
             else
             {
                 return false;
             }

         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
         return false;
    }
}
