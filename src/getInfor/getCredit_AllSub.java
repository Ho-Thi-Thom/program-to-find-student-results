package getInfor;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.AllSub;
import model.AllSub_Credit;

/**
 *
 * @author HO_THI_THOM
 */
public class getCredit_AllSub {

    public static String getAllSub(String id) {
        AllSub_Credit stu = new AllSub_Credit();
        ArrayList<AllSub> arrAllPoint;
        try {
            final WebClient webClient = new WebClient();
            final URL url = new URL("http://thongtindaotao.sgu.edu.vn/default.aspx?page=nhapmasv&flag=XemDiemThi");
            // turn off warning html unit
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.setCssErrorHandler(new SilentCssErrorHandler());

            try {
                final HtmlPage page = webClient.getPage(url);

                HtmlInput searchMSSV = (HtmlInput) page.getElementById("ctl00_ContentPlaceHolder1_ctl00_txtMaSV");
                searchMSSV.type(id);
                HtmlInput submitDataMssv = (HtmlInput) page.getElementById("ctl00_ContentPlaceHolder1_ctl00_btnOK");
                HtmlPage inforStudent = submitDataMssv.click();

                DomNodeList<DomNode> listDanhSachDiem = inforStudent.querySelectorAll(".row-diem .Label ");

                int mamon = 1;
                int i = 0;

                ArrayList<String> st = new ArrayList<>();
                while (i < listDanhSachDiem.getLength()) {
                    st.add(listDanhSachDiem.get(i + mamon).asText());
                    i += 14;
                }
                arrAllPoint = new ArrayList<AllSub>();
                Map<String, Integer> map = new HashMap<>();
                int j = 0;
                for (String item : st) {
                    map.put(item, j++);
                }
                ArrayList<AllSub> as = readDSMonHocIT();
                for (AllSub r : as) {
                    if (!map.containsKey(r.getMamonhoc())) {
                        arrAllPoint.add(r);
                    }
                }
                stu.setAp((ArrayList<AllSub>) arrAllPoint);
                DomNodeList<DomNode> listNameSubjectAndPoint = inforStudent.querySelectorAll(".row-diemTK .Label");
                int sizeList = listNameSubjectAndPoint.size();
                String credit = listNameSubjectAndPoint.get(sizeList - 1).asText();
                stu.setCredit(Integer.parseInt(credit));
            } catch (IOException ex) {
                Logger.getLogger(getAllPointST.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FailingHttpStatusCodeException ex) {
                Logger.getLogger(getAllPointST.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getAllPointST.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gs = new Gson();
        String json = gs.toJson(stu);
        System.out.println("Inf:" + json);
        return json;
    }

    public static ArrayList<AllSub> readDSMonHocIT() {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        ArrayList<AllSub> ar = new ArrayList<>();
        try {
            File file = new File("infor_MonHoc.json");
            inputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            try {
                Gson gson = new Gson();
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    try {
                        AllSub stu = gson.fromJson(line, AllSub.class);
                        ar.add(i++, stu);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                System.out.println("error");
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return ar;
    }

    public static void main(String[] args) {
//        AllSub_Credit ac = getAllSub("3118410419");
//        int sr = ac.getCredit();
//        ArrayList<AllSub> ab = ac.getAp();
//        for (AllSub item : ab) {
//            System.out.println("-" + item.getMamonhoc() + " " + item.getTenmonhoc() + " " + item.getSotinchi());
//        }
//
    }
}
