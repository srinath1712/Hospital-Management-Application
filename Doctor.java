package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Doctor
{
    private Connection connnection;

    //    Constructor
    public Doctor(Connection connnection)
    {
        this.connnection = connnection;
    }

//    View Patients

    public void viewDoctors()
    {
//        veiw the doctors list
        String query = "select * from doctors";
        try
        {

            PreparedStatement preparedStatement = connnection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors:");
            System.out.println("+-----------+-----------------+------------------+");
            System.out.println("| Doctor ID | Name            | Specialization   |");
            System.out.println("+-----------+-----------------+------------------+");

            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-9s | %-15s | %-16s |\n", id, name,specialization);
                System.out.println("+-----------+-----------------+------------------+");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //    Check Doctors
    public boolean getDoctorById(int id)
    {
        String query = "select * from doctors where id = ?";
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
