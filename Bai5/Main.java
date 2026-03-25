package Bai5;

import java.util.Scanner;
import java.sql.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Bai5.PatientController controller = new Bai5.PatientController();

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. Xem giuong trong");
            System.out.println("2. Tiep nhan benh nhan");
            System.out.println("3. Thoat");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    showBeds();
                    break;

                case 2:
                    handleInput();
                    break;

                case 3:
                    return;
            }
        }
    }

    static void showBeds() {
        try {
            Connection conn = DatabaseHelper.getConnection();
            String sql = "SELECT * FROM Beds WHERE status='EMPTY'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("Giuong trong:");
            while (rs.next()) {
                System.out.println("Bed ID: " + rs.getInt("bed_id"));
            }

        } catch (Exception e) {
            System.out.println("Loi DB");
        }
    }

    static void handleInput() {
        try {
            System.out.print("Ten: ");
            String name = sc.nextLine();

            System.out.print("Tuoi: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Ma giuong: ");
            int bedId = Integer.parseInt(sc.nextLine());

            System.out.print("Tien: ");
            double amount = Double.parseDouble(sc.nextLine());

            if (amount <= 0) {
                System.out.println("Tien khong hop le");
                return;
            }

            controller.tiepNhan(name, age, bedId, amount);

        } catch (Exception e) {
            System.out.println("Nhap sai du lieu");
        }
    }
}