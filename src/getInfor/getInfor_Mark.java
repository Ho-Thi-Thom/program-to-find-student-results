package getInfor;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AllPoint;
import model.AllPointId;
import model.InforStudentData;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author HO_THI_THOM
 */
public class getInfor_Mark {
    
    public static String getThongTin(String mssv) {
        InforStudentData st = new InforStudentData();
        AllPointId stu = new AllPointId();
        AllPoint allPoint;
        List<AllPoint> arrAllPoint;
        try {
            URL url = new URL("http://thongtindaotao.sgu.edu.vn/default.aspx?page=nhapmasv&flag=XemDiemThi");
            LogFactory.getFactory().setAttribute("org.apache.commons.loggin.Log",
                    "org.apache.commons.logging.impl.NoOpLog");
            
            WebClient webClient = new WebClient();
            // turn off warning html unit
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            HtmlPage page = webClient.getPage(url);
            //click button xemdiem
            HtmlSpan clickbtnXemDiem = page.getHtmlElementById("ctl00_menu_lblXemDiem");
            clickbtnXemDiem.click();
            //click ô input de nhap mssv
            HtmlTextInput inputMSSV = page.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_txtMaSV");
            inputMSSV.type(mssv);
            //click button OK
            HtmlSubmitInput submitButtonSearch = page.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_btnOK");
            HtmlPage ttSinhVien = submitButtonSearch.click();
//    private String studenID;
            st.setStudenID(mssv);
//    private String nameStudent;
            try {
                HtmlSpan nameStudent = ttSinhVien.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblTenSinhVien");
                st.setNameStudent(nameStudent.asText());
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
//    private String sex;
            try {
                HtmlSpan phai = ttSinhVien.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblPhai");
                st.setSex(phai.asText());
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
//   private String birthplace;
            try {
                HtmlSpan birthplace = ttSinhVien.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblNoiSinh");
                st.setBirthplace(birthplace.asText());
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
//    private String classs;
            try {
                HtmlSpan classs = ttSinhVien.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblLop");
                st.setClasss(classs.asText());
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
//    private String branch;
            try {
                HtmlSpan branch = ttSinhVien.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lbNganh");
                st.setBranch(branch.asText());
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
//    private String course;
            try {
                HtmlSpan course = ttSinhVien.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_ucThongTinSV_lblKhoaHoc");
                st.setCourse(course.asText());
            } catch (ElementNotFoundException e) {
                e.printStackTrace();
            }
//diem_chi
            DomNodeList<DomNode> listNameSubjectAndPoint = ttSinhVien.querySelectorAll(".row-diemTK .Label");
            int sizeList = listNameSubjectAndPoint.size();
            String credit = listNameSubjectAndPoint.get(sizeList - 1).asText();
            String mark10 = listNameSubjectAndPoint.get(sizeList - 7).asText();
            String mark4 = listNameSubjectAndPoint.get(sizeList - 5).asText();
            
            st.setMark10(mark10);
            st.setMark4(mark4);
            st.setCredit(credit);
            //
            
            DomNodeList<DomNode> listDanhSachDiem = ttSinhVien.querySelectorAll(".row-diem .Label ");
            
            int mamon = 1;
            int tenmon = 2;
            int sotc = 3;
            int diemhe4 = 12;
            int diem10 = 9;
            int loai = 11;
            
            int i = 0;
            arrAllPoint = new ArrayList<AllPoint>();
            while (i < listDanhSachDiem.getLength()) {
                allPoint = new AllPoint();
                allPoint.setMamon(listDanhSachDiem.get(i + mamon).asText());
                allPoint.setTenmon(listDanhSachDiem.get(i + tenmon).asText());
//                    System.out.println(listDanhSachDiem.get(i+sotc).asText());
                allPoint.setDiem4(listDanhSachDiem.get(i + diemhe4).asText());
                allPoint.setDiem10(listDanhSachDiem.get(i + diem10).asText());
                allPoint.setXeploai(listDanhSachDiem.get(i + loai).asText());
                arrAllPoint.add(allPoint);
                i += 14;
            }
            
            stu.setAllPoint(arrAllPoint);
            st.setAllpoint(stu);
// birthday
            st.setBirthday(getbirthday(mssv));
            st.setNsub(nsub(mssv));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (FailingHttpStatusCodeException ex) {
            ex.printStackTrace();
        }
        Gson gs = new Gson();
        String json = gs.toJson(st);
//        System.out.println("Inf:" + json);
        return json;
        
    }
    
    public static String getbirthday(String mssv) {
        String reString = "";
        try {
            
            final WebClient webClient = new WebClient();
            final URL url = new URL("http://thongtindaotao.sgu.edu.vn/Default.aspx?page=thoikhoabieu&sta=1&id=" + mssv);
            // turn off warning html unit
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            final HtmlPage page = webClient.getPage(url);
            //Get birthday
            try {
                HtmlSpan birthday = page.getHtmlElementById("ctl00_ContentPlaceHolder1_ctl00_lblContentTenSV");
                reString = birthday.asText().substring(birthday.asText().length() - 10, birthday.asText().length()).trim();
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
        return reString;
    }
    
    public static String nsub(String id) {
        String num = "";
        try {
            final WebClient webClient = new WebClient();
            final URL url = new URL("http://thongtindaotao.sgu.edu.vn/default.aspx?page=nhapmasv&flag=XemDiemThi");
            // turn off warning html unit
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.setCssErrorHandler(new SilentCssErrorHandler());
            
            final HtmlPage page = webClient.getPage(url);
            
            HtmlInput searchMSSV = (HtmlInput) page.getElementById("ctl00_ContentPlaceHolder1_ctl00_txtMaSV");
            searchMSSV.type(id);
            
            HtmlInput submitDataMssv = (HtmlInput) page.getElementById("ctl00_ContentPlaceHolder1_ctl00_btnOK");
            HtmlPage inforStudent = submitDataMssv.click();
            ArrayList<String> arr = new ArrayList<>();
            DomNodeList<DomNode> listDanhSachDiem = inforStudent.querySelectorAll(".row-diem");
            for (int i = 0; i < listDanhSachDiem.getLength(); i++) {
                arr.add(i, listDanhSachDiem.get(i).asText());
            }
            ArrayList<String> brr = new ArrayList<>();
            for (String a : arr) {
                brr.add(Format(a));
            }
            Map<String, Integer> map = new HashMap<>();
//
            for (String r : brr) {
                if (map.containsKey(r)) {
                    map.put(r, map.get(r) + 1);
                } else {
                    map.put(r, 1);
                }
            }
            num = String.valueOf(map.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return num;
    }
    
    public static String Format(String input) {
        String hp = "";
        String s = input.trim().replaceAll("\\s\\s+", " ");
        String[] words = s.split("\\s");
        hp = words[1];
        return hp;
    }
    
    public static void main(String[] args) {
        //        InforStudentData st = getThongTin("3118410419");
        //        System.out.println("Họ tên :" + st.getNameStudent());
        //        System.out.println("Phái :" + st.getSex());
        //        System.out.println("Nơi sinh :" + st.getBirthplace());
        //        System.out.println("Ngày sinh :" + st.getBirthday());
        //        System.out.println("Ngành học :" + st.getBranch());
        //        System.out.println("Lớp :" + st.getClasss());
        //        System.out.println("Khóa học :" + st.getCourse());
        //        System.out.println("Diểm hệ 10:" + st.getMark10());
        //        System.out.println("Điểm hệ 4 :" + st.getMark4());
        //        System.out.println("Số tín chỉ:" + st.getCredit());
        //        System.out.println("Số môn đã học:" + st.getNsub());
        //        nsub("3118410412");
        getInfor_Mark a = new getInfor_Mark();
        a.getThongTin("3118410419");
    }
}
