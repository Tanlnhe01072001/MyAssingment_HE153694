/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.schedule;

import DAO.SessionDBContext;
import DAO.SlotDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;
import model.Session;

/**
 *
 * @author apc
 */
public class ScheduleController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    SlotDBContext slotDB = new SlotDBContext();
    SessionDBContext seDB = new SessionDBContext();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String raw_year = request.getParameter("year");
        String raw_week = request.getParameter("week");
        String raw_selectedYear = request.getParameter("selectedYear");
        LocalDate ld = LocalDate.now();
        Calendar calendar = Calendar.getInstance();
        int currentYear = ld.getYear();
        int currentWeek = ld.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        int week = (raw_week != null && raw_week.length() > 0) ? Integer.parseInt(raw_week)+1 : currentWeek;
        int year = (raw_year != null && raw_year.length() > 0) ? Integer.parseInt(raw_year) : currentYear;
        int selectedYear = (raw_year != null && raw_year.length() > 0)? Integer.parseInt(raw_selectedYear):currentYear;
        week = (selectedYear == year)?week:1;
        week = (selectedYear != year && year == currentYear)?currentWeek:week;
        ArrayList<Date> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            calendar.clear();
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.YEAR, year);
            calendar.add(Calendar.DATE, i);
            Date day = new java.sql.Date(calendar.getTimeInMillis());
            days.add(day);
        }
        int weeksOfYear = Calendar.getInstance().getActualMaximum(Calendar.WEEK_OF_YEAR);
        ArrayList<String> weeks = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM");
        for (int i = 0; i < weeksOfYear; i++) {
            calendar.clear();
            calendar.set(Calendar.WEEK_OF_YEAR, i + 1);
            calendar.set(Calendar.YEAR, year);
            String w = df.format(calendar.getTime()) + " to ";
            calendar.add(Calendar.DATE, 6);
            w += df.format(calendar.getTime());
            weeks.add(w);
        }
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.YEAR, currentYear);
        Date start = new java.sql.Date(calendar.getTimeInMillis());
        calendar.add(Calendar.DATE, 6);
        Date end = new java.sql.Date(calendar.getTimeInMillis());
        request.setAttribute("sessions", seDB.weeklySessions(start, end, 1));
        request.setAttribute("weeks", weeks);
        request.setAttribute("week", week-1);
        request.setAttribute("year", year);
        request.setAttribute("currentYear", currentYear);
        request.setAttribute("days", days);
        request.setAttribute("slots", slotDB.list());
        request.getRequestDispatcher("view/WeeklySchedule.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
