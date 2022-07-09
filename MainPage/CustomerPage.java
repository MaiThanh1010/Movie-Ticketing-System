/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MainPage;

import Connection.DBUtils;
import com.itextpdf.text.PageSize;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
/**
 *
 * @author nguye
 */
public class CustomerPage extends javax.swing.JFrame {

    /**
     * Creates new form CustomerPage
     */
    ArrayList<String> maPhim = new ArrayList<>();
    ArrayList<String> tenPhim = new ArrayList<>();
    ArrayList<String> maLC = new ArrayList<>();
    ArrayList<String> ngChieu = new ArrayList<>();
    ArrayList<String> maSC = new ArrayList<>();
    ArrayList<String> SC = new ArrayList<>();
    ArrayList<String> TGBD = new ArrayList<>();
    ArrayList<String> TGKT = new ArrayList<>();
    ArrayList<Integer> a = new ArrayList<>();
    ArrayList<Integer> b = new ArrayList<>();
    ArrayList<Integer> x = new ArrayList<>();
    ArrayList<Integer> y = new ArrayList<>();
    ArrayList<Integer> ghe = new ArrayList<>();
    DefaultTableModel tblModelGhe = null;
    private String TenDN;
    private int SLGhe = 50;
    private int SL = 0; 
    public CustomerPage(String TenDN) {
        initComponents();
        setSize(900,500);
        //center the form
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        cbbGioiTinh.setModel(new DefaultComboBoxModel<>(new String[]{"Nam", "Nữ"}));
        LoadCbbTenPhim();
        LoadCbbNgChieu();
        LoadCbbSuatChieu();
        this.TenDN=TenDN;
        loadjTabbedPane2();
        setVisible(true);
        txtSL.setEditable(false);
        //setResizable(false);*/
        
        /*initComponents();
        //Các bước set giao diện
        /*1. Set kích thước giao diện*/
        //setSize(900,500);
        /*2. Set nút chọn tắt mặc định EXIT_ON_CLOSE*/ 
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*3. Tiêu đề*/
        //setTitle("Trang thành viên");
        /*4. Không cho phóng to*/
        //showGhe();
        //setResizable(false);
        /*5. Vị trí trang*/
        //setLocation(100,40);
        //Thiết lập lấy thông tin người dùng
        //getKH(TenDN);
        //Thiết lập lấy thông tin phim
        /*getNgayChieu();
        lichChoose.setModel(new DefaultComboBoxModel(ngaychieu.toArray()));
        phimChoose.setSelectedIndex(-1);
        eventButton();
        /*lichChoose.removeAllItems();*/
        
    }
    
     public void LoadCbbTenPhim() {
         maPhim.remove(maPhim);
         tenPhim.remove(tenPhim);
        Connection con = new DBUtils().createConn();
        try {
            String strSQL = "SELECT * FROM PHIM";
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery(strSQL);
            while (rs.next()) {
                maPhim.add(rs.getString("MaPhim"));
                tenPhim.add(rs.getString("TenPhim"));
            }

            cbbTenPhim.setModel(new DefaultComboBoxModel<String>(tenPhim.toArray(new String[0])));
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Lỗi");
        }
    }

   public void LoadCbbNgChieu() {
        ngChieu.removeAll(ngChieu);
        maLC.removeAll(maLC);

        Connection con = new DBUtils().createConn();
       try {
           String strSQL = "SELECT DISTINCT * FROM LICHCHIEU WHERE LICHCHIEU.MAPHIM = ?";
           PreparedStatement pres = con.prepareStatement(strSQL);
           pres.setString(1, maPhim.get(cbbTenPhim.getSelectedIndex()));
           ResultSet rs = pres.executeQuery();
           while (rs.next()) {
               ngChieu.add(rs.getString("NgChieu"));
               maLC.add(rs.getString("MALC"));
           }
           cbbNgChieu.setModel(new DefaultComboBoxModel<String>(ngChieu.toArray(new String[0])));
           con.close();
       } catch (SQLException e) {
           System.out.println(e);
           System.out.println("Lỗi");
       }
    }
    public void LoadCbbSuatChieu() {
        maSC.removeAll(maSC);
        SC.removeAll(SC);
        TGBD.removeAll(TGBD);
        TGKT.removeAll(TGKT);
        Connection con = new DBUtils().createConn();
        try {
            String strSQL = "SELECT DISTINCT SUATCHIEU.TENSC, SUATCHIEU.MASC, TGBD, TGKT FROM SUATCHIEU,LICHCHIEU,PHIM WHERE SUATCHIEU.MALC=LICHCHIEU.MALC AND LICHCHIEU.MAPHIM=PHIM.MAPHIM AND SUATCHIEU.MALC = ? AND LICHCHIEU.MAPHIM= ?";
            PreparedStatement pres = con.prepareStatement(strSQL);
            pres.setString(1, maLC.get(cbbNgChieu.getSelectedIndex()));
            pres.setString(2, maPhim.get(cbbTenPhim.getSelectedIndex()));
            ResultSet rs = pres.executeQuery();
            while (rs.next()) {
                maSC.add(rs.getString("MaSC"));
                TGBD.add(rs.getString("TGBD"));
                TGKT.add(rs.getString("TGKT"));
                SC.add(TGBD +" - "+ TGKT);
            }
            cbbSuatChieu.setModel(new DefaultComboBoxModel<String>(SC.toArray(new String[0])));
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Lỗi");
        }
    }
    
