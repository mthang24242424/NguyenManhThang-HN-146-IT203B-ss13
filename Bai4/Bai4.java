package Bai4;

import java.sql.*;
import java.util.*;

public class Bai4 {

    public List<BenhNhanDTO> getDanhSachBenhNhan() {

        /*
        PHAN TICH INPUT / OUTPUT

        Input:
        - Khong co tham so (lay danh sach benh nhan trong ngay)

        Output:
        - List<BenhNhanDTO>
        - Moi BenhNhanDTO gom:
            + maBenhNhan
            + tenBenhNhan
            + List<DichVu> dsDichVu

        YEU CAU:
        - Load 500 benh nhan < 1s
        - Khong duoc mat du lieu benh nhan khong co dich vu (Trap 2)
        */

        List<BenhNhanDTO> result = new ArrayList<>();
        Map<Integer, BenhNhanDTO> map = new LinkedHashMap<>();

        Connection conn = null;

        try {
            conn = DatabaseManager.getConnection();

            /*
            GIAI PHAP DUOC CHON (TOI UU NHAT)

            - Dung 1 query duy nhat (JOIN)
            - Giam Network IO (chi 1 lan goi DB)
            - Xu ly grouping bang Map trong Java

            SQL dung LEFT JOIN de tranh mat du lieu (Trap 2)
            */

            String sql = """
                SELECT bn.maBenhNhan, bn.tenBenhNhan,
                       dv.maDichVu, dv.tenDichVu
                FROM BenhNhan bn
                LEFT JOIN DichVuSuDung dv
                ON bn.maBenhNhan = dv.maBenhNhan
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int maBN = rs.getInt("maBenhNhan");

                if (!map.containsKey(maBN)) {
                    BenhNhanDTO bn = new BenhNhanDTO();
                    bn.setMaBenhNhan(maBN);
                    bn.setTenBenhNhan(rs.getString("tenBenhNhan"));

                    bn.setDsDichVu(new ArrayList<>());

                    map.put(maBN, bn);
                }

                int maDV = rs.getInt("maDichVu");
                String tenDV = rs.getString("tenDichVu");

                /*
                TRAP 2 - XU LY MAT DU LIEU + NULL

                - Neu benh nhan chua co dich vu:
                  maDV se = 0 (default int)
                  tenDV = null

                => Khong duoc add vao list
                => Nhung benh nhan van ton tai trong map
                */

                if (tenDV != null) {
                    DichVu dv = new DichVu();
                    dv.setMaDichVu(maDV);
                    dv.setTenDichVu(tenDV);

                    map.get(maBN).getDsDichVu().add(dv);
                }
            }

            result.addAll(map.values());

        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                System.out.println("Loi dong ket noi");
            }
        }

        return result;
    }
}