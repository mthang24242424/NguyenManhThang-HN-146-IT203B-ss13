package Bai5;

import java.sql.*;

public class PatientController {

    public void tiepNhan(String name, int age, int bedId, double amount) {
        Connection conn = null;

        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // thêm bệnh nhân
            String insertPatient = "INSERT INTO Patients(name, age, bed_id) VALUES (?, ?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(insertPatient, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, name);
            ps1.setInt(2, age);
            ps1.setInt(3, bedId);
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            int patientId = 0;
            if (rs.next()) patientId = rs.getInt(1);

            // update giường
            String updateBed = "UPDATE Beds SET status='OCCUPIED' WHERE bed_id=? AND status='EMPTY'";
            PreparedStatement ps2 = conn.prepareStatement(updateBed);
            ps2.setInt(1, bedId);

            int row = ps2.executeUpdate();

            // bẫy: giường không tồn tại hoặc đã có người
            if (row == 0) {
                throw new Exception("Giuong khong hop le hoac da co nguoi");
            }

            // thêm payment
            String insertPay = "INSERT INTO Payments(patient_id, amount) VALUES (?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(insertPay);
            ps3.setInt(1, patientId);
            ps3.setDouble(2, amount);
            ps3.executeUpdate();

            conn.commit();
            System.out.println("Tiep nhan thanh cong");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {}

            System.out.println("That bai: " + e.getMessage());

        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }
}