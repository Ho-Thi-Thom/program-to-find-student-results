package dal;

import com.google.gson.Gson;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AllPoint;
import model.AllPointId;
import model.InforStudentData;
import model.Infor_ViThu_BangXepHang;

/**
 *
 * @author HO_THI_THOM
 */
public class StudentDAL {

    Connection conn = null;
    sqlConnection connect = new sqlConnection();

    public void ConnectDB() {
        if (conn == null) {
            try {
                conn = connect.getConnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<InforStudentData> getAllSVDTO(String a) {
        ConnectDB();
        ArrayList<InforStudentData> list = new ArrayList<InforStudentData>();
//        String sql = "SELECT *\n"
//                + "FROM dbo.inforStudents  \n"
//                + "WHERE faculty=N'Công nghệ thông tin' AND keyid='18' AND branch='Công nghệ thông tin' \n"
//                + "ORDER BY mark10 DESC\n";
        String sql = "SELECT *\n"
                + "FROM dbo.inforStudents  \n"
                + a
                + "ORDER BY mark10 DESC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();//doc bo dau tien
            while (rs.next()) {
                InforStudentData st = new InforStudentData();
                st.setStudenID(rs.getString(1));
                st.setNameStudent(rs.getString(2));
                st.setKey(rs.getString(3));
                list.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int IndexSV(String mssv, String a) {
        ArrayList<InforStudentData> st = new ArrayList<>();
        st = getAllSVDTO(a);
        int i = 0;
        for (InforStudentData item : st) {
            if (item.getStudenID().equals(mssv)) {
                return i;
            }
            i++;
        }
        return i;
    }

    public InforStudentData getTTSV(String mssv) {
        ConnectDB();
        InforStudentData st = new InforStudentData();
        String sql = "select *\n"
                + "from inforStudents\n"
                + "where studenID='" + mssv + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                st.setStudenID(rs.getString(1));
                st.setNameStudent(rs.getString(2));
                st.setKey(rs.getString(3));
                st.setSex(rs.getString(4));
                st.setBirthplace(rs.getString(5));
                st.setClasss(rs.getString(6));
                st.setBranch(rs.getString(7));
                st.setSpecialized(rs.getString(8));
                st.setFaculty(rs.getString(9));
                st.setEducate(rs.getString(10));
                st.setCourse(rs.getString(11));
                st.setConsultant(rs.getString(12));
                st.setBirthday(rs.getString(13));
                st.setCredit(rs.getString(14));
                st.setMark10(rs.getString(15));
                st.setMark4(rs.getString(15));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return st;
    }

    public ArrayList<InforStudentData> svtr_s(int indexx, String a) {
        ConnectDB();
        ArrayList<InforStudentData> list_trc = new ArrayList<InforStudentData>();
        ArrayList<InforStudentData> list_s = new ArrayList<InforStudentData>();
        int index = indexx;
        String sql1 = "SELECT *\n"
                + "FROM dbo.inforStudents  \n"
                + a
                + "ORDER BY mark10 DESC\n"
                + "OFFSET " + (index - 10) + " ROWS\n"
                + "FETCH NEXT 10 ROWS ONLY";
        try {
            PreparedStatement ps = conn.prepareStatement(sql1);
            ResultSet rs = ps.executeQuery();//doc bo dau tien
            while (rs.next()) {
                InforStudentData st = new InforStudentData();
                st.setStudenID(rs.getString(1));
                st.setNameStudent(rs.getString(2));
                st.setKey(rs.getString(3));
                st.setSex(rs.getString(4));
                st.setBirthplace(rs.getString(5));
                st.setClasss(rs.getString(6));
                st.setBranch(rs.getString(7));
                st.setSpecialized(rs.getString(8));
                st.setFaculty(rs.getString(9));
                st.setEducate(rs.getString(10));
                st.setCourse(rs.getString(11));
                st.setConsultant(rs.getString(12));
                st.setBirthday(rs.getString(13));
                st.setCredit(rs.getString(14));
                st.setMark10(rs.getString(15));
                st.setMark4(rs.getString(15));
                list_trc.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql2 = "SELECT *\n"
                + "FROM dbo.inforStudents  \n"
                + a
                + "ORDER BY mark10 DESC\n"
                + "OFFSET " + (index) + " ROWS\n"
                + "FETCH NEXT 11 ROWS ONLY";
        try {
            PreparedStatement ps = conn.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InforStudentData st = new InforStudentData();
                st.setStudenID(rs.getString(1));
                st.setNameStudent(rs.getString(2));
                st.setKey(rs.getString(3));
                st.setSex(rs.getString(4));
                st.setBirthplace(rs.getString(5));
                st.setClasss(rs.getString(6));
                st.setBranch(rs.getString(7));
                st.setSpecialized(rs.getString(8));
                st.setFaculty(rs.getString(9));
                st.setEducate(rs.getString(10));
                st.setCourse(rs.getString(11));
                st.setConsultant(rs.getString(12));
                st.setBirthday(rs.getString(13));
                st.setCredit(rs.getString(14));
                st.setMark10(rs.getString(15));
                st.setMark4(rs.getString(15));
                list_s.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<InforStudentData> list3 = new ArrayList<>();
        list3.addAll(list_trc);
        list3.addAll(list_s);
//        for (InforStudentData item : list3) {
//            System.out.println("mssv: " + item.getStudenID() + " Tên: " + item.getNameStudent());
//        }
        return list3;
    }

    public String getDsXepHang_ViThu(String mssv, String khoa, String khoahoc, String nganh) {
        InforStudentData st = getTTSV(mssv);
        String khoasv = st.getFaculty();
        String khoahocsv = st.getKey();
        String nganhhocsv = st.getBranch();
        String chuoitruyvan = "";//chuoitruyvan="WHERE faculty=N'"+khoasv+"' AND keyid='"+khoahocsv+"' AND branch=N'"+nganhhocsv+"' \n";
        if (khoa.equals("k") && khoahoc.equals("k") && nganh.equals("k")) {
            chuoitruyvan = "";
        } else if (khoa.equals("c") && khoahoc.equals("c")) {
            chuoitruyvan = "WHERE faculty=N'" + khoasv + "' AND keyid='" + khoahocsv + "' \n";
        } else if (khoa.equals("c") && khoahoc.equals("k")) {
            chuoitruyvan = "WHERE faculty=N'" + khoasv + "' \n";
        } else if (khoa.equals("k") && khoahoc.equals("c")) {
            chuoitruyvan = "WHERE keyid='" + khoahocsv + "' \n";
        } else if (khoahoc.equals("c") && nganh.equals("c")) {
            chuoitruyvan = "WHERE keyid='" + khoahocsv + "' AND branch=N'" + nganhhocsv + "' \n";
        } else if (khoahoc.equals("k") && nganh.equals("c")) {
            chuoitruyvan = "WHERE branch=N'" + nganhhocsv + "' \n";
        }
        int index = IndexSV(mssv, chuoitruyvan);
        ArrayList<InforStudentData> svtr_s = svtr_s(index, chuoitruyvan);
        Infor_ViThu_BangXepHang infor = new Infor_ViThu_BangXepHang();
        infor.setVithu(index);
        infor.setArr(svtr_s);
        Gson gs = new Gson();
        String json = gs.toJson(infor);
//        System.out.println("Inf:" + json);
        return json;
    }

    /**/
    public String getDiemSV(String mssv) {
        ConnectDB();
        AllPointId st = new AllPointId();
        ArrayList<AllPoint> apid = new ArrayList<>();
        String sql = "select *\n"
                + "from allpointStudents \n"
                + "where MaSV='" + mssv + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AllPoint ap = new AllPoint();
                ap.setMamon(rs.getString(2));
                ap.setTenmon(rs.getString(3));
                ap.setDiem4(rs.getString(4));
                ap.setDiem10(rs.getString(5));
                ap.setXeploai(rs.getString(6));
                apid.add(ap);
            }
            st.setId(SoMon(mssv)+"");
            st.setAllPoint(apid);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Gson gs = new Gson();
        String json = gs.toJson(st);
        System.out.println("Inf:" + json);
        return json;
    }

    public int SoMon(String masv) {
        ConnectDB();
        int somon = 0;
        String sql = "select count(DISTINCT MaMH) as somon\n"
                + "from allpointStudents\n"
                + "where MaSV='" + masv + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                somon = rs.getInt(1);
            }
            System.out.println(somon);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return somon;
    }

    public static void main(String[] args) {
        StudentDAL st = new StudentDAL();
//        st.getDsXepHang_ViThu("3118410419", "c","c", "c");
//        st.getDiemSV("3118410419");
        st.SoMon("3118410412");
    }
}