    public void TableGhe() {
        a.removeAll(a);
        b.removeAll(b);
        Connection con = new DBUtils().createConn();
        try {
            String strSQL = "SELECT HANGGHE, SOGHE FROM GHE\n"
                    + "WHERE MASC=? AND TRANGTHAI = 'FALSE'";
            PreparedStatement pres = con.prepareStatement(strSQL);
            pres.setString(1, maSC.get(cbbSuatChieu.getSelectedIndex()));
            ResultSet rs = pres.executeQuery();
            while (rs.next()) {
                a.add((rs.getInt("HangGhe")));
                b.add((rs.getInt("SoGhe")));
            }
            //System.out.print(a.size());
            for(int i=0;i<a.size();i++){
                tblGhe.setValueAt("N/A", a.get(i)-1, b.get(i));
            }
            con.close();
        }catch (HeadlessException | SQLException ex)
        {
           System.out.println(ex);
        } 
        setVisible(true);
    }
    
    private CustomerPage(){
        
    }
    
    

    public void loadjTabbedPane2(){
        Connection con = new DBUtils().createConn();

        String strSQL = "SELECT * FROM KHACHHANG WHERE TENDN = '"+TenDN+"'";

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(strSQL);
            while(rs.next()){
                txtMaKH.setText(rs.getString("MaKH"));
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
            rs.close();
            
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Lỗi");
        }
    }
    
