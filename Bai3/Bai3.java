package Bai3;

import java.sql.*;

public class Bai3 {

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {

        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false);

            String sqlSelect = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            PreparedStatement psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, maBenhNhan);
            ResultSet rs = psSelect.executeQuery();

            if (!rs.next()) {
                throw new Exception("Khong tim thay benh nhan");
            }

            double balance = rs.getDouble("balance");

            if (balance < tienVienPhi) {
                throw new Exception("Khong du tien de thanh toan");
            }

            String sqlUpdateWallet =
                    "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sqlUpdateWallet);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);
            ps1.executeUpdate();

            String sqlUpdateBed =
                    "UPDATE Beds SET status = 'TRONG' WHERE patient_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sqlUpdateBed);
            ps2.setInt(1, maBenhNhan);
            int row2 = ps2.executeUpdate();

            if (row2 == 0) {
                throw new Exception("Khong cap nhat duoc giuong");
            }

            String sqlUpdatePatient =
                    "UPDATE Patients SET status = 'XUAT_VIEN' WHERE patient_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sqlUpdatePatient);
            ps3.setInt(1, maBenhNhan);
            int row3 = ps3.executeUpdate();

            if (row3 == 0) {
                throw new Exception("Khong cap nhat duoc benh nhan");
            }

            conn.commit();

            System.out.println("Xuat vien thanh cong");

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Rollback loi: " + ex.getMessage());
            }

            System.out.println("That bai: " + e.getMessage());

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Dong ket noi loi");
            }
        }
    }
}