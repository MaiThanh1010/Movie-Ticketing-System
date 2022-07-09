/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MainPage;

/**
 *
 * @author nguye
 */
import Connection.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.Color;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.*;
import java.text.DateFormat;
import java.time.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class AdminPage extends javax.swing.JFrame {

    /**
     * Creates new form AdminPage
     */
    
    DefaultTableModel tblModelKH = null;
    DefaultTableModel tblModelPhim = null;
    DefaultTableModel tblModelLC = null;
    DefaultTableModel tblModelSC = null;
    DefaultTableModel tblModelNV = null;
    ArrayList<String> maTL = new ArrayList<>();
    ArrayList<String> tenTL = new ArrayList<>();
    ArrayList<String> maPhim = new ArrayList<>();
    ArrayList<String> tenPhim = new ArrayList<>();
    ArrayList<String> maLC = new ArrayList<>();
    ArrayList<String> tenLC = new ArrayList<>();
    ArrayList<String> maSC = new ArrayList<>();
    ArrayList<String> tenSC = new ArrayList<>();
    ArrayList<String> maPhong = new ArrayList<>();
    ArrayList<String> tenPhong = new ArrayList<>();
    
    
    public void loadCbbTL(){
        Connection con = new DBUtils().createConn();
        try{
            String SQL = "SELECT DISTINCT MATL, TENTL FROM THELOAIPHIM";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            cbbTheLoai.removeAllItems();
            maTL.clear();
            tenTL.clear();
            while(rs.next()){
                maTL.add(rs.getString("MATL"));
                tenTL.add(rs.getString("TENTL"));
            }
            cbbTheLoai.setModel(new DefaultComboBoxModel(tenTL.toArray()));
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
    }
    
    public void loadCbbPhim(){
        Connection con = new DBUtils().createConn();
        try{
            String SQL = "SELECT DISTINCT MAPHIM,TENPHIM FROM PHIM ORDER BY MAPHIM";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            cbbPhim.removeAllItems();
            maPhim.clear();
            tenPhim.clear();
            while(rs.next()){
                maPhim.add(rs.getString("MAPHIM"));
                tenPhim.add(rs.getString("TENPHIM"));
            }
        cbbPhim.setModel(new DefaultComboBoxModel(tenPhim.toArray()));
        } catch (SQLException ex) {
        ex.printStackTrace();
        }
    }
    
    public void loadCbbPhong(){
        Connection con = new DBUtils().createConn();
        try{
            String SQL = "SELECT DISTINCT MAPHONG,TENPHONG FROM PHONG ORDER BY MAPHONG";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            cbbPhong.removeAllItems();
            maPhong.clear();
            tenPhong.clear();
            while(rs.next()){
                maPhong.add(rs.getString("MAPHONG"));
                tenPhong.add(rs.getString("TENPHONG"));
            }
        cbbPhong.setModel(new DefaultComboBoxModel(tenPhong.toArray()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }   
    
    public void loadCbbMaLC(){
        Connection con = new DBUtils().createConn();
        try{
            String strSQL = "Select MALC from LICHCHIEU";
            Statement stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stat.executeQuery(strSQL);
            while(rs.next()){
                maLC.add(rs.getString("MaLC"));
            }
            cbbMaLC.setModel(new DefaultComboBoxModel<String>(maLC.toArray(new String[0])));
        } catch (SQLException e){
            System.out.println(e);
            System.out.println("Lỗi");
        }
    }
    
    public void loadTableKH() {
        tblModelKH = new DefaultTableModel();
        String tieude[] = {"Mã khách hàng", "Họ và tên", "Ngày sinh", "Giới tính", "Email", "Số điện thoại", "Tên đăng nhập", "Mật khẩu", "Tổng tiền tích lũy"};
        tblModelKH.setColumnIdentifiers(tieude);
        Connection con = new DBUtils().createConn();
        try{
            String row[] = new String[9];
            String SQL = "SELECT MAKH,TENKH,NGSINH,GIOITINH, EMAIL, SDT,TENDN,MK,TONGTIEN FROM KHACHHANG ORDER BY MAKH";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            while(rs.next())
            {
                row[0] = rs.getString("MaKH");
                row[1] = rs.getString("TenKH");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                row[2] = df.format(rs.getDate("NgSinh"));
                if(rs.getBoolean("GioiTinh")==true)
                    row[3]="Nữ";
                else
                    row[3]="Nam";
                //row[3] = rs.getString("GioiTinh");
                row[4] = rs.getString("Email");
                row[5] = rs.getString("SDT");
                row[6] = rs.getString("TenDN");
                row[7] = rs.getString("MK");
                row[8] = rs.getString("TongTien");
                //int TL = Integer.parseInt(rs.getString(9));
                tblModelKH.addRow(row);
            }
            con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        tblKH.setModel(tblModelKH);
        setVisible(true);
    }
    
    public void loadTablePhim(){
        tblModelPhim = new DefaultTableModel();
        String tieude[] = {"Mã phim", "Tên phim", "Thể loại", "Đạo diễn", "Diễn viên", "Ngày phát hành", "Thời lượng", "Doanh thu"};
        tblModelPhim.setColumnIdentifiers(tieude);
        Connection con = new DBUtils().createConn();
        try{
            String row[] = new String[8];
            String SQL_PHIM = "SELECT PHIM.MAPHIM,PHIM.TENPHIM,THELOAIPHIM.TENTL,PHIM.DAODIEN,PHIM.DIENVIEN,PHIM.NGPHATHANH,PHIM.THOILUONG, PHIM.DOANHTHU FROM PHIM,THELOAIPHIM WHERE PHIM.MATL = THELOAIPHIM.MATL ORDER BY PHIM.MAPHIM";
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(SQL_PHIM);
            while(rs.next())
            {
                row[0] = rs.getString("MaPhim");
                row[1] = rs.getString("TenPhim");
                row[2] = rs.getString("TenTL");
                row[3] = rs.getString("DaoDien");
                row[4] = rs.getString("DienVien");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                row[5] = df.format(rs.getDate("NgPhatHanh"));
                row[6] = rs.getString("ThoiLuong");
                row[7] = rs.getString("DoanhThu");
                tblModelPhim.addRow(row);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        tblP.setModel(tblModelPhim);
        setVisible(true);
    }
    
    public void loadTableLC(){    
        tblModelLC = new DefaultTableModel();
        String tieude[] = {"Mã lịch chiếu", "Tên phim", "Ngày chiếu"};
        tblModelLC.setColumnIdentifiers(tieude);
        Connection con = new DBUtils().createConn();
        try{
            String row[] = new String[3];
            String SQL = "SELECT MALC,PHIM.TENPHIM, NGCHIEU \n" 
            + "FROM LICHCHIEU,PHIM \n" 
            + "WHERE LICHCHIEU.MAPHIM = PHIM.MAPHIM \n" 
            + "ORDER BY MALC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next()){
                row[0] = rs.getString("MaLC");
                row[1] = rs.getString("TenPhim");
                row[2] = df.format(rs.getDate("NgChieu"));
                tblModelLC.addRow(row);
            }
            con.close();
        } 
        catch (Exception ex){
            ex.printStackTrace();
        }
        tblLC.setModel(tblModelLC);
        setVisible(true);
    }
    
    private void loadTableSC(){    
        tblModelSC = new DefaultTableModel();
        String tieude[] = {"Mã suất chiếu","Tên suất chiếu", "Mã lịch chiếu", "Tên phim", "Ngày chiếu", "Phòng", "Thời gian bắt đầu", "Thời gian kết thúc", "Số lượng ghế còn trống"};
        tblModelSC.setColumnIdentifiers(tieude);
        Connection con = new DBUtils().createConn();
        try{
            String row[] = new String[9];
            String SQL = "SELECT MASC, TENSC, TGBD, TGKT, LICHCHIEU.MALC, TENPHIM, NGCHIEU, TENPHONG, SLGHE \n"
                       + "FROM SUATCHIEU, PHONG, LICHCHIEU, PHIM \n"
                       + "WHERE SUATCHIEU.MAPHONG = PHONG.MAPHONG AND LICHCHIEU.MALC=SUATCHIEU.MALC AND PHIM.MAPHIM=LICHCHIEU.MAPHIM \n"
                       + "ORDER BY MASC";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL);
            SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next()){
                row[0] = rs.getString("MaSC");
                row[1] = rs.getString("TenSC");
                row[2] = rs.getString("MaLC");
                row[3] = rs.getString("TenPhim");
                row[4] = df.format(rs.getDate("NgChieu"));
                row[5] = rs.getString("TenPhong");
                row[6] = tf.format(rs.getTime("TGBD"));
                row[7] = tf.format(rs.getTime("TGKT"));
                row[8] = rs.getString("SLGhe");
                tblModelSC.addRow(row);
                }
            con.close();
        } 
        catch (Exception ex){
            ex.printStackTrace();
        }
        tblSC.setModel(tblModelSC);
        setVisible(true);
    }
    
    public void loadTableNV(){ 
        tblModelNV = new DefaultTableModel();
        String tieude[] = {"Mã nhân viên", "Tên nhân viên", "Ngày sinh", "Giới tính", "Email", "Số điện thoại", "Địa chỉ", "Ngày vào làm", "Mức lương"};
        tblModelNV.setColumnIdentifiers(tieude);
        Connection con = new DBUtils().createConn();
        try{
            String row[] = new String[9];
            String SQL = "SELECT MANV,TENNV,NGSINH,NGVAOLAM,GIOITINH,EMAIL, SODT,DIACHI,MUCLUONG FROM NHANVIEN ORDER BY MANV";
            Statement statement = con.createStatement();
            ResultSet rs=statement.executeQuery(SQL);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            while(rs.next())
                {
                row[0] = rs.getString("MaNV");
                row[1] = rs.getString("TenNV");
                row[2] = df.format(rs.getDate("NgSinh"));
                if(rs.getBoolean("GioiTinh")==true)
                    row[3]="Nữ";
                else
                    row[3]="Nam";
                //row[3] = rs.getString("GioiTinh");
                row[4] = rs.getString("Email");
                row[5] = rs.getString("SoDT");
                row[6] = rs.getString("DiaChi");
                row[7] = df.format(rs.getDate("NgVaoLam"));
                row[8] = rs.getString("MucLuong");
                tblModelNV.addRow(row);
                }
            con.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        tblNV.setModel(tblModelNV);
        setVisible(true);
    }
        
    public void clearKH(){
        txtMaKH.setText("");
        txtTenKH.setText("");
        ngSinh.setDate(null);
        cbbGioiTinh.setSelectedIndex(0);
        txtEmail.setText("");
        txtSDT.setText("");
        txtTenDN.setText("");
        txtMK.setText("");
        txtTongTien.setText("0");
    }
    
    private void clearPhim(){
        txtMaP.setText("");
        txtTenP.setText("");
        cbbTheLoai.setSelectedIndex(0);
        txtDaoDien.setText("");
        txtDienVien.setText("");
        ngPhatHanh.setDate(null);
        txtThoiLuong.setText("");
    }
    
    private void clearLC(){
        txtMaLC.setText("");
        cbbPhim.setSelectedIndex(0);
        ngChieu.setDate(null);
    }
    
    private void clearSC(){
         //ngChieuSC.setDate(null);
        cbbPhong.setSelectedIndex(0);
        //cbbLC.setSelectedIndex(0);
    }
    
    public void clearNV(){
        txtMaNV.setText("");
        txtTenNV.setText("");
        ngSinhNV.setDate(null);
        cbbGT.setSelectedIndex(0);
        txtEmailNV.setText("");
        txtSDTNV.setText("");
        txtDiaChi.setText("");
        ngVaoLam.setDate(null);
        txtLuong.setText("0");
    }
    
    public String layMaGhe(){
        String MaKB = "";
        Connection con = new DBUtils().createConn();
        String StrSQL = "SELECT MaGhe FROM GHE order by MaGhe DESC";
        try{
            Statement stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stat.executeQuery(StrSQL);
            if (rs.absolute(1)==false){
                MaKB="GH0001";
            }
            else {
                String MaKBHienTai = rs.getString("MaGhe");
                MaKB = MaKBHienTai.substring(2,6);
                int MaKBmoi = Integer.parseInt(MaKB) +1;
                if(MaKBmoi<10)
                    MaKB = "GH000"+MaKBmoi;
                else
                    if(MaKBmoi<100)
                        MaKB = "GH00"+MaKBmoi;
                    else
                        if(MaKBmoi<1000)
                            MaKB = "GH0"+MaKBmoi;
                        else
                            MaKB = "GH"+MaKBmoi;
            }
            con.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Lỗi");
        }
        return MaKB;
    }
    
    public void loadGhe(){
        try{
            Connection con = new DBUtils().createConn();
            for(int i=1; i<=5; i++){
                for (int j=1; j<=5; j++){
                    String strInsert = "insert into GHE (MAGHE, MASC, HANGGHE, SOGHE, TRANGTHAI) values (?, ?, ?, ?, ?)";
                    PreparedStatement pres= con.prepareStatement(strInsert);
                    pres.setString(1, layMaGhe());
                    pres.setString(2, txtMaSC.getText());
                    pres.setInt(3, i);
                    pres.setInt(4, j);
                    pres.setBoolean(5, true );
                    pres.executeUpdate();
                }
            }
            con.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public AdminPage() {
        initComponents();
        setSize(900,500);
        //center the form
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        txtNgChieu.setEditable(false);
        txtTenPhim.setEditable(false);
        cbbGioiTinh.setModel(new DefaultComboBoxModel<>(new String[]{"Nam", "Nữ"}));
        cbbGT.setModel(new DefaultComboBoxModel<>(new String[]{"Nam", "Nữ"}));
        loadTableKH();
        loadTablePhim();
        loadTableLC();
        loadTableSC();
        loadTableNV();
        loadCbbPhim();
        loadCbbTL();
        loadCbbPhong();
        loadCbbMaLC();
        
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtTenDN = new javax.swing.JTextField();
        txtMK = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        btnThemKH = new javax.swing.JButton();
        btnSuaKH = new javax.swing.JButton();
        btnXoaKH = new javax.swing.JButton();
        ngSinh = new com.toedter.calendar.JDateChooser();
        cbbGioiTinh = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKH = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtMaP = new javax.swing.JTextField();
        txtTenP = new javax.swing.JTextField();
        txtThoiLuong = new javax.swing.JTextField();
        ngPhatHanh = new com.toedter.calendar.JDateChooser();
        cbbTheLoai = new javax.swing.JComboBox<>();
        txtDaoDien = new javax.swing.JTextField();
        txtDienVien = new javax.swing.JTextField();
        btnThemP = new javax.swing.JButton();
        btnSuaP = new javax.swing.JButton();
        btnXoaP = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblP = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        txtDoanhThu = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        btnThemLC = new javax.swing.JButton();
        btnSuaLC = new javax.swing.JButton();
        btnXoaLC = new javax.swing.JButton();
        txtMaLC = new javax.swing.JTextField();
        ngChieu = new com.toedter.calendar.JDateChooser();
        cbbPhim = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLC = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btnThemSC = new javax.swing.JButton();
        btnSuaSC = new javax.swing.JButton();
        btnXoaSC = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSC = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        cbbPhong = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtTenSC = new javax.swing.JTextField();
        Date date = new Date();
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        TGBD = new javax.swing.JSpinner(sm);
        Date date1 = new Date();
        SpinnerDateModel sm1 = new SpinnerDateModel(date1, null, null, Calendar.HOUR_OF_DAY);
        TGKT = new javax.swing.JSpinner(sm1);
        txtMaSC = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        cbbMaLC = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        txtNgChieu = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        txtTenPhim = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        txtTenNV = new javax.swing.JTextField();
        cbbGT = new javax.swing.JComboBox<>();
        ngSinhNV = new com.toedter.calendar.JDateChooser();
        txtSDTNV = new javax.swing.JTextField();
        txtEmailNV = new javax.swing.JTextField();
        ngVaoLam = new com.toedter.calendar.JDateChooser();
        txtLuong = new javax.swing.JTextField();
        btnThemNV = new javax.swing.JButton();
        btnSuaNV = new javax.swing.JButton();
        btnXoaNV = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        jLabel31 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 153));
        setUndecorated(true);
        getContentPane().setLayout(null);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(875, 5, 22, 22);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/arrow-92-24.jpg"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(830, 2, 30, 30);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 204));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Mã khách hàng");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(70, 20, 150, 25);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 51, 51));
        jLabel2.setText("Họ và tên");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(70, 60, 120, 25);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Tên đăng nhập");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(70, 100, 130, 25);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 51));
        jLabel4.setText("Mật khẩu");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(70, 140, 120, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("Email");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(70, 180, 50, 25);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("Số điện thoại");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(480, 20, 150, 25);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("Ngày sinh");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(480, 60, 100, 25);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 51));
        jLabel8.setText("Giới tính");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(480, 100, 100, 25);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("Tổng tiền tích lũy");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(480, 140, 120, 25);

        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });
        jPanel1.add(txtMaKH);
        txtMaKH.setBounds(190, 20, 200, 25);

        txtTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHActionPerformed(evt);
            }
        });
        jPanel1.add(txtTenKH);
        txtTenKH.setBounds(190, 60, 200, 25);

        txtTenDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDNActionPerformed(evt);
            }
        });
        jPanel1.add(txtTenDN);
        txtTenDN.setBounds(190, 100, 200, 25);
        jPanel1.add(txtMK);
        txtMK.setBounds(190, 140, 200, 25);

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmail);
        txtEmail.setBounds(190, 180, 200, 25);

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        jPanel1.add(txtSDT);
        txtSDT.setBounds(630, 20, 200, 25);
        jPanel1.add(txtTongTien);
        txtTongTien.setBounds(630, 140, 200, 25);

        btnThemKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemKH.setForeground(new java.awt.Color(255, 51, 51));
        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/them.jpg"))); // NOI18N
        btnThemKH.setText("Thêm");
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });
        jPanel1.add(btnThemKH);
        btnThemKH.setBounds(140, 230, 130, 25);

        btnSuaKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaKH.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.jpg"))); // NOI18N
        btnSuaKH.setText("Sửa");
        btnSuaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKHActionPerformed(evt);
            }
        });
        jPanel1.add(btnSuaKH);
        btnSuaKH.setBounds(400, 230, 130, 25);

        btnXoaKH.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaKH.setForeground(new java.awt.Color(255, 51, 51));
        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        btnXoaKH.setText("Xóa");
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });
        jPanel1.add(btnXoaKH);
        btnXoaKH.setBounds(660, 230, 130, 25);
        jPanel1.add(ngSinh);
        ngSinh.setBounds(630, 60, 200, 25);

        cbbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGioiTinhActionPerformed(evt);
            }
        });
        jPanel1.add(cbbGioiTinh);
        cbbGioiTinh.setBounds(630, 100, 100, 25);

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKH);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(60, 290, 780, 161);

        jButton1.setText("Quay lại");
        jPanel1.add(jButton1);
        jButton1.setBounds(820, 580, 73, 22);

        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel1.add(jLabel37);
        jLabel37.setBounds(0, 0, 900, 470);

        jTabbedPane1.addTab("Quản lý khách hàng", null, jPanel1, "");

        jPanel2.setLayout(null);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("Mã phim");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(70, 20, 120, 25);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setText("Tên phim");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(70, 60, 120, 25);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("Thời lượng");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(70, 100, 120, 25);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Ngày phát hành");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(70, 140, 150, 25);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("Thể loại");
        jPanel2.add(jLabel14);
        jLabel14.setBounds(480, 20, 120, 25);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("Đạo diễn");
        jPanel2.add(jLabel15);
        jLabel15.setBounds(480, 60, 120, 25);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("Diễn viên");
        jPanel2.add(jLabel16);
        jLabel16.setBounds(480, 100, 100, 25);

        txtMaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaP);
        txtMaP.setBounds(190, 20, 200, 25);
        jPanel2.add(txtTenP);
        txtTenP.setBounds(190, 60, 200, 25);
        jPanel2.add(txtThoiLuong);
        txtThoiLuong.setBounds(190, 100, 200, 25);
        jPanel2.add(ngPhatHanh);
        ngPhatHanh.setBounds(190, 140, 200, 25);

        cbbTheLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel2.add(cbbTheLoai);
        cbbTheLoai.setBounds(630, 20, 200, 25);
        jPanel2.add(txtDaoDien);
        txtDaoDien.setBounds(630, 60, 200, 25);
        jPanel2.add(txtDienVien);
        txtDienVien.setBounds(630, 100, 200, 25);

        btnThemP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemP.setForeground(new java.awt.Color(255, 51, 51));
        btnThemP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/them.jpg"))); // NOI18N
        btnThemP.setText("Thêm");
        btnThemP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPActionPerformed(evt);
            }
        });
        jPanel2.add(btnThemP);
        btnThemP.setBounds(140, 230, 130, 25);

        btnSuaP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaP.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.jpg"))); // NOI18N
        btnSuaP.setText("Sửa");
        btnSuaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPActionPerformed(evt);
            }
        });
        jPanel2.add(btnSuaP);
        btnSuaP.setBounds(400, 230, 130, 25);

        btnXoaP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaP.setForeground(new java.awt.Color(255, 51, 51));
        btnXoaP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        btnXoaP.setText("Xóa");
        btnXoaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoaP);
        btnXoaP.setBounds(660, 230, 130, 25);

        tblP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblP);

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(60, 290, 780, 160);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("Doanh thu");
        jPanel2.add(jLabel17);
        jLabel17.setBounds(480, 140, 90, 25);
        jPanel2.add(txtDoanhThu);
        txtDoanhThu.setBounds(630, 140, 200, 25);
        txtDoanhThu.getAccessibleContext().setAccessibleName("");

        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel2.add(jLabel39);
        jLabel39.setBounds(0, 0, 900, 470);

        jTabbedPane1.addTab("Quản lý phim", jPanel2);

        jPanel3.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 51, 51));
        jLabel18.setText("Mã lịch chiếu");
        jPanel3.add(jLabel18);
        jLabel18.setBounds(70, 20, 120, 25);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 51, 51));
        jLabel20.setText("Phim");
        jPanel3.add(jLabel20);
        jLabel20.setBounds(70, 60, 100, 25);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 51, 51));
        jLabel19.setText("Ngày chiếu");
        jPanel3.add(jLabel19);
        jLabel19.setBounds(70, 100, 100, 25);

        btnThemLC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemLC.setForeground(new java.awt.Color(255, 51, 51));
        btnThemLC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/them.jpg"))); // NOI18N
        btnThemLC.setText("Thêm");
        btnThemLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLCActionPerformed(evt);
            }
        });
        jPanel3.add(btnThemLC);
        btnThemLC.setBounds(140, 190, 130, 25);

        btnSuaLC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaLC.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaLC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.jpg"))); // NOI18N
        btnSuaLC.setText("Sửa");
        btnSuaLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaLCActionPerformed(evt);
            }
        });
        jPanel3.add(btnSuaLC);
        btnSuaLC.setBounds(400, 190, 130, 25);

        btnXoaLC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaLC.setForeground(new java.awt.Color(255, 51, 51));
        btnXoaLC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        btnXoaLC.setText("Xóa");
        btnXoaLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLCActionPerformed(evt);
            }
        });
        jPanel3.add(btnXoaLC);
        btnXoaLC.setBounds(660, 190, 130, 25);

        txtMaLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLCActionPerformed(evt);
            }
        });
        jPanel3.add(txtMaLC);
        txtMaLC.setBounds(190, 20, 200, 25);
        jPanel3.add(ngChieu);
        ngChieu.setBounds(190, 100, 200, 25);

        cbbPhim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbPhimActionPerformed(evt);
            }
        });
        jPanel3.add(cbbPhim);
        cbbPhim.setBounds(190, 60, 200, 25);

        tblLC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblLC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLCMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblLC);

        jPanel3.add(jScrollPane3);
        jScrollPane3.setBounds(60, 250, 780, 190);

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel3.add(jLabel40);
        jLabel40.setBounds(0, 0, 900, 470);

        jTabbedPane1.addTab("Quản lý lịch chiếu", jPanel3);

        jPanel4.setLayout(null);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 51, 51));
        jLabel21.setText("Mã lịch chiếu");
        jPanel4.add(jLabel21);
        jLabel21.setBounds(70, 60, 120, 25);

        btnThemSC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemSC.setForeground(new java.awt.Color(255, 51, 51));
        btnThemSC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/them.jpg"))); // NOI18N
        btnThemSC.setText("Thêm");
        btnThemSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSCActionPerformed(evt);
            }
        });
        jPanel4.add(btnThemSC);
        btnThemSC.setBounds(140, 230, 130, 25);

        btnSuaSC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaSC.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaSC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.jpg"))); // NOI18N
        btnSuaSC.setText("Sửa");
        btnSuaSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSCActionPerformed(evt);
            }
        });
        jPanel4.add(btnSuaSC);
        btnSuaSC.setBounds(400, 230, 130, 25);

        btnXoaSC.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaSC.setForeground(new java.awt.Color(255, 51, 51));
        btnXoaSC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        btnXoaSC.setText("Xóa");
        btnXoaSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSCActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoaSC);
        btnXoaSC.setBounds(660, 230, 130, 25);

        tblSC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblSC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSCMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSC);

        jPanel4.add(jScrollPane4);
        jScrollPane4.setBounds(70, 280, 770, 170);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 51, 51));
        jLabel32.setText("Phòng");
        jPanel4.add(jLabel32);
        jLabel32.setBounds(70, 140, 100, 25);

        cbbPhong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel4.add(cbbPhong);
        cbbPhong.setBounds(190, 140, 200, 25);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 51, 51));
        jLabel22.setText("Suất chiếu");
        jPanel4.add(jLabel22);
        jLabel22.setBounds(480, 20, 120, 25);

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 51, 51));
        jLabel33.setText("Thời gian bắt đầu");
        jPanel4.add(jLabel33);
        jLabel33.setBounds(480, 100, 120, 25);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 51, 51));
        jLabel34.setText("Thời gian kết thúc");
        jLabel34.setVerifyInputWhenFocusTarget(false);
        jPanel4.add(jLabel34);
        jLabel34.setBounds(480, 140, 150, 25);
        jPanel4.add(txtTenSC);
        txtTenSC.setBounds(630, 20, 200, 25);

        JSpinner.DateEditor de = new JSpinner.DateEditor(TGBD, "HH:mm");
        TGBD.setEditor(de);
        jPanel4.add(TGBD);
        TGBD.setBounds(630, 100, 200, 25);

        JSpinner.DateEditor de1 = new JSpinner.DateEditor(TGKT, "HH:mm");
        TGKT.setEditor(de1);
        jPanel4.add(TGKT);
        TGKT.setBounds(630, 140, 200, 25);

        txtMaSC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaSCActionPerformed(evt);
            }
        });
        jPanel4.add(txtMaSC);
        txtMaSC.setBounds(190, 20, 200, 25);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 51, 51));
        jLabel36.setText("Mã suất chiếu");
        jPanel4.add(jLabel36);
        jLabel36.setBounds(70, 20, 120, 25);

        cbbMaLC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbMaLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMaLCActionPerformed(evt);
            }
        });
        jPanel4.add(cbbMaLC);
        cbbMaLC.setBounds(190, 60, 200, 25);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 51, 51));
        jLabel35.setText("Ngày chiếu");
        jPanel4.add(jLabel35);
        jLabel35.setBounds(70, 100, 100, 25);
        jPanel4.add(txtNgChieu);
        txtNgChieu.setBounds(190, 100, 200, 25);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 51, 51));
        jLabel43.setText("Tên Phim");
        jPanel4.add(jLabel43);
        jLabel43.setBounds(480, 60, 100, 25);
        jPanel4.add(txtTenPhim);
        txtTenPhim.setBounds(630, 60, 200, 25);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 51, 51));
        jLabel44.setText("Số lượng ghế còn trống");
        jPanel4.add(jLabel44);
        jLabel44.setBounds(70, 180, 170, 25);

        txtSL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSLActionPerformed(evt);
            }
        });
        jPanel4.add(txtSL);
        txtSL.setBounds(290, 180, 100, 25);

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel4.add(jLabel41);
        jLabel41.setBounds(0, 0, 900, 470);

        jTabbedPane1.addTab("Quản lý suất chiếu", jPanel4);

        jPanel5.setLayout(null);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 51, 51));
        jLabel23.setText("Mã nhân viên");
        jPanel5.add(jLabel23);
        jLabel23.setBounds(70, 20, 120, 25);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 51, 51));
        jLabel24.setText("Tên nhân viên");
        jPanel5.add(jLabel24);
        jLabel24.setBounds(70, 60, 120, 25);

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 51, 51));
        jLabel25.setText("Giới tính");
        jPanel5.add(jLabel25);
        jLabel25.setBounds(70, 100, 100, 25);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 51, 51));
        jLabel26.setText("Ngày sinh");
        jPanel5.add(jLabel26);
        jLabel26.setBounds(70, 140, 100, 25);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 51, 51));
        jLabel27.setText("Số điện thoại");
        jPanel5.add(jLabel27);
        jLabel27.setBounds(480, 20, 120, 25);

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 51, 51));
        jLabel28.setText("Email");
        jPanel5.add(jLabel28);
        jLabel28.setBounds(480, 60, 100, 25);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 51, 51));
        jLabel29.setText("Ngày vào làm");
        jPanel5.add(jLabel29);
        jLabel29.setBounds(480, 100, 94, 20);

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 51, 51));
        jLabel30.setText("Mức lương");
        jPanel5.add(jLabel30);
        jLabel30.setBounds(480, 140, 150, 25);
        jPanel5.add(txtMaNV);
        txtMaNV.setBounds(190, 20, 200, 25);
        jPanel5.add(txtTenNV);
        txtTenNV.setBounds(190, 60, 200, 25);

        cbbGT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cbbGT);
        cbbGT.setBounds(190, 100, 100, 25);
        jPanel5.add(ngSinhNV);
        ngSinhNV.setBounds(190, 140, 200, 25);
        jPanel5.add(txtSDTNV);
        txtSDTNV.setBounds(630, 20, 200, 25);
        jPanel5.add(txtEmailNV);
        txtEmailNV.setBounds(630, 60, 200, 25);
        jPanel5.add(ngVaoLam);
        ngVaoLam.setBounds(630, 100, 200, 25);
        jPanel5.add(txtLuong);
        txtLuong.setBounds(630, 140, 200, 25);

        btnThemNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemNV.setForeground(new java.awt.Color(255, 51, 51));
        btnThemNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/them.jpg"))); // NOI18N
        btnThemNV.setText("Thêm");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });
        jPanel5.add(btnThemNV);
        btnThemNV.setBounds(140, 230, 130, 25);

        btnSuaNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaNV.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/sua.jpg"))); // NOI18N
        btnSuaNV.setText("Sửa");
        btnSuaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNVActionPerformed(evt);
            }
        });
        jPanel5.add(btnSuaNV);
        btnSuaNV.setBounds(400, 230, 130, 25);

        btnXoaNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaNV.setForeground(new java.awt.Color(255, 51, 51));
        btnXoaNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        btnXoaNV.setText("Xóa");
        btnXoaNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNVActionPerformed(evt);
            }
        });
        jPanel5.add(btnXoaNV);
        btnXoaNV.setBounds(660, 230, 130, 25);

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblNV);

        jPanel5.add(jScrollPane5);
        jScrollPane5.setBounds(62, 293, 780, 160);

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 51, 51));
        jLabel31.setText("Địa chỉ");
        jPanel5.add(jLabel31);
        jLabel31.setBounds(70, 180, 100, 25);
        jPanel5.add(txtDiaChi);
        txtDiaChi.setBounds(190, 180, 200, 25);

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel5.add(jLabel42);
        jLabel42.setBounds(0, 0, 900, 470);

        jTabbedPane1.addTab("Quản lý nhân viên", jPanel5);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 900, 700);

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        getContentPane().add(jLabel38);
        jLabel38.setBounds(0, 0, 900, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbbGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGioiTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbGioiTinhActionPerformed

    private void btnThemPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPActionPerformed
        // TODO add your handling code here:
            Connection con = new DBUtils().createConn();
            String strInsert = "insert into PHIM (MAPHIM, TENPHIM, MATL, DAODIEN, DIENVIEN, NGPHATHANH, THOILUONG, DOANHTHU) values (?, ?, ?, ?, ?, ?, ?, 0)";
            try{
                PreparedStatement pres = con.prepareStatement(strInsert);
                pres.setString(1, txtMaP.getText());
                pres.setString(2, txtTenP.getText());
                pres.setString(3, maTL.get(cbbTheLoai.getSelectedIndex()));
                pres.setString(4, txtDaoDien.getText());
                pres.setString(5, txtDienVien.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(6, df.format(ngPhatHanh.getDate()));
                pres.setString(7, txtThoiLuong.getText());
                pres.executeUpdate();
                con.close();
                loadTablePhim();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_btnThemPActionPerformed

    private void tblKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKHMouseClicked
        // TODO add your handling code here:
        int indexTB = tblKH.getSelectedRow();
        
        if (indexTB >=0 && indexTB <tblKH.getRowCount()){
            txtMaKH.setText(tblKH.getValueAt(indexTB, 0).toString());
            txtTenKH.setText(tblKH.getValueAt(indexTB, 1).toString());
            cbbGioiTinh.setSelectedItem(tblKH.getValueAt(indexTB, 3).toString());
            txtEmail.setText(tblKH.getValueAt(indexTB, 4).toString());
            txtSDT.setText(tblKH.getValueAt(indexTB, 5).toString());
            txtTenDN.setText(tblKH.getValueAt(indexTB, 6).toString());
            txtMK.setText(tblKH.getValueAt(indexTB, 7).toString());
            txtTongTien.setText(tblKH.getValueAt(indexTB, 8).toString());
            }
        try{
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)tblKH.getValueAt(indexTB, 2));
            ngSinh.setDate(dat);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Lỗi");
        }
    }//GEN-LAST:event_tblKHMouseClicked

    private void tblPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPMouseClicked
        // TODO add your handling code here:
        int indexTB = tblP.getSelectedRow();
        
        if (indexTB >=0 && indexTB <tblP.getRowCount()){
            txtMaP.setText(tblP.getValueAt(indexTB, 0).toString());
            txtTenP.setText(tblP.getValueAt(indexTB, 1).toString());
            cbbTheLoai.setSelectedItem(tblP.getValueAt(indexTB, 2).toString());
            txtDaoDien.setText(tblP.getValueAt(indexTB, 3).toString());
            txtDienVien.setText(tblP.getValueAt(indexTB, 4).toString());
            txtThoiLuong.setText(tblP.getValueAt(indexTB, 6).toString());
            txtDoanhThu.setText(tblP.getValueAt(indexTB, 6).toString());
            }
        try{
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)tblP.getValueAt(indexTB, 5));
            ngPhatHanh.setDate(dat);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Lỗi");
        }
    }//GEN-LAST:event_tblPMouseClicked

    private void tblLCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLCMouseClicked
        // TODO add your handling code here:
        int indexTB = tblLC.getSelectedRow();
        
        if (indexTB >=0 && indexTB <tblLC.getRowCount()){
            txtMaLC.setText(tblLC.getValueAt(indexTB, 0).toString());
            cbbPhim.setSelectedItem(tblLC.getValueAt(indexTB, 1).toString());
            }
        try{
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)tblLC.getValueAt(indexTB, 2));
            ngChieu.setDate(dat);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Lỗi");
        }
    }//GEN-LAST:event_tblLCMouseClicked

    private void tblSCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSCMouseClicked
        // TODO add your handling code here:
        int indexTB = tblSC.getSelectedRow();
       
        if (indexTB >=0 && indexTB <tblSC.getRowCount()){
            txtMaSC.setText(tblSC.getValueAt(indexTB, 0).toString());
            txtTenSC.setText(tblSC.getValueAt(indexTB, 1).toString());
            cbbMaLC.setSelectedItem(tblSC.getValueAt(indexTB, 2).toString());
            txtTenPhim.setText(tblSC.getValueAt(indexTB, 3).toString());
            txtNgChieu.setText(tblSC.getValueAt(indexTB, 4).toString());
            cbbPhong.setSelectedItem(tblSC.getValueAt(indexTB, 5).toString());
            txtSL.setText(tblSC.getValueAt(indexTB, 8).toString());
            try{
                Date t_TGBD = new SimpleDateFormat("HH:mm").parse((String)(tblSC.getValueAt(indexTB,6)));
                Date t_TGKT = new SimpleDateFormat("HH:mm").parse((String)(tblSC.getValueAt(indexTB,7)));
                Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)tblLC.getValueAt(indexTB, 2));
                //LC.setDate(dat);
                TGBD.setValue((Object)t_TGBD);
                TGKT.setValue((Object)t_TGKT);  
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Lỗi");
            }
            }
        
        
    }//GEN-LAST:event_tblSCMouseClicked

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked
        // TODO add your handling code here:
        int indexTB = tblNV.getSelectedRow();
        
        if (indexTB >=0 && indexTB <tblNV.getRowCount()){
            txtMaNV.setText(tblNV.getValueAt(indexTB, 0).toString());
            txtTenNV.setText(tblNV.getValueAt(indexTB, 1).toString());
            cbbGT.setSelectedItem(tblNV.getValueAt(indexTB, 3).toString());
            txtEmailNV.setText(tblNV.getValueAt(indexTB, 4).toString());
            txtSDTNV.setText(tblNV.getValueAt(indexTB, 5).toString());
            txtDiaChi.setText(tblNV.getValueAt(indexTB, 6).toString());
            txtLuong.setText(tblKH.getValueAt(indexTB, 8).toString());
            }
        try{
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)tblNV.getValueAt(indexTB, 2));
            ngSinhNV.setDate(dat);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Lỗi");
        }
        
        try{
            Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)tblNV.getValueAt(indexTB, 2));
            ngVaoLam.setDate(dat);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            System.out.println("Lỗi");
        }
    }//GEN-LAST:event_tblNVMouseClicked

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
            String strInsert = "insert into KHACHHANG (MAKH,TENKH,NGSINH,GIOITINH, EMAIL, SDT,TENDN,MK, TONGTIEN) values (?, ?, ?, ?, ?, ?, ?, ?, 0)";
            try{
                PreparedStatement pres = con.prepareStatement(strInsert);
                pres.setString(1, txtMaKH.getText());
                pres.setString(2, txtTenKH.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(3, df.format(ngSinh.getDate()));
                if (cbbGioiTinh.getSelectedItem().toString().equals("Nữ") == true){
                    pres.setBoolean(4, true);
                }
                else{
                    pres.setBoolean(4, false);
                }
                pres.setString(5, txtEmail.getText());
                pres.setString(6, txtSDT.getText());
                pres.setString(7, txtTenDN.getText());
                pres.setString(8, txtMK.getText());
                pres.executeUpdate();
                con.close();
                loadTableKH();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void btnThemLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLCActionPerformed
        // TODO add your handling code here:
            Connection con = new DBUtils().createConn();
            String strInsert = "insert into LICHCHIEU (MALC, MAPHIM, NGCHIEU) values (?, ?, ?)";
            try{
                PreparedStatement pres = con.prepareStatement(strInsert);
                pres.setString(1, txtMaLC.getText());
                pres.setString(2, maPhim.get(cbbPhim.getSelectedIndex()));
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(3, df.format(ngChieu.getDate()));
                pres.executeUpdate();
                con.close();
                loadTableLC();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_btnThemLCActionPerformed

    private void btnThemSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSCActionPerformed
        // TODO add your handling code here:
            try{
                Connection con = new DBUtils().createConn();
                String strInsert = "insert into SUATCHIEU (MASC, TENSC, MALC, MAPHONG, TGBD, TGKT, SLGHE) values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pres= con.prepareStatement(strInsert);
                pres.setString(1, txtMaSC.getText());
                pres.setString(2, txtTenSC.getText());
                pres.setString(3, cbbMaLC.getSelectedItem().toString());
                pres.setString(4, maPhong.get(cbbPhong.getSelectedIndex()));
                DateFormat tf = new SimpleDateFormat("HH:mm");
                pres.setString(5, tf.format(TGBD.getValue()));
                pres.setString(6, tf.format(TGKT.getValue()));
                pres.setString(7, txtSL.getText());
                pres.executeUpdate();
                con.close();
                loadTableSC();
                loadGhe();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu không thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
                
    }//GEN-LAST:event_btnThemSCActionPerformed

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
            String strInsert = "insert into NHANVIEN (MANV, TENNV, NGSINH, GIOITINH, EMAIL, SODT, DIACHI , NGVAOLAM, MUCLUONG) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try{
                PreparedStatement pres = con.prepareStatement(strInsert);
                pres.setString(1, txtMaNV.getText());
                pres.setString(2, txtTenNV.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(3, df.format(ngSinhNV.getDate()));
                if (cbbGT.getSelectedItem().toString().equals("Nữ") == true){
                    pres.setBoolean(4, true);
                }
                else{
                    pres.setBoolean(4, false);
                }
                pres.setString(5, txtEmail.getText());
                pres.setString(6, txtSDT.getText());
                pres.setString(7, txtDiaChi.getText());
                pres.setString(8, df.format(ngVaoLam.getDate()));
                pres.setString(9, txtLuong.getText());
                pres.executeUpdate();
                con.close();
                loadTableNV();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu không thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void btnSuaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKHActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn sửa không?", "Sửa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Connect DB
           Connection conn = new DBUtils().createConn();

           try{
               String strUpdate = "update KHACHHANG set TENKH = ?, NGSINH = ?, GIOITINH = ?, EMAIL = ?, SDT = ?, TENDN = ?, MK = ? where MAKH =?";
               PreparedStatement pres= conn.prepareStatement(strUpdate);
               pres.setString(1, txtTenKH.getText());
               DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
               pres.setString(2, df.format(ngSinh.getDate()));
               if (cbbGioiTinh.getSelectedItem().toString().equals("Nữ") == true){
                    pres.setBoolean(3, true);
               }
               else{
                    pres.setBoolean(3, false);
               }
               pres.setString(4, txtEmail.getText());
               pres.setString(5, txtSDT.getText());
               pres.setString(6, txtTenDN.getText());
               pres.setString(7, txtMK.getText());
               pres.setString(8, txtMaKH.getText());
               pres.executeUpdate();
               conn.close();
               loadTableKH();
            JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSuaKHActionPerformed

    private void btnSuaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn sửa không?", "Sửa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            Connection con = new DBUtils().createConn();
            try{
                String strUpdate = "update PHIM set TENPHIM = ?, MATL = ?, DAODIEN = ?, NGPHATHANH = ?, THOILUONG = ?, DOANHTHU = ? where MAPhHIM =?";
                PreparedStatement pres= con.prepareStatement(strUpdate);
                pres.setString(1, txtTenP.getText());
                pres.setString(2, maTL.get(cbbTheLoai.getSelectedIndex()));
                pres.setString(3, txtDaoDien.getText());
                pres.setString(4, txtDienVien.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(5, df.format(ngPhatHanh.getDate()));
                pres.setString(6, txtThoiLuong.getText());
                pres.setString(7, txtDoanhThu.getText());
                pres.setString(8,txtMaP.getText());
                pres.executeUpdate();
                con.close();
                loadTablePhim();
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSuaPActionPerformed

    private void btnSuaLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaLCActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn sửa không?", "Sửa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            Connection con = new DBUtils().createConn();
            try{
                String strUpdate = "update LICHCHIEU set MAPHIM = ?, NGCHIEU = ? where MALC =?";
                PreparedStatement pres= con.prepareStatement(strUpdate);
                pres.setString(3, txtMaLC.getText());
                pres.setString(1, maPhim.get(cbbPhim.getSelectedIndex()));
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(2, df.format(ngChieu.getDate()));
                pres.executeUpdate();
                con.close();
                loadTableLC();
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSuaLCActionPerformed

    private void btnSuaSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSCActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn sửa không?", "Sửa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            Connection con = new DBUtils().createConn();
            try{
                String strUpdate = "update SUATCHIEU set TENSC = ?, MaLC = ?, MAPHONG = ?, TGBD = ?, TGKT = ?, SLGHE = ? where MASC =?";
                PreparedStatement pres= con.prepareStatement(strUpdate);
                pres.setString(7, txtMaSC.getText());
                pres.setString(1, txtTenSC.getText());
                pres.setString(2, cbbMaLC.getSelectedItem().toString());
                pres.setString(3, maPhong.get(cbbPhong.getSelectedIndex()));
                DateFormat tf = new SimpleDateFormat("HH:mm");
                pres.setString(4, tf.format(TGBD.getValue()));
                pres.setString(5, tf.format(TGKT.getValue()));
                pres.setString(6, txtSL.getText());
                pres.executeUpdate();
                con.close();
                loadTableSC();
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Loi");
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSuaSCActionPerformed

    private void btnSuaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNVActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn sửa không?", "Sửa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Connect DB
           Connection conn = new DBUtils().createConn();

           try{
               String strUpdate = "update NHANVIEN set TENNV = ?, NGSINH = ?, GIOITINH = ?, EMAIL = ?, SDT = ?, DIACHI = ?, NGVAOLAM = ?, MUCLUONG = ? where MANV =?";
               PreparedStatement pres= conn.prepareStatement(strUpdate);
                pres.setString(1, txtTenNV.getText());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                pres.setString(2, df.format(ngSinhNV.getDate()));
                if (cbbGT.getSelectedItem().toString().equals("Nữ") == true){
                    pres.setBoolean(3, true);
                }
                else{
                    pres.setBoolean(3, false);
                }
                pres.setString(4, txtEmail.getText());
                pres.setString(5, txtSDT.getText());
                pres.setString(6, txtDiaChi.getText());
                pres.setString(7, df.format(ngVaoLam.getDate()));
                pres.setString(8, txtLuong.getText());
                pres.setString(9, txtMaNV.getText());
                pres.executeUpdate();
                conn.close();
                loadTableNV();
                JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex){
                    ex.printStackTrace();
                    System.out.println("Loi");
                    JOptionPane.showMessageDialog(null, "Sửa  dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
        }

    }//GEN-LAST:event_btnSuaNVActionPerformed

    private void btnXoaNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNVActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa không?", "Xóa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Xóa khỏi database
            Connection conn = new DBUtils().createConn();
            String strSQL = "Delete from NHANVIEN where MANV = ?";
            try{
                PreparedStatement pres = conn.prepareStatement(strSQL);
                pres.setString(1, txtMaNV.getText());
                pres.executeUpdate();
                conn.close();
                loadTableKH();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnXoaNVActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa không?", "Xóa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Xóa khỏi database
            Connection conn = new DBUtils().createConn();
            String strSQL = "Delete from KHACHHANG where MAKH = ?";
            try{
                PreparedStatement pres = conn.prepareStatement(strSQL);
                pres.setString(1, txtMaKH.getText());
                pres.executeUpdate();
                conn.close();
                loadTableKH();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnXoaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa không?", "Xóa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Xóa khỏi database
            Connection conn = new DBUtils().createConn();
            String strSQL = "Delete from PHIM where MAPHIM = ?";
            try{
                PreparedStatement pres = conn.prepareStatement(strSQL);
                pres.setString(1, txtMaP.getText());
                pres.executeUpdate();
                conn.close();
                loadTablePhim();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnXoaPActionPerformed

    private void btnXoaLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLCActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa không?", "Xóa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Xóa khỏi database
            Connection conn = new DBUtils().createConn();
            String strSQL = "Delete from LICHCHIEU where MALC = ?";
            try{
                PreparedStatement pres = conn.prepareStatement(strSQL);
                pres.setString(1, txtMaLC.getText());
                pres.executeUpdate();
                conn.close();
                loadTableLC();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnXoaLCActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
        
        String strSQL = "SELECT * FROM KHACHHANG WHERE MAKH = '"+txtMaKH.getText()+"'";
        
        try {
            Statement stmt = con.createStatement(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(strSQL);
            
            //lấy dòng đầu tiên hoặc rs.first()
            if(rs.absolute(1)==false){
                JOptionPane.showMessageDialog(null,"Không tìm thấy khách hàng!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                txtTenKH.setText("");
                cbbGioiTinh.setSelectedItem("");
                txtEmail.setText("");
                txtSDT.setText("");
                txtTenDN.setText("");
                txtMK.setText("");
                txtTongTien.setText("");
                try{
                Date dat = new SimpleDateFormat("yyyy-MM-dd").parse("");
                ngSinh.setDate(dat);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            else {
                txtTenKH.setText(rs.getString("TenKH"));
                if(rs.getBoolean("GioiTinh")==true)
                    cbbGioiTinh.setSelectedItem("Nữ");
                else
                    cbbGioiTinh.setSelectedItem("Nam");
                txtEmail.setText(rs.getString("Email"));
                txtSDT.setText(rs.getString("SDT"));
                txtTenDN.setText(rs.getString("TenDN"));
                txtMK.setText(rs.getString("MK"));
                txtTongTien.setText(rs.getString("TongTien"));
                try{
                Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)rs.getString("ngSinh"));
                ngSinh.setDate(dat);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            con.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Lỗi");
            }
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void txtMaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
        
        String strSQL = "SELECT * FROM PHIM, THELOAIPHIM WHERE PHIM.MATL=THELOAIPHIM.MATL AND MAPHIM = '"+txtMaP.getText()+"'";
        
        try {
            Statement stmt = con.createStatement(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(strSQL);
            
            //lấy dòng đầu tiên hoặc rs.first()
            if(rs.absolute(1)==false){
                JOptionPane.showMessageDialog(null,"Không tìm thấy phim!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                txtTenP.setText("");
                cbbTheLoai.setSelectedItem("");
                txtDaoDien.setText("");
                txtDienVien.setText("");
                txtThoiLuong.setText("");
                try{
                Date dat = new SimpleDateFormat("yyyy-MM-dd").parse("");
                ngPhatHanh.setDate(dat);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            else {
                txtTenP.setText(rs.getString("TenPhim"));
                cbbTheLoai.setSelectedItem(rs.getString("TenTL"));
                txtDaoDien.setText(rs.getString("DaoDien"));
                txtDienVien.setText(rs.getString("DienVien"));
                txtThoiLuong.setText(rs.getString("ThoiLuong"));
                try{
                Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)rs.getString("ngPhatHanh"));
                ngPhatHanh.setDate(dat);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            con.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Lỗi");
            }
    }//GEN-LAST:event_txtMaPActionPerformed

    private void txtMaLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLCActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
        
        String strSQL = "SELECT * FROM LICHCHIEU, PHIM WHERE PHIM.MAPHIM=LICHCHIEU.MAPHIM AND MALC = '"+txtMaLC.getText()+"'";
        
        try {
            Statement stmt = con.createStatement(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(strSQL);
            
            //lấy dòng đầu tiên hoặc rs.first()
            if(rs.absolute(1)==false){
                JOptionPane.showMessageDialog(null,"Không tìm thấy lịch chiếu!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                cbbPhim.setSelectedItem("");
                try{
                    Date dat = new SimpleDateFormat("yyyy-MM-dd").parse("");
                    ngChieu.setDate(dat);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            else {
                cbbPhim.setSelectedItem(rs.getString("TenPhim"));
                try{
                Date dat = new SimpleDateFormat("yyyy-MM-dd").parse((String)rs.getString("ngChieu"));
                ngChieu.setDate(dat);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            con.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Lỗi");
            }
    }//GEN-LAST:event_txtMaLCActionPerformed

    private void txtMaSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaSCActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
        
        String strSQL = "SELECT * FROM SUATCHIEU, PHONG, LICHCHIEU, PHIM \n"
                       +"WHERE SUATCHIEU.MAPHONG = PHONG.MAPHONG AND LICHCHIEU.MALC=SUATCHIEU.MALC AND PHIM.MAPHIM=LICHCHIEU.MAPHIM AND MASC = '"+txtMaSC.getText()+"'";
        
        try {
            Statement stmt = con.createStatement(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(strSQL);
            
            //lấy dòng đầu tiên hoặc rs.first()
            if(rs.absolute(1)==false){
                JOptionPane.showMessageDialog(null,"Không tìm thấy suất chiếu!","Thông báo", JOptionPane.INFORMATION_MESSAGE);
                txtTenSC.setText("");
                cbbMaLC.setSelectedItem("");
                txtTenPhim.setText("");
                cbbPhong.setSelectedItem("");
                txtNgChieu.setText("");
                try{
                    Date t_TGBD = new SimpleDateFormat("HH:mm").parse("");
                    Date t_TGKT = new SimpleDateFormat("HH:mm").parse("");
                    TGBD.setValue((Object)t_TGBD);
                    TGKT.setValue((Object)t_TGKT);  
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("Lỗi");
                }
            }
            else {
                txtTenSC.setText(rs.getString("TenSC"));
                cbbMaLC.setSelectedItem(rs.getString("LICHCHIEU.MaLC"));
                txtTenPhim.setText(rs.getString("TenPhim"));
                cbbPhong.setSelectedItem(rs.getString("TenPhong"));
                txtNgChieu.setText("NgChieu");
                try{
                    Date t_TGBD = new SimpleDateFormat("HH:mm").parse((String)rs.getString("TGBD"));
                    Date t_TGKT = new SimpleDateFormat("HH:mm").parse((String)rs.getString("TGKT"));
                    TGBD.setValue((Object)t_TGBD);
                    TGKT.setValue((Object)t_TGKT);  
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
                System.out.println("Lỗi");
            }
            }
            con.close();
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Lỗi");
            }
    }//GEN-LAST:event_txtMaSCActionPerformed

    private void txtTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHActionPerformed

    private void txtTenDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDNActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void cbbPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbPhimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbPhimActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnXoaSCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSCActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn xóa không?", "Xóa dữ liệu", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
            // Xóa khỏi database
            Connection conn = new DBUtils().createConn();
            String strSQL = "Delete from SUATCHIEU where MASC = ? ";
            try{
                PreparedStatement pres = conn.prepareStatement(strSQL);
                pres.setString(1, txtMaSC.getText());
                pres.executeUpdate();
                conn.close();
                loadTableSC();
            } catch (SQLException e) {
                System.out.println(e);
            }
            Connection con = new DBUtils().createConn();
            String SQL = "Delete from GHE where MASC = ? ";
            try{
                PreparedStatement pres = con.prepareStatement(SQL);
                pres.setString(1, txtMaSC.getText());
                pres.executeUpdate();
                con.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_btnXoaSCActionPerformed

    private void cbbMaLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMaLCActionPerformed
        // TODO add your handling code here:
        Connection con = new DBUtils().createConn();
        
        String strSQL = "SELECT * FROM LICHCHIEU, PHIM WHERE LICHCHIEU.MAPHIM=PHIM.MAPHIM AND MALC = '"+cbbMaLC.getSelectedItem()+"'";
        
        try {
            Statement stmt = con.createStatement(
                                  ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(strSQL);
            
            //lấy dòng đầu tiên hoặc rs.first()
            if(rs.next()){
                txtTenPhim.setText(rs.getString("TenPhim"));
                txtNgChieu.setText(rs.getString("NgChieu"));
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Lỗi");
        }
    }//GEN-LAST:event_cbbMaLCActionPerformed

    private void txtSLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSLActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        mainpage.main(null);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner TGBD;
    private javax.swing.JSpinner TGKT;
    private javax.swing.JButton btnSuaKH;
    private javax.swing.JButton btnSuaLC;
    private javax.swing.JButton btnSuaNV;
    private javax.swing.JButton btnSuaP;
    private javax.swing.JButton btnSuaSC;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnThemLC;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnThemP;
    private javax.swing.JButton btnThemSC;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JButton btnXoaLC;
    private javax.swing.JButton btnXoaNV;
    private javax.swing.JButton btnXoaP;
    private javax.swing.JButton btnXoaSC;
    private javax.swing.JComboBox<String> cbbGT;
    private javax.swing.JComboBox<String> cbbGioiTinh;
    private javax.swing.JComboBox<String> cbbMaLC;
    private javax.swing.JComboBox<String> cbbPhim;
    private javax.swing.JComboBox<String> cbbPhong;
    private javax.swing.JComboBox<String> cbbTheLoai;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser ngChieu;
    private com.toedter.calendar.JDateChooser ngPhatHanh;
    private com.toedter.calendar.JDateChooser ngSinh;
    private com.toedter.calendar.JDateChooser ngSinhNV;
    private com.toedter.calendar.JDateChooser ngVaoLam;
    private javax.swing.JTable tblKH;
    private javax.swing.JTable tblLC;
    private javax.swing.JTable tblNV;
    private javax.swing.JTable tblP;
    private javax.swing.JTable tblSC;
    private javax.swing.JTextField txtDaoDien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDienVien;
    private javax.swing.JTextField txtDoanhThu;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailNV;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMK;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaLC;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMaP;
    private javax.swing.JTextField txtMaSC;
    private javax.swing.JTextField txtNgChieu;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSDTNV;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtTenDN;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTenP;
    private javax.swing.JTextField txtTenPhim;
    private javax.swing.JTextField txtTenSC;
    private javax.swing.JTextField txtThoiLuong;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