    public void Bill(){
        String tenphim = cbbTenPhim.getSelectedItem().toString();
        String ngchieu = cbbNgChieu.getSelectedItem().toString();
        String suatchieu = cbbSuatChieu.getSelectedItem().toString();
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "*************************************************************************************\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "************************************** THÔNG TIN ĐẶT VÉ ***********************\n\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "Tên phim       :" + "\t" + tenphim+ "\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "Ngày chiếu     :" + "\t" + ngchieu + "\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "Suất chiếu     :" + "\t" + suatchieu + "\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "Số lượng vé    :" + "\t" + SL + "\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "Tổng tiền      :" + "\t" + SL*45000 + "\n\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "*************************************************************************************\n");
        jtxtXacNhan.setText(jtxtXacNhan.getText() + "*************************************************************************************\n");
    }

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtTenDN = new javax.swing.JTextField();
        txtMK = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        ngSinh = new com.toedter.calendar.JDateChooser();
        cbbGioiTinh = new javax.swing.JComboBox<>();
        btnSuaThongTin = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblTenPhim = new javax.swing.JLabel();
        lblNgChieu = new javax.swing.JLabel();
        lblSuatChieu = new javax.swing.JLabel();
        cbbTenPhim = new javax.swing.JComboBox<>();
        cbbNgChieu = new javax.swing.JComboBox<>();
        cbbSuatChieu = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtTongTienTemp = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtxtXacNhan = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGhe = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtSoGhe = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSL = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/xoa.jpg"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(870, 5, 20, 20);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/arrow-92-24.jpg"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(830, 1, 30, 30);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 204));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel1.setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/mainbig.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, -30, 900, 500);

        jTabbedPane1.addTab("Trang chủ", jPanel1);

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Mã khách hàng");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(70, 90, 150, 25);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 51, 51));
        jLabel6.setText("Họ và tên");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(70, 130, 120, 25);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 51));
        jLabel7.setText("Tên đăng nhập");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(70, 170, 130, 25);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 51));
        jLabel8.setText("Mật khẩu");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(70, 210, 120, 25);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("Email");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(70, 250, 50, 25);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("Số điện thoại");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(480, 90, 150, 25);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 51));
        jLabel11.setText("Ngày sinh");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(480, 130, 100, 25);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 51, 51));
        jLabel12.setText("Giới tính");
        jPanel2.add(jLabel12);
        jLabel12.setBounds(480, 170, 100, 25);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 51, 51));
        jLabel13.setText("Tổng tiền tích lũy");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(480, 210, 120, 25);

        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });
        jPanel2.add(txtMaKH);
        txtMaKH.setBounds(190, 90, 200, 25);

        txtTenKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKHActionPerformed(evt);
            }
        });
        jPanel2.add(txtTenKH);
        txtTenKH.setBounds(190, 130, 200, 25);

        txtTenDN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDNActionPerformed(evt);
            }
        });
        jPanel2.add(txtTenDN);
        txtTenDN.setBounds(190, 170, 200, 25);
        jPanel2.add(txtMK);
        txtMK.setBounds(190, 210, 200, 25);

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        jPanel2.add(txtEmail);
        txtEmail.setBounds(190, 250, 200, 25);

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });
        jPanel2.add(txtSDT);
        txtSDT.setBounds(630, 90, 200, 25);
        jPanel2.add(txtTongTien);
        txtTongTien.setBounds(630, 210, 200, 25);
        jPanel2.add(ngSinh);
        ngSinh.setBounds(630, 130, 200, 25);

        cbbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGioiTinhActionPerformed(evt);
            }
        });
        jPanel2.add(cbbGioiTinh);
        cbbGioiTinh.setBounds(630, 170, 100, 25);

        btnSuaThongTin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSuaThongTin.setForeground(new java.awt.Color(255, 51, 51));
        btnSuaThongTin.setText("Sửa thông tin cá nhân");
        jPanel2.add(btnSuaThongTin);
        btnSuaThongTin.setBounds(70, 340, 190, 26);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel2.add(jLabel14);
        jLabel14.setBounds(0, -30, 900, 500);

        jTabbedPane1.addTab("Thông tin cá nhân", jPanel2);

        jPanel4.setLayout(null);

        lblTenPhim.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTenPhim.setForeground(new java.awt.Color(255, 51, 51));
        lblTenPhim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenPhim.setText("TÊN PHIM");
        jPanel4.add(lblTenPhim);
        lblTenPhim.setBounds(70, 20, 100, 25);

        lblNgChieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblNgChieu.setForeground(new java.awt.Color(255, 51, 51));
        lblNgChieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNgChieu.setText("NGÀY CHIẾU");
        jPanel4.add(lblNgChieu);
        lblNgChieu.setBounds(480, 20, 100, 25);

        lblSuatChieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSuatChieu.setForeground(new java.awt.Color(255, 51, 51));
        lblSuatChieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSuatChieu.setText("SUẤT CHIẾU");
        jPanel4.add(lblSuatChieu);
        lblSuatChieu.setBounds(70, 60, 100, 25);

        cbbTenPhim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTenPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTenPhimActionPerformed(evt);
            }
        });
        jPanel4.add(cbbTenPhim);
        cbbTenPhim.setBounds(190, 20, 200, 25);

        cbbNgChieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbNgChieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNgChieuActionPerformed(evt);
            }
        });
        jPanel4.add(cbbNgChieu);
        cbbNgChieu.setBounds(630, 20, 200, 25);

        cbbSuatChieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbSuatChieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSuatChieuActionPerformed(evt);
            }
        });
        jPanel4.add(cbbSuatChieu);
        cbbSuatChieu.setBounds(190, 60, 200, 25);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 51));
        jLabel5.setText("TỔNG TIỀN");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(480, 60, 100, 25);
        jPanel4.add(txtTongTienTemp);
        txtTongTienTemp.setBounds(630, 60, 200, 25);

        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 51, 51));
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel4.add(btnThanhToan);
        btnThanhToan.setBounds(610, 400, 120, 26);

        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel4.add(btnXacNhan);
        btnXacNhan.setBounds(90, 400, 120, 25);

        jtxtXacNhan.setColumns(20);
        jtxtXacNhan.setRows(5);
        jScrollPane2.setViewportView(jtxtXacNhan);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(500, 120, 340, 240);

        tblGhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1),  new Integer(1),  new Integer(2),  new Integer(3),  new Integer(4),  new Integer(5)},
                { new Integer(2),  new Integer(6),  new Integer(7),  new Integer(8),  new Integer(9),  new Integer(10)},
                { new Integer(3),  new Integer(11),  new Integer(12),  new Integer(13),  new Integer(14),  new Integer(15)},
                { new Integer(4),  new Integer(16),  new Integer(17),  new Integer(18),  new Integer(19),  new Integer(20)},
                { new Integer(5),  new Integer(21),  new Integer(22),  new Integer(23),  new Integer(24),  new Integer(25)}
            },
            new String [] {
                "Hàng", "1", "2", "3", "4", "5"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblGhe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGheMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGhe);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(60, 120, 340, 140);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 51));
        jLabel3.setText("Nhập số thứ tự ghế");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(65, 290, 150, 25);

        txtSoGhe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtSoGheMouseEntered(evt);
            }
        });
        txtSoGhe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoGheActionPerformed(evt);
            }
        });
        jPanel4.add(txtSoGhe);
        txtSoGhe.setBounds(250, 290, 100, 25);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("Số lượng");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(65, 340, 100, 25);
        jPanel4.add(txtSL);
        txtSL.setBounds(250, 340, 100, 25);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/z3467763434728_04b153734553a51e6bdfe42ea1113ae9.jpg"))); // NOI18N
        jPanel4.add(jLabel4);
        jLabel4.setBounds(0, -30, 900, 500);

        jTabbedPane1.addTab("Đặt vé", jPanel4);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 900, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void cbbTenPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTenPhimActionPerformed
        // TODO add your handling code here:
        LoadCbbNgChieu();
        LoadCbbSuatChieu();

    }//GEN-LAST:event_cbbTenPhimActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel2MouseClicked

    private void cbbGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGioiTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbGioiTinhActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSDTActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtTenDNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDNActionPerformed

    private void txtTenKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMaKHActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        int ret = JOptionPane.showConfirmDialog(null,"Bạn có muốn thanh toán?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION){
                Connection con = new DBUtils().createConn();
            try{
                for (int i=0;i<a.size();i++){
                String strUpdate = "update GHE set TRANGTHAI = ? where MASC =? and HANGGHE = ? and SOGHE=?";
                PreparedStatement pres= con.prepareStatement(strUpdate);
                pres.setBoolean(1, false);
                pres.setString(2, maSC.get(cbbSuatChieu.getSelectedIndex()));
                pres.setInt(3, x.get(i));
                pres.setInt(4, y.get(i));
                pres.executeUpdate();
                }
                
                String strUpdate1 ="Update KHACHHANG set TONGTIEN=TONGTIEN+? where KHACHHANG.TENDN=?";
                PreparedStatement pres1= con.prepareStatement(strUpdate1);
                pres1.setString(1, txtTongTienTemp.getText());
                pres1.setString(2, TenDN);
                pres1.executeUpdate();
                
                String strUpdate2 ="Update PHIM set DOANHTHU=DOANHTHU+? where PHIM.MAPHIM=?";
                PreparedStatement pres2= con.prepareStatement(strUpdate2);
                pres2.setString(1, txtTongTienTemp.getText());
                pres2.setString(2, maPhim.get(cbbTenPhim.getSelectedIndex()));
                pres2.executeUpdate();
                con.close();
               } catch (SQLException ex){
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Thanh toán thành công !", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            TableGhe();
        }
        
        
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void cbbSuatChieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSuatChieuActionPerformed
        // TODO add your handling code here:
        TableGhe();
    }//GEN-LAST:event_cbbSuatChieuActionPerformed

    private void cbbNgChieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNgChieuActionPerformed
        // TODO add your handling code here:
        LoadCbbSuatChieu();
    }//GEN-LAST:event_cbbNgChieuActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        // TODO add your handling code here:
         Connection con = new DBUtils().createConn();
        try {

                JOptionPane.showMessageDialog(null, "Thông tin xác nhận", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                Bill();

            con.close();
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Loi");
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void txtSoGheMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoGheMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtSoGheMouseEntered

    private void txtSoGheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoGheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoGheActionPerformed

    private void tblGheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGheMouseClicked
        // TODO add your handling code here:
        ghe.removeAll(ghe);
        int indexRow = tblGhe.getSelectedRow();
        int indexColumn = tblGhe.getSelectedColumn();
        if(tblGhe.getValueAt(indexRow, indexColumn)!="N/A"){
            //ghe.add("Integer.parseInt(tblGhe.getValueAt(indexRow, indexColumn)");
            x.add(indexRow+1);
            y.add(indexColumn);
            txtSoGhe.setText(txtSoGhe.getText()+tblGhe.getValueAt(indexRow, indexColumn)+"  ");
            SL++;
            txtSL.setText(String.valueOf(SL));
            txtTongTienTemp.setText(String.valueOf(SL*45000));
        }
        //System.out.println(ghe.size());
        System.out.println(x.size());
        System.out.println(y.size());
    }//GEN-LAST:event_tblGheMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        mainpage.main(null);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSuaThongTin;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JComboBox<String> cbbGioiTinh;
    private javax.swing.JComboBox<String> cbbNgChieu;
    private javax.swing.JComboBox<String> cbbSuatChieu;
    private javax.swing.JComboBox<String> cbbTenPhim;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jtxtXacNhan;
    private javax.swing.JLabel lblNgChieu;
    private javax.swing.JLabel lblSuatChieu;
    private javax.swing.JLabel lblTenPhim;
    private com.toedter.calendar.JDateChooser ngSinh;
    private javax.swing.JTable tblGhe;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMK;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSL;
    private javax.swing.JTextField txtSoGhe;
    private javax.swing.JTextField txtTenDN;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTongTien;
    private javax.swing.JTextField txtTongTienTemp;
    // End of variables declaration//GEN-END:variables
}
