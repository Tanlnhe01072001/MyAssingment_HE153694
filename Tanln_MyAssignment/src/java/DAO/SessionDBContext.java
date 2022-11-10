/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;
import model.Group;
import model.Lecturer;
import model.Room;
import model.Session;
import model.Slot;
import model.Subject;

/**
 *
 * @author apc
 */
public class SessionDBContext extends DBContext<Session> {

    public ArrayList<Session> weeklySessions(Date start, Date end, int lid) {
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "select a.SessionID, a.SessionName, a.SessionDetail, a.DateApply, d.GID, d.Gname, e.LName, a.SlotID, c.RName,f.id as suid, f.code from Session a \n"
                    + "join Slot b on a.SlotID = b.id\n"
                    + "join Room c on a.RoomID = c.RID\n"
                    + "join [Group] d on a.GID = d.GID\n"
                    + "join Lecturer e on d.LecturerID = e.LID\n"
                    + "join Subject f on d.SubjectID = f.id\n"
                    + "where a.DateApply >= ? and a.DateApply <= ? and lid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setDate(1, start);
            stm.setDate(2, end);
            stm.setInt(3, lid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Session se = new Session();
                se.setSeid(rs.getInt("SessionID"));
                se.setSename(rs.getString("SessionName"));
                se.setSedetail(rs.getString("SessionDetail"));
                Group group = new Group();
                group.setGid(rs.getInt("GID"));
                group.setGname(rs.getString("Gname"));
                Lecturer lecturer = new Lecturer();
                lecturer.setLid(lid);
                lecturer.setLname(rs.getString("LName"));
                group.setLecturer(lecturer);
                Subject subject = new Subject();
                subject.setSuid(rs.getInt("suid"));
                subject.setSucode(rs.getString("code"));
                group.setSubject(subject);
                se.setGroup(group);
                Slot slot = new Slot();
                slot.setSlid(rs.getInt("SlotID"));
                se.setSlot(slot);
                Room room = new Room();
                room.setRname(rs.getString("RName"));
                se.setRoom(room);
                AttendanceDBContext attendDB = new AttendanceDBContext();
                se.setAttendances(attendDB.listBySessionID(se.getSeid()));
                se.setApplyDate(rs.getDate("DateApply"));
                sessions.add(se);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessions;
    }

    public ArrayList<Session> listByGroup(int gid) {
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "SELECT [SessionID]\n"
                    + "      ,[SessionName]\n"
                    + "      ,[SessionDetail]\n"
                    + "      ,[GID]\n"
                    + "  FROM [Session]\n"
                    + "  WHERE [GID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Session se = new Session();
                se.setSeid(rs.getInt("SessionID"));
                se.setSename(rs.getString("SessionName"));
                se.setSedetail(rs.getString("SessionDetail"));
                AttendanceDBContext attendDB = new AttendanceDBContext();
                se.setAttendances(attendDB.listBySessionID(se.getSeid()));
                sessions.add(se);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessions;
    }

    @Override
    public ArrayList<Session> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Session get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
