package Hospital;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem
{
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Srinath1712@";

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        try
        {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            while(true)
            {
                System.out.println(" HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice: ");
                int choice = scanner.nextInt();

                switch (choice)
                {
                    case 1:
                    {
//                      addPatient
                        patient.addPatient();
                        System.out.println();
                        break;
                    }
                    case 2:
                    {
//                      viewPatient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    }
                    case 3:
                    {
//                      viewDoctor
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    }
                    case 4:
                    {
//                      bookAppointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    }
                    case 5:
                    {
                        System.out.println("Thank You For Using Hospital Management System!");
                        return;
                    }
                    default:
                    {
                        System.out.println("Enter Valid Choice!");
                        break;
                    }
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner)
    {
//        patient ID
        System.out.println("Enter Patient ID: ");
        int patientID = scanner.nextInt();
//        doctor ID
        System.out.println("Enter Doctor ID: ");
        int doctorID = scanner.nextInt();
//        appointment date
        System.out.println("Enter appointment date(YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        if(patient.getPatientById(patientID) && doctor.getDoctorById(doctorID))
        {
//            check the doctor date availability
            if(checkDoctorAvailability(doctorID,appointmentDate, connection))
            {
                String appointmentQuery = "insert into appointments(patient_id, doctor_id, appointment_date) values(?,?,?)";
                try
                {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientID);
                    preparedStatement.setInt(2,doctorID);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();

//                    check the row affected
                    if(rowsAffected>0)
                    {
                        System.out.println("Appointmetnt Booked!");
                    }
                    else
                    {
                        System.out.println("Failed to Book Appointmetnt!");
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("Doctor not available on this date!");
            }

        }
        else
        {
            System.out.println("Either doctor or patient does not exist!");
        }
    }

//    checkAvailability

    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection)
    {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorID);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                if(count==0){
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
