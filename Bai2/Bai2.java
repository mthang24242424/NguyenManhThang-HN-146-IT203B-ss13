package Bai2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bai2 {

    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {

        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();

            /*
            PHAN 1 - PHAN TICH

            Khi xay ra loi o buoc cap nhat hoa don ma khong rollback:
            - Transaction bi dung giua chung
            - Connection bi giu o trang thai dang xu ly
            - Co the gay lock hoac loi he thong

            Hanh dong thieu:
            rollback() de huy toan bo giao dich
            */

            conn.setAutoCommit(false);

            String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();

            String sql2 = "UPDATE Invoices SET status = 'PAID' WHERE invoice_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();

            conn.commit();

            System.out.println("Thanh toan hoan tat");

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Rollback loi: " + ex.getMessage());
            }

            System.out.println("Loi: " + e.getMessage());

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Dong ket noi loi: " + e.getMessage());
            }
        }
    }
}