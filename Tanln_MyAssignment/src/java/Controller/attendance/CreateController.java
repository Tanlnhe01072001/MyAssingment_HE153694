/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.attendance;

import DAO.AttendanceDBContext;
import DAO.GroupDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.Attendance;
import model.Group;
import model.Lecturer;
import model.Session;
import model.Student;

/**
 *
 * @author apc
 */
public class CreateController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int gid = Integer.parseInt(request.getParameter("gid"));
        int seid = Integer.parseInt(request.getParameter("seid"));
        GroupDBContext groupDB = new GroupDBContext();
        Group g = groupDB.get(gid);
        request.setAttribute("seid", seid);
        request.setAttribute("group", g);
        request.setAttribute("students", g.getStudents());
        request.getRequestDispatcher("../view/attendance/create.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String[] indexs = request.getParameterValues("index");
        String raw_seid = request.getParameter("seid");
        Session se = new Session();
        se.setSeid(Integer.parseInt(raw_seid));
        Lecturer l = new Lecturer();
        l.setLid(1);
        for (String index : indexs) {
            Attendance a = new Attendance();
            Student s = new Student();
            a.setSession(se);
            s.setSid(Integer.parseInt(request.getParameter("id"+index)));
            a.setStudent(s);
            a.setStatus(request.getParameter("status"+index).equals("attend")?"attended":"absented");
            a.setComment(request.getParameter("comment"+index));
            a.setEditDate(Timestamp.valueOf(LocalDateTime.now()));
            a.setLecturer(l);
            se.getAttendances().add(a);
        }
        AttendanceDBContext attendDB = new AttendanceDBContext();
        attendDB.insertList(se.getAttendances());
        response.sendRedirect("list?seid="+se.getSeid());
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
