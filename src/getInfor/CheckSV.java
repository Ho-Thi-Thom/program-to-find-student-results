package getInfor;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;
import java.net.URL;
import jdk.nashorn.api.tree.BreakTree;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author HO_THI_THOM
 */
public class CheckSV {

    public static boolean getThongTin(String mssv) {
        try {
           URL url = new URL("http://thongtindaotao.sgu.edu.vn/default.aspx?page=nhapmasv&flag=XemDiemThi");
            LogFactory.getFactory().setAttribute("org.apache.commons.loggin.Log",
                    "org.apache.commons.logging.impl.NoOpLog");

            WebClient webClient = new WebClient();
            // turn off warning html unit
            java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
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
            if(ttSinhVien.asXml().length()<30000)return false;
//            return true;
        } catch (Exception ex) {
               return false;
        }
        return true;
    }
    public static void main(String[] args) {
    if(getThongTin("3118410415")){
        System.out.println("tim thay");
    }else{
        System.out.println("ko");
    }
    }
    
}
