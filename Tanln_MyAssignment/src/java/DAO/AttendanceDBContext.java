/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;
import model.Group;
import model.Lecturer;
import model.Session;
import model.Student;

/**
 *
 * @author apc
 */
public class AttendanceDBContext extends DBContext<Attendance> {

    public ArrayList<Attendance> listBySessionID(int seid) {
        ArrayList<Attendance> attendences = new ArrayList<>();
        try {
            String sql = "select a.SessionID,a.Status, a.Comment,a.EditDate,b.SID,b.SCode,b.SName,b.Img,c.LID,c.LName, e.Gname from Attendance a\n"
                    + "join Student b on a.SID = b.SID\n"
                    + "join Lecturer c on a.LID = c.LID\n"
                    + "join Session d on a.SessionID = d.SessionID\n"
                    + "join [Group] e on d.GID = e.GID\n"
                    + "where a.SessionID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, seid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                Session se = new Session();
                se.setSeid(seid);
                Group g = new Group();
                g.setGname(rs.getString("GName"));
                se.setGroup(g);
                a.setSession(se);
                Lecturer le = new Lecturer();
                le.setLid(rs.getInt("LID"));
                le.setLname(rs.getString("LName"));
                a.setLecturer(le);
                Student s = new Student();
                s.setSid(rs.getInt("SID"));
                s.setSname(rs.getString("SName"));
                s.setScode(rs.getString("SCode"));
                s.setImg(rs.getString("Img"));
                a.setStudent(s);
                a.setStatus(rs.getString("Status"));
                a.setComment(rs.getString("Comment"));
                a.setEditDate(rs.getTimestamp("EditDate"));
                attendences.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attendences;
    }

    public void insertList(ArrayList<Attendance> attendences) {
        try {
            connection.setAutoCommit(false);

            for (Attendance a : attendences) {
                String sql = "INSERT INTO [Attendance]\n"
                        + "           ([SID]\n"
                        + "           ,[SessionID]\n"
                        + "           ,[Status]\n"
                        + "           ,[Comment]\n"
                        + "           ,[EditDate]\n"
                        + "           ,[LID])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(1, a.getStudent().getSid());
                stm.setInt(2, a.getSession().getSeid());
                stm.setString(3, a.getStatus());
                stm.setString(4, a.getComment());
                stm.setTimestamp(5, a.getEditDate());
                stm.setInt(6, a.getLecturer().getLid());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void UpdateList(ArrayList<Attendance> attendences) {
        try {
            connection.setAutoCommit(false);

            for (Attendance a : attendences) {
                String sql = "UPDATE [Attendance]\n"
                        + "   SET [Status] = ?\n"
                        + "      ,[Comment] = ?\n"
                        + "      ,[EditDate] = ?\n"
                        + "      ,[LID] = ?\n"
                        + " WHERE [SID] = ? and SessionID = ? ";
                PreparedStatement stm = connection.prepareStatement(sql);
                stm.setInt(5, a.getStudent().getSid());
                stm.setInt(6, a.getSession().getSeid());
                stm.setString(1, a.getStatus());
                stm.setString(2, a.getComment());
                stm.setTimestamp(3, a.getEditDate());
                stm.setInt(4, a.getLecturer().getLid());
                stm.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Attendance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
