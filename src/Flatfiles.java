import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Flatfiles {

    // Variabel Global
    static String fileName;
    static ArrayList<String> Data1;
    static ArrayList<String> Data2;
    static ArrayList<String> Data3;
    static boolean isEditing = false;
    static Scanner input;

    // Main Function
    public static void main(String[] args) {
        Data1 = new ArrayList<>();
        Data2 = new ArrayList<>();
        Data3 = new ArrayList<>();
        input = new Scanner(System.in);

        String filePath = System.console() == null ? "/src/output.txt" : "/output.txt";
        fileName = System.getProperty("user.dir") + filePath;

        System.out.println("FILE: " + fileName);

        // Menjalankan program
        while (true) {
            showMenu();
        }

    }

    // Method Untuk Clear Screen
    static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                // clear screen untuk windows
                new ProcessBuilder("cmd", "/c", "cls")
                        .inheritIO()
                        .start()
                        .waitFor();
            } else {
                // clear screen untuk Linux, Unix, Mac
                Runtime.getRuntime().exec("clear");
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.println("Error karena: " + e.getMessage());
        }
    }

    // Method Untuk menampilkan menu
    static void showMenu() {
        System.out.println("====== HELLO MART ======");
        System.out.println("[1] Lihat Data Barang");
        System.out.println("[2] Tambah Data Barang");
        System.out.println("[3] Edit Data Barang");
        System.out.println("[4] Hapus Data Barang");
        System.out.println("[5] Cari Data Barang");
        System.out.println("[0] Keluar");
        System.out.println("");

        System.out.print("Silahkan pilih menu : ");
        String PilihMenu = input.nextLine();

        if (PilihMenu.equals("1")) {
            showData();
        } else if (PilihMenu.equals("2")) {
            addData();
        } else if (PilihMenu.equals("3")) {
            editData();
        } else if (PilihMenu.equals("4")) {
            deleteData();
        } else if (PilihMenu.equals("5")) {
            searchData();
        } else if (PilihMenu.equals("0")) {
            System.exit(0);
        } else {
            System.out.println("Pilihan menu yang anda pilih tidak ada !");
            backToMenu();
        }
    }

    // Method untuk kembali kemenu
    static void backToMenu() {
        System.out.println("");
        System.out.print("Tekan [Enter] untuk kembali..");
        input.nextLine();
        clearScreen();
    }

    // Method Untuk Membaca data yang dimasukkan
    static void readData() {
        try {
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);

            // load isi file ke dalam array
            Data1.clear();
            Data2.clear();
            Data3.clear();
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                Data1.add(data);
                Data2.add(data);
                Data3.add(data);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error karena: " + e.getMessage());
        }

    }

    // Method Untuk menampilkan data
    static void showData() {
        clearScreen();
        readData();
        if (Data1.size() > 0) {
            System.out.println("Isi Data Barang :");
            int index = 0;
            for (String data : Data1) {
                System.out.println(String.format("[%d] %s", index, data));
                index++;
            }
        } else {
            System.out.println("Tidak ada data!");
        }

        if (!isEditing) {
            backToMenu();
        }
    }

    // Method Untuk menambahkan data
    static void addData() {
        clearScreen();

        System.out.println("Data barang yang ingin kamu tambahkan?");
        System.out.print("Code Barang  : ");
        String newData1 = input.nextLine();
        System.out.print("Nama Barang  : ");
        String newData2 = input.nextLine();
        System.out.print("Jenis Barang : ");
        String newData3 = input.nextLine();

        try {
            // tulis file
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.append(String.format("%s  #%s #%s \n", newData1, newData2, newData3));
            fileWriter.close();
            System.out.println("Berhasil ditambahkan!");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan karena: " + e.getMessage());
        }

        backToMenu();
    }

    // Method Untuk mengedit data
    static void editData() {
        isEditing = true;
        showData();

        try {
            System.out.println("\n-------------------------------");
            System.out.print("Pilih Indeks yang akan diedit :  ");
            int index = Integer.parseInt(input.nextLine());

            if (index > Data1.size()) {
                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
            } else {

                System.out.print("Code Baru  : ");
                String Dbaru1 = input.nextLine();
                System.out.print("Nama Baru  : ");
                String Dbaru2 = input.nextLine();
                System.out.print("Jenis Baru : ");
                String Dbaru3 = input.nextLine();

                // update data
                Data1.set(index, Dbaru1 + " #" + Dbaru2 + " #" + Dbaru3);

                try {
                    FileWriter fileWriter = new FileWriter(fileName, false);

                    // tulis data baru
                    for (String data : Data1) {
                        fileWriter.append(String.format("%s%n", data));
                    }
                    fileWriter.close();

                    System.out.println("Berhasil diubah!");
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan karena: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        isEditing = false;
        backToMenu();
    }

    // Method Untuk menghapus data
    static void deleteData() {
        isEditing = true;
        showData();

        System.out.println("");
        System.out.println("---------------------------------");
        System.out.print("Pilih Indeks yang akan dihapus :  ");
        int index = Integer.parseInt(input.nextLine());

        try {
            if (index > Data1.size()) {
                throw new IndexOutOfBoundsException("Kamu memasukan data yang salah!");
            } else {

                System.out.println("\nKamu akan menghapus :");
                System.out.println(String.format("[%d] %s", index, Data1.get(index)));
                System.out.println("\nApa kamu yakin?");
                System.out.print("Jawab (y/t): ");
                String jawab = input.nextLine();

                if (jawab.equalsIgnoreCase("y")) {
                    // hapus data
                    Data1.remove(index);

                    // tulis ulang file
                    try {
                        FileWriter fileWriter = new FileWriter(fileName, false);

                        // tulis data baru
                        for (String data : Data1) {
                            fileWriter.append(String.format("%s%n", data));
                        }
                        fileWriter.close();

                        System.out.println("Berhasil dihapus!");
                    } catch (IOException e) {
                        System.out.println("Terjadi kesalahan karena: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        isEditing = false;
        backToMenu();
    }

    // Method Untuk mencari data
    static void searchData() {
        isEditing = true;
        showData();

        System.out.println("");
        System.out.println("------------------------------------");
        System.out.print("Masukkan Code barang yang anda cari : ");
        String Cari = input.nextLine();
        boolean Ketemu = false;
        int x = 0;

        for (int i = 0; i < Data1.size(); i++) {
            if (Data1.get(i).contains(Cari)) {
                Ketemu = true;
                x = i;
            }

        }

        if (Ketemu) {
            System.out.println("\n-->[" + x + "] " + Data1.get(x));
            System.out.println("\nData barang ditemukan !");
        } else {
            System.out.println("Data barang tidak ada!");
        }

        isEditing = false;
        backToMenu();
    }
}
