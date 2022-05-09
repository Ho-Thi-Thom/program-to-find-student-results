/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dal.StudentDAL;
import getInfor.CheckSV;
import getInfor.getCredit_AllSub;
import getInfor.getInfor_Mark;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Event {

    public static String Query(String input) throws SQLException {
        String result = null;
        String[] temp = input.split(";");
        if (CheckSV.getThongTin((temp[0]))) {
            if (temp[1].equals("tracuuttsinhvien")) {
                result = getInfor_Mark.getThongTin(temp[0]);
            } else if (temp[1].equals("tracuutiendohoctap")) {
                result = getCredit_AllSub.getAllSub(temp[0]);
            } else if(temp[1].equals("tracuuxephang")){
                StudentDAL sDAL= new StudentDAL();
                result = sDAL.getDsXepHang_ViThu(temp[0],temp[2], temp[3], temp[4]);             
            }else if(temp[1].equals("tracuudiemchitiet")){
                StudentDAL sDAL= new StudentDAL();
                result=sDAL.getDiemSV(temp[0]);
            }
        } else {
            result = "failed";
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Query("3118410419;tracuuxephang;c;c;c"));
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
