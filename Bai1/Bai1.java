package Bai1;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Bai1 {

    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();

            /*
            Phần 1:

            JDBC mặc định Auto-Commit = TRUE

            Mỗi lệnh executeUpdate() sẽ tự động commit ngay sau khi chạy xong

            Flow lỗi:
            1. UPDATE Medicine_Inventory -> chạy OK -> AUTO COMMIT -> dữ liệu đã lưu DB
            2. Lỗi xảy ra (vd: chia 0) -> chương trình dừng
            3. INSERT Prescription_History không được thực thi

             Kết quả:
               - Kho thuốc bị trừ (đã commit)
               - Không có lịch sử cấp phát

             Không rollback được vì:
               - Mỗi câu lệnh là 1 transaction riêng
            */

            conn.setAutoCommit(false);

            String sqlUpdateInventory =
                    "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sqlUpdateInventory);
            ps1.setInt(1, medicineId);
            ps1.executeUpdate();

            String sqlInsertHistory =
                    "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, NOW())";
            PreparedStatement ps2 = conn.prepareStatement(sqlInsertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();

            conn.commit();

            System.out.println("Cấp phát thuốc thành công!");

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("Có lỗi xảy ra: " + e.getMessage());

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}