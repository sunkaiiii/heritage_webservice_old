package Handle;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Sender;
import org.apache.axis.encoding.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunkai on 2017/3/15.
 */
public class HandleUser {
    Connection con;
    //驱动程序名
    String driver = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名mydata
    String url = "jdbc:mysql://localhost:3306/heritage?useUnicode=true&characterEncoding=utf8";
    //MySQL配置时的用户名
    String user = "root";
    //MySQL配置时的密码
    String password = "";
    String success = "Success";
    String error = "Error";
    String sql;
    PreparedStatement pst;

    private Statement connectSQL() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
//                  System.out.println("Succeeded connecting to the Database");
            }
            return con.createStatement();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }
        return null;
    }

    private void closeSQL() {
        try {
            if (con!=null&&!con.isClosed()) {
                con.close();
            }
            if (!pst.isClosed()) {
                pst.close();
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }
    }

    public String User_Regist(String userName, String passWord, String findPasswordQuestion, String findPassWordAnswer) {
        try {
            Statement statement = connectSQL();
            if (null == statement) {
                return null;
            }
            sql = "SELECT * from USER_INFO where USER_NAME=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet rs;
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("已有该用户:" + userName);
                return "hadUser";
            }
            sql = "insert into USER_INFO(USER_NAME,USER_PASSWORD,USER_PASSWORD_QUESTION,USER_PASSWORD_ANSWER) " +
                    "VALUES (?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, passWord);
            pst.setString(3, findPasswordQuestion);
            pst.setString(4, findPassWordAnswer);
            if (pst.executeUpdate() == 1) {
                System.out.println("添加用户:" + userName + ",密码:" + passWord);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Sign_In(String userName, String PassWord) {
        try {
            Statement statement = connectSQL();
            if (null == statement) {
                return null;
            }
            sql = "select * from user_info where USER_NAME=? and USER_PASSWORD=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, PassWord);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                System.out.println("用户:" + userName + "登陆成功");
                return success;
            } else {
                System.out.println("登陆失败");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public int Get_User_ID(String userName) {
        try {
            System.out.println("用户" + userName + " 查询ID");
            Statement statement = connectSQL();
            if (null == statement) {
                return -1;
            }
            sql = "select * from user_info where USER_NAME=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                return result.getInt("id");
            } else {
                System.out.println("登陆失败");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return -1;
    }

    public String Find_Password_Question(String userName) {
        try {
            Statement statement = connectSQL();
            if (null == statement) {
                return null;
            }
            sql = "SELECT USER_PASSWORD_QUESTION from user_info where USER_NAME=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                String question;
                question = result.getString("USER_PASSWORD_QUESTION");
                return question;
            } else {
                return "noUser";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Check_Question_Answer(String userName, String questionAnswer) {
        try {
            Statement statement = connectSQL();
            sql = "select * from user_info where USER_NAME=? and USER_PASSWORD_ANSWER=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, userName);
            pst.setString(2, questionAnswer);
            ResultSet result = pst.executeQuery();
            if (result.next()) {
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Change_Password(String userName, String Password) {
        try {
            Statement statement = connectSQL();
            if (null == statement) {
                return null;
            }
            sql = "update user_info set USER_PASSWORD=? where USER_NAME=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, Password);
            pst.setString(2, userName);
            if (pst.executeUpdate() == 1) {
                System.out.println(userName + "修改密码");
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Add_Main_Activity(String activityTitle, String activityContent, String activityImage) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into main_activity(activity_title,activity_content,activity_image) values (?,?,?)";
        byte[] imgData = Base64.decode(activityImage);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, activityTitle);
            pst.setString(2, activityContent);
            pst.setBinaryStream(3, in, in.available());
            if (pst.executeUpdate() == 1) {
                System.out.println("成功添加活动:" + activityTitle);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Update_Main_Activity(String activityImage, int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "update main_activity set activity_image=? where id=?";
        byte[] imgData = Base64.decode(activityImage);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setBinaryStream(1, in, in.available());
            pst.setInt(2, id);
            if (pst.executeUpdate() == 1) {
                System.out.println("成功成功\n");
                return success;
            } else {
                System.out.println("更新失败\n");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Update_classify_activity(String activityImage, int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "update classify_activity set activity_image=? where id=?";
        byte[] imgData = Base64.decode(activityImage);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setBinaryStream(1, in, in.available());
            pst.setInt(2, id);
            if (pst.executeUpdate() == 1) {
                System.out.println("成功成功\n");
                return success;
            } else {
                System.out.println("更新失败\n");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Read_Main_Activity() {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select * from main_activity order by id desc LIMIT 0,4";
        try {
            pst = con.prepareStatement(sql);
            ResultSet result = pst.executeQuery();
            int count = 0;
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (result.next() && count < 4) {
                System.out.println(result.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", result.getString("id"));
                member.put("activity_title", result.getString("activity_title"));
                member.put("activity_content", result.getString("activity_content"));
                InputStream in = result.getBinaryStream("activity_image");
                byte[] imgByte = new byte[in.available()];
                in.read(imgByte);
                String imgCode = new String(Base64.encode(imgByte));
                member.put("activity_image", imgCode);
                jsonMembers.put(member);
                count++;
            }
            json.put("main_Activity", jsonMembers);
//          System.out.println(json.toString());
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Add_Classify_Activity_Information(String activity_title, String activity_content, String activity_channel, String activity_image) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into classify_activity(activity_title,activity_content,activity_channel,activity_image) values (?,?,?,?)";
        byte[] imgData = Base64.decode(activity_image);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, activity_title);
            pst.setString(2, activity_content);
            pst.setString(3, activity_channel);
            pst.setBinaryStream(4, in, in.available());
            if (pst.executeUpdate() == 1) {
                System.out.println("成功添加活动:" + activity_title);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Activity_Count(String channel) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT COUNT(*) from classify_activity where activity_channel=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, channel);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                String count = resultSet.getString(1);
                System.out.println(channel + "一共有:" + count);
                return count;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Channel_Information(String channel) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT id,activity_title,activity_content,activity_channel from classify_activity where activity_channel=? order by id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, channel);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getString("id"));
                member.put("activity_title", resultSet.getString("activity_title"));
                member.put("activity_content", resultSet.getString("activity_content"));
                member.put("activity_channel", resultSet.getString("activity_channel"));
                jsonMembers.put(member);
            }
            json.put("classify_activity", jsonMembers);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Channel_Image(int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT activity_image from classify_activity where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            String imgCode = null;
            if (resultSet.next()) {
                InputStream in = resultSet.getBinaryStream("activity_image");
                if (null != in) {
                    byte[] imgByte = new byte[in.available()];
                    in.read(imgByte);
                    imgCode = Base64.encode(imgByte);
                    return imgCode;
                } else {
                    return error;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Add_Folk_Activity(String title, String content, String location, String image) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into folk_Activity(title,content,location,image) values(?,?,?,?)";
        byte[] imgData = Base64.decode(image);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, content);
            pst.setString(3, location);
            pst.setBinaryStream(4, in, in.available());
            if (pst.executeUpdate() == 1) {
                System.out.println("成功添加活动:" + title);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Folk_Count() {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT COUNT(*) from folk_activity";
        try {
            pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                String count = resultSet.getString(1);
                System.out.println("folk有" + count + "个活动");
                return count;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Folk_Information() {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT * from folk_activity  order by id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getString("id"));
                member.put("title", resultSet.getString("title"));
                member.put("content", resultSet.getString("content"));
                member.put("location", resultSet.getString("location"));
                member.put("divide", resultSet.getString("divide"));
                member.put("teacher", resultSet.getString("teacher"));
                member.put("tech-time", resultSet.getString("tech-time"));
                jsonMembers.put(member);
            }
            json.put("folk_information", jsonMembers);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Folk_Single_Information(int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT * from folk_activity  where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getString("id"));
                member.put("title", resultSet.getString("title"));
                member.put("content", resultSet.getString("content"));
                member.put("location", resultSet.getString("location"));
                return member.toString();
            }
            return "";
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Folk_Image(int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT image from folk_activity where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            String imgCode;
            if (resultSet.next()) {
                InputStream in = resultSet.getBinaryStream("image");
                if (null != in) {
                    byte[] imgByte = new byte[in.available()];
                    in.read(imgByte);
                    imgCode = Base64.encode(imgByte);
                    return imgCode;
                } else {
                    return error;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Update_folk_activity_Image(String activityImage, int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "update folk_activity set image=? where id=?";
        byte[] imgData = Base64.decode(activityImage);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setBinaryStream(1, in, in.available());
            pst.setInt(2, id);
            if (pst.executeUpdate() == 1) {
                System.out.println("成功成功\n");
                return success;
            } else {
                System.out.println("更新失败\n");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Search_Folk_Info(String searhInfo) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select * from folk_activity where title like ? or content like ? or location like ? or divide like ? or teacher like ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, '%' + searhInfo + '%');
            pst.setString(2, '%' + searhInfo + '%');
            pst.setString(3, '%' + searhInfo + '%');
            pst.setString(4, '%' + searhInfo + '%');
            pst.setString(5, '%' + searhInfo + '%');
            ResultSet rs = pst.executeQuery();
            JSONObject jsonObject = new JSONObject();
            JSONArray datas = new JSONArray();
            while (rs.next()) {
                JSONObject data = new JSONObject();
                data.put("id", rs.getString("id"));
                data.put("title", rs.getString("title"));
                data.put("content", rs.getString("content"));
                data.put("location", rs.getString("location"));
                data.put("divide", rs.getString("divide"));
                data.put("teacher", rs.getString("teacher"));
                data.put("tech-time", rs.getString("tech-time"));
                datas.put(data);
            }
            jsonObject.put("folk_information", datas);
            System.out.println("民间查询到的结果:" + jsonObject);
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;

    }

    public String Add_User_Order(int userID, int orderID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into user_order(userID,orderID) values(?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, orderID);
            if (pst.executeUpdate() == 1) {
                System.out.println("预约成功");
                return success;
            }
            return error;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Cancel_User_Order(int userID, int orderID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return error;
        }
        sql = "delete from user_order where userID=? and orderID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, orderID);
            if (pst.executeUpdate() >= 1) {
                System.out.println("取消成功");
                return success;
            }
            return error;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Order(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return error;
        }
        sql = "select * from user_order where userID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject info = new JSONObject();
                info.put("id", resultSet.getInt("id"));
                info.put("userID", resultSet.getInt("userID"));
                info.put("orderID", resultSet.getInt("orderID"));
                jsonArray.put(info);
            }
            json.put("UserOrderInfo", jsonArray);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public int Check_Is_Order(int userID, int orderID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return -1;
        }
        sql = "select * from user_order where userID=? and orderID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, orderID);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                System.out.println("已有" + userID + "," + orderID + "的活动");
                return 1;
            }
            System.out.println("没有" + userID + "," + orderID + "的活动");
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return -1;
    }

    public String Add_Find_Activity(String title, String content, String image) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "INSERT INTO find_activity(title,content,image) VALUES (?,?,?)";
        byte[] imgData = Base64.decode(image);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, title);
            pst.setString(2, content);
            pst.setBinaryStream(3, in);
            if (pst.executeUpdate() == 1) {
                System.out.println("添加成功\n");
                return success;
            } else {
                System.out.println("更新失败\n");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Find_Activity_ID() {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select id from find_activity order by id desc LIMIT 0,4";
        try {
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonIDs = new JSONArray();
            while (rs.next()) {
                jsonIDs.put(rs.getInt("id"));
            }
            json.put("id", jsonIDs);
            System.out.println("find活动的ID是" + json.toString());
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_Find_Activity_Information(int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select * from find_activity where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            JSONObject json = new JSONObject();
            if (rs.next()) {
                json.put("title", rs.getString("title"));
                json.put("content", rs.getString("content"));
                InputStream in = rs.getBinaryStream("image");
                byte[] imgByte = new byte[in.available()];
                in.read(imgByte);
                String imgCode = new String(Base64.encode(imgByte));
                json.put("image", imgCode);
            }
            System.out.println("find活动的内容是" + json.toString());
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Update_Find_Activity_Image(String imageCode, int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "update find_activity set image=? where id=?";
        byte[] imgData = Base64.decode(imageCode);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setBinaryStream(1, in, in.available());
            pst.setInt(2, id);
            if (pst.executeUpdate() == 1) {
                System.out.println("更新成功\n");
                return success;
            } else {
                System.out.println("更新失败\n");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Add_User_Comment_Information(int user_id, String comment_title, String comment_content, String comment_image) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into user_comment(user_id,comment_time,comment_title,comment_content,comment_image) VALUES (?,?,?,?,?)";
        byte[] imgData = Base64.decode(comment_image);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, user_id);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            pst.setString(2, time);
            pst.setString(3, comment_title);
            pst.setString(4, comment_content);
            pst.setBinaryStream(5, in);
            if (pst.executeUpdate() == 1) {
                System.out.println("用户：" + user_id + "添加了comment" + comment_title);
                return success;
            } else {
                System.out.println("用户：" + user_id + "添加了comment失败");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Delete_User_Comment_By_ID(final int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return "error";
        }
        sql = "delete from user_comment where id=?";

        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentID);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String sql2 = "delete from user_comment_reply where comment_ID=?";
                        pst = con.prepareStatement(sql2);
                        pst.setInt(1, commentID);
                        pst.executeUpdate();
                        sql2 = "delete from user_comment_like where commentID=?";
                        pst = con.prepareStatement(sql2);
                        pst.setInt(1, commentID);
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            if (pst.executeUpdate() >= 1) {
                System.out.println("用户删除" + commentID);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ;
        }
        return error;
    }

    public String Update_User_Comment_Image(String img, int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "update user_comment set comment_image=? where id=?";
        byte[] imgData = Base64.decode(img);
        InputStream in = new ByteArrayInputStream(imgData);
        try {
            pst = con.prepareStatement(sql);
            pst.setBinaryStream(1, in, in.available());
            pst.setInt(2, id);
            if (pst.executeUpdate() == 1) {
                System.out.println("更新成功\n");
                return success;
            } else {
                System.out.println("更新失败\n");
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }
    public String Update_User_Comment_Informaiton(final int commentID,final String comment_title, final String comment_content, final String comment_image){
        Statement statement=connectSQL();
        if(null==statement){
            return error;
        }
        sql="update user_comment set comment_title=?,comment_content=?,comment_image=? where id=?";
        byte[] imgData=Base64.decode(comment_image);
        InputStream in=new ByteArrayInputStream(imgData);
        try{
            pst=con.prepareStatement(sql);
            pst.setString(1,comment_title);
            pst.setString(2,comment_content);
            pst.setBinaryStream(3,in);
            pst.setInt(4,commentID);
            if(pst.executeUpdate()>=1){
                System.out.println("更新帖子成功");
                return success;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("更新帖子失败");
        return error;
    }

    public String Get_User_Comment_ID() {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT id from user_comment ORDER BY id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
                jsonMembers.put(resultSet.getString(1));
            }
            json.put("members", jsonMembers);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Comment_ID_By_User(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT id from user_comment where user_id=? ORDER BY id DESC";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
                jsonMembers.put(resultSet.getString(1));
            }
            json.put("members", jsonMembers);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    //1.x老版本接口
    public String Get_User_Comment_Information() {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT  user_comment.id,user_id,user_name,comment_time,comment_title,comment_content,comment_num " +
                "from user_comment,user_info where user_id=user_info.ID order by id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getInt("id"));
                member.put("comment_time", resultSet.getString("comment_time"));
                member.put("user_name", resultSet.getString("user_name"));
                member.put("comment_title", resultSet.getString("comment_title"));
                member.put("comment_content", resultSet.getString("comment_content"));
                member.put("user_id", resultSet.getInt("user_id"));
                sql = "SELECT COUNT(*) from user_comment_like where commentID=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet commentNum = pst.executeQuery();
                if (commentNum.next()) {
                    int num = commentNum.getInt(1);
                    member.put("comment_num", String.valueOf(num));
                } else {
                    member.put("comment_num", String.valueOf(0));
                }
//              member.put("comment_num",resultSet.getString("comment_num"));
                sql = "SELECT COUNT(*) from user_comment_reply where comment_ID=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet replyNum = pst.executeQuery();
                if (replyNum.next()) {
                    int num = replyNum.getInt(1);
                    member.put("reply_num", String.valueOf(num));
                } else {
                    member.put("reply_num", String.valueOf(0));
                }
                jsonMembers.put(member);
            }
            json.put("user_comment_information", jsonMembers);
            System.out.println("查询发现内容:" + json);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_User_Comment_Information(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT  user_comment.id,user_id,user_name,comment_time,comment_title,comment_content,comment_num " +
                "from user_comment,user_info where user_id=user_info.ID order by id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getInt("id"));
                member.put("comment_time", resultSet.getString("comment_time"));
                member.put("user_name", resultSet.getString("user_name"));
                member.put("comment_title", resultSet.getString("comment_title"));
                member.put("comment_content", resultSet.getString("comment_content"));
                member.put("user_id", resultSet.getInt("user_id"));
                member.put("isFollow", is_User_Follow(userID, resultSet.getInt("user_id")));
                member.put("isLike", Get_User_Is_Like(userID, resultSet.getInt("id")));
                sql = "SELECT COUNT(*) from user_comment_like where commentID=?";
                connectSQL();
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet commentNum = pst.executeQuery();
                if (commentNum.next()) {
                    int num = commentNum.getInt(1);
                    member.put("comment_num", String.valueOf(num));
                } else {
                    member.put("comment_num", String.valueOf(0));
                }
//              member.put("comment_num",resultSet.getString("comment_num"));
                sql = "SELECT COUNT(*) from user_comment_reply where comment_ID=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet replyNum = pst.executeQuery();
                if (replyNum.next()) {
                    int num = replyNum.getInt(1);
                    member.put("reply_num", String.valueOf(num));
                } else {
                    member.put("reply_num", String.valueOf(0));
                }
                jsonMembers.put(member);
            }
            json.put("user_comment_information", jsonMembers);
            System.out.println("查询发现内容:" + json);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return null;
    }

    public String Get_User_Comment_Information_By_User(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT user_comment.id,user_id,user_name,comment_time,comment_title,comment_content,comment_num " +
                "from user_comment,user_info where user_id in (select focus_fansID from my_focus where focus_userID=?) and user_id=user_info.ID order by id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getInt("id"));
                member.put("comment_time", resultSet.getString("comment_time"));
                member.put("user_name", resultSet.getString("user_name"));
                member.put("comment_title", resultSet.getString("comment_title"));
                member.put("comment_content", resultSet.getString("comment_content"));
                member.put("user_id", resultSet.getInt("user_id"));
                member.put("isLike", Get_User_Is_Like(userID, resultSet.getInt("id")));
                member.put("isFollow", is_User_Follow(userID, resultSet.getInt("user_id")));
                sql = "SELECT COUNT(*) from user_comment_like where commentID=?";
                connectSQL();
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet commentNum = pst.executeQuery();
                if (commentNum.next()) {
                    int num = commentNum.getInt(1);
                    member.put("comment_num", String.valueOf(num));
                } else {
                    member.put("comment_num", String.valueOf(0));
                }
//              member.put("comment_num",resultSet.getString("comment_num"));
                sql = "SELECT COUNT(*) from user_comment_reply where comment_ID=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet replyNum = pst.executeQuery();
                if (replyNum.next()) {
                    int num = replyNum.getInt(1);
                    member.put("reply_num", String.valueOf(num));
                } else {
                    member.put("reply_num", String.valueOf(0));
                }
                jsonMembers.put(member);
            }
            json.put("user_comment_information", jsonMembers);
            System.out.println("查询发现内容:" + json);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Comment_Information_By_Own(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT user_comment.id,user_id,user_name,comment_time,comment_title,comment_content,comment_num " +
                "from user_comment,user_info where user_id=? and user_id=user_info.id order by id DESC LIMIT 0,20";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            JSONObject json = new JSONObject();
            JSONArray jsonMembers = new JSONArray();
            while (resultSet.next()) {
//              System.out.println(resultSet.getString(2));
                JSONObject member = new JSONObject();
                member.put("id", resultSet.getInt("id"));
                member.put("comment_time", resultSet.getString("comment_time"));
                member.put("user_name", resultSet.getString("user_name"));
                member.put("comment_title", resultSet.getString("comment_title"));
                member.put("comment_content", resultSet.getString("comment_content"));
                member.put("user_id", resultSet.getInt("user_id"));
                member.put("isFollow", is_User_Follow(userID, resultSet.getInt("user_id")));
                member.put("isLike", Get_User_Is_Like(userID, resultSet.getInt("id")));
                sql = "SELECT COUNT(*) from user_comment_like where commentID=?";
                connectSQL();
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet commentNum = pst.executeQuery();
                if (commentNum.next()) {
                    int num = commentNum.getInt(1);
                    member.put("comment_num", String.valueOf(num));
                } else {
                    member.put("comment_num", String.valueOf(0));
                }
//              member.put("comment_num",resultSet.getString("comment_num"));
                sql = "SELECT COUNT(*) from user_comment_reply where comment_ID=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, resultSet.getInt("id"));
                ResultSet replyNum = pst.executeQuery();
                if (replyNum.next()) {
                    int num = replyNum.getInt(1);
                    member.put("reply_num", String.valueOf(num));
                } else {
                    member.put("reply_num", String.valueOf(0));
                }
                jsonMembers.put(member);
            }
            json.put("user_comment_information", jsonMembers);
            System.out.println("查询我的帖子内容:" + json);
            return json.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Comment_Image(int id) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT comment_image from user_comment where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            String imgCode;
            if (resultSet.next()) {
                InputStream in = resultSet.getBinaryStream("comment_image");
                if (null != in) {
                    byte[] imgByte = new byte[in.available()];
                    in.read(imgByte);
                    imgCode = Base64.encode(imgByte);
                    return imgCode;
                } else {
                    return error;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_All_User_Coment_Info_By_ID(int user, int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT * from user_comment where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String id = rs.getString(1);
                String userID = rs.getString(2);
                String comment_time = rs.getString(3);
                String comment_title = rs.getString(4);
                String comment_content = rs.getString(5);
                InputStream in = rs.getBinaryStream(7);

                String replyCount = Get_User_Comment_Count(commentID);
                String isUserLike = Get_User_Is_Like(rs.getInt(2), commentID);
                String likeNumber = Get_Comment_Like_Number(commentID);
                String isUserFllow = is_User_Follow(user, rs.getInt(2));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("userID", userID);
                jsonObject.put("userName", GetUserNameByUserID(Integer.valueOf(userID)));
                jsonObject.put("comment_time", comment_time);
                jsonObject.put("comment_title", comment_title);
                jsonObject.put("comment_content", comment_content);
                jsonObject.put("replyCount", replyCount);
                jsonObject.put("isUserLike", isUserLike);
                jsonObject.put("likeNumber", likeNumber);
                jsonObject.put("isUserFllow", isUserFllow);
                jsonObject.put("image", Get_User_Comment_Image(commentID));
                return jsonObject.toString();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_Comment_Like_Number(int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT COUNT(*) from user_comment_like where commentID=?";
        try {
            sql = "SELECT COUNT(*) from user_comment_like where commentID=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentID);
            ResultSet commentNum = pst.executeQuery();
            if (commentNum.next()) {
                String num = commentNum.getString(1);
                return num;
            } else {
                return "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Comment_Count(int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT count(*) from user_comment_reply where comment_ID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Is_Like(int userID, int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT * from user_comment_like where userID=? and commentID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, commentID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Comment_Reply(int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT * from user_comment_reply where comment_ID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, commentID);
            ResultSet rs = pst.executeQuery();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                JSONObject member = new JSONObject();
                member.put("reply_id", rs.getInt("reply_id"));
                member.put("reply_time", rs.getString("reply_time"));
                member.put("comment_ID", rs.getString("comment_ID"));
                member.put("user_id", rs.getString("user_id"));
                sql = "SELECT * from user_info where id=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, rs.getInt("user_id"));
                ResultSet resultSet = pst.executeQuery();
                if (resultSet.next()) {
                    member.put("user_name", resultSet.getString("user_name"));
                } else {
                    member.put("user_name", "");
                }
                member.put("reply_content", rs.getString("reply_content"));
                jsonArray.put(member);
            }
            jsonObject.put("reply_information", jsonArray);
            System.out.println(jsonObject);
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Set_User_Like(int userID, int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into user_comment_like(userID,commentID) VALUES(?,?) ";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, commentID);
            if (pst.executeUpdate() == 1) {
                System.out.println("用户" + userID + "点赞了" + commentID);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Cancel_User_Like(int userID, int commentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "delete from user_comment_like where userID=? and commentID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, commentID);
            if (pst.executeUpdate() >= 1) {
                System.out.println("用户" + userID + "取消点赞了" + commentID);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Add_User_Comment_Reply(int userID, int commentID, String replyContent, String intentString) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into user_comment_reply(user_id,comment_id,reply_time,reply_content) values(?,?,?,?)";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, commentID);
            pst.setString(3, time);
            pst.setString(4, replyContent);
            if (pst.executeUpdate() == 1) {
                System.out.println("用户" + userID + "回复了" + commentID + "内容:" + replyContent);
                sql = "SELECT  reply_id from user_comment_reply where user_id=? and comment_id=? and reply_time=? and reply_content=?";
                pst = con.prepareStatement(sql);
                pst.setInt(1, userID);
                pst.setInt(2, commentID);
                pst.setString(3, time);
                pst.setString(4, replyContent);
                ResultSet rs = pst.executeQuery();
                new sendMessage(commentID, userID, replyContent, intentString).start();
                if (rs.next()) {
                    return rs.getString("reply_id");
                }
                return error;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Update_User_Comment_Reply(int replyID, String replyContent) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "UPDATE user_comment_reply SET reply_content=? where reply_id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, replyContent);
            pst.setInt(2, replyID);
            if (pst.executeUpdate() >= 1) {
                System.out.println("回复" + replyID + "更新了内容:" + replyContent);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Delete_User_Comment_Reply(int replyID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "delete from user_comment_reply where reply_id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, replyID);
            if (pst.executeUpdate() >= 1) {
                System.out.println("删除回复" + replyID);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_Follow_Number(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select count(*) from my_focus where focus_userID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                String count = resultSet.getString(1);
                System.out.println("用户" + userID + "关注了" + count + "人");
                return count;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_Fans_Number(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select count(*) from my_focus where focus_fansID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next()) {
                String count = resultSet.getString(1);
                System.out.println("用户" + userID + "粉丝" + count + "人");
                return count;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public int Get_User_Permission(int userID){
        Statement statement=connectSQL();
        if(null==statement)
            return -1;
        sql="select USER_PERMISSION from user_info where id=?";
        try{
            pst=con.prepareStatement(sql);
            pst.setInt(1,userID);
            ResultSet resultSet=pst.executeQuery();
            if(resultSet.next()){
                int permission=resultSet.getInt(1);
                return permission;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return -1;
    }

    public int Get_User_Focus_And_Fans_View_Permission(int userID){
        Statement statement=connectSQL();
        if(null==statement)
            return -1;
        sql="select USER_FOCUS_AND_FANS_VIEW_PERMISSION from user_info where id=?";
        try{
            pst=con.prepareStatement(sql);
            pst.setInt(1,userID);
            ResultSet resultSet=pst.executeQuery();
            if(resultSet.next()){
                int permission=resultSet.getInt(1);
                return permission;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return -1;
    }

    public String Set_User_Permission(int userID,int permission){
        Statement statement=connectSQL();
        if(null==statement)
            return error;
        sql="UPDATE user_info set USER_PERMISSION=? where id=?";
        try{
            pst=con.prepareStatement(sql);
            pst.setInt(1,permission);
            pst.setInt(2,userID);
            if(pst.executeUpdate()>0){
                System.out.println("用户："+userID+" 修改权限:"+permission);
                return success;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Set_User_Focus_And_Fans_View_Permission(int userID,int permission){
        Statement statement=connectSQL();
        if(null==statement)
            return error;
        sql="UPDATE user_info set USER_FOCUS_AND_FANS_VIEW_PERMISSION=? where id=?";
        try{
            pst=con.prepareStatement(sql);
            pst.setInt(1,permission);
            pst.setInt(2,userID);
            if(pst.executeUpdate()>0){
                System.out.println("用户："+userID+" 修改查看粉丝和关注权限:"+permission);
                return success;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }



    public String Get_User_All_Info(int userID) {
        String userName = GetUserNameByUserID(userID);
        String focusNumber = Get_Follow_Number(userID);
        String fansNumber = Get_Fans_Number(userID);
        int permission=Get_User_Permission(userID);
        int focusAndFansPermission=Get_User_Focus_And_Fans_View_Permission(userID);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", userName);
            jsonObject.put("focusNumber", focusNumber);
            jsonObject.put("fansNumber", fansNumber);
            jsonObject.put("permission",permission);
            jsonObject.put("focusAndFansPermission",focusAndFansPermission);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }

    public String Get_Follow_Information(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select focus_fansID,USER_NAME,focus_userID from my_focus,user_info where my_focus.focus_userID=? and my_focus.focus_fansID=user_info.ID";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            JSONObject data = new JSONObject();
            JSONArray info = new JSONArray();
            while (resultSet.next()) {
                JSONObject object = new JSONObject();
                object.put("focus_fansID", resultSet.getString("focus_fansID"));
                object.put("focus_focusID", resultSet.getString("focus_userID"));
                object.put("USER_NAME", resultSet.getString("USER_NAME"));
                System.out.println(resultSet.getString("USER_NAME"));
                info.put(object);
            }
            data.put("Follow_Information", info);
            System.out.println("用户" + userID + "关注的信息:" + data);
            return data.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_Fans_Information(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select focus_userID,USER_NAME,focus_fansID from my_focus,user_info where my_focus.focus_fansID=? and my_focus.focus_userID=user_info.ID";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet resultSet = pst.executeQuery();
            JSONObject data = new JSONObject();
            JSONArray info = new JSONArray();
            while (resultSet.next()) {
                JSONObject object = new JSONObject();
                object.put("focus_focusID", resultSet.getString("focus_userID"));
                object.put("focus_fansID", resultSet.getString("focus_fansID"));
                object.put("USER_NAME", resultSet.getString("USER_NAME"));
//              System.out.println(resultSet.getString("USER_NAME"));
                info.put(object);
            }
            data.put("Follow_Information", info);
            System.out.println("用户" + userID + "粉丝的信息:" + data);
            return data.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Add_Focus(int userID, int focusID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "insert into my_focus(focus_userID,focus_fansID) VALUES (?,?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, focusID);
            if (pst.executeUpdate() == 1) {
                System.out.println("用户" + userID + "关注了" + focusID);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Cancel_Focus(int userID, int focusID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "delete from my_focus where focus_userID=? and focus_fansID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            pst.setInt(2, focusID);
            if (pst.executeUpdate() >= 1) {
                System.out.println("用户" + userID + "取消关注了" + focusID);
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Check_Follow_Eachohter(int userID, int focusID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "SELECT count(*) from my_focus where focus_userID=? and focus_fansID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, focusID);
            pst.setInt(2, userID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return success;
                }
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_Search_UserInfo(String name) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select id,user_name from user_info where user_name like ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, '%' + name + '%');
            ResultSet resultSet = pst.executeQuery();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject object = new JSONObject();
                object.put("id", resultSet.getString("id"));
                object.put("user_name", resultSet.getString("user_name"));
                jsonArray.put(object);
            }
            jsonObject.put("searchInfo", jsonArray);
            System.out.println("查询到的结果:" + jsonObject);
            return jsonObject.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String is_User_Follow(int userName, int fansName) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select * from my_focus where focus_userID=? and focus_fansID=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userName);
            pst.setInt(2, fansName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Image(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "select USER_IS_HAD_IMAGE,USER_IMAGE from user_info where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt("USER_IS_HAD_IMAGE") == 0) {
                    return error;
                }
                InputStream in = rs.getBinaryStream("USER_IMAGE");
                if (in == null)
                    return error;
                byte[] img = new byte[in.available()];
                in.read(img);
                String imageCode = Base64.encode(img);
                System.out.println("用户" + userID + "获取头像");
                return imageCode;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Update_User_Image(int userID, String image) {
        Statement statement = connectSQL();
        if (null == statement) {
            return null;
        }
        sql = "UPDATE USER_INFO set USER_IMAGE=?,USER_IS_HAD_IMAGE=?,USER_UPDATE_TIME=? where id=?";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = df.format(new Date());
            pst = con.prepareStatement(sql);
            pst.setInt(2, 1);
            pst.setString(3, time);
            pst.setInt(4, userID);
            byte[] code = Base64.decode(image);
            InputStream in = new ByteArrayInputStream(code);
            pst.setBinaryStream(1, in);
            if (pst.executeUpdate() == 1) {
                System.out.println("用户" + userID + "更新头像");
                return success;
            } else {
                return error;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    public String Get_User_Update_Time(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return error;
        }
        sql = "select USER_UPDATE_TIME from user_info where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String updateTime = rs.getString("USER_UPDATE_TIME");
                return updateTime;
            }
            return error;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            closeSQL();
        }
        return error;
    }

    private int GetUserIdFromCommentID(int CommentID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return -1;
        }
        sql = "select user_id from user_comment where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, CommentID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("user_id");
                return userID;
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return -1;
    }

    private String GetUserNameByUserID(int userID) {
        Statement statement = connectSQL();
        if (null == statement) {
            return error;
        }
        sql = "select USER_NAME from user_info where id=?";
        try {
            pst = con.prepareStatement(sql);
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("USER_NAME");
                return userName;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    class sendMessage extends Thread {
        int userID;
        int commentID;
        String replayUserName, commentCreateUserName;
        String content;
        String intentString;

        public sendMessage(int commentID, int userID, String content, String intentString) {
            this.userID = userID;
            this.commentID = commentID;
            this.content = content;
            this.intentString = intentString;
        }

        @Override
        public void run() {
            int commentCreateUserID = GetUserIdFromCommentID(commentID);
            if (commentCreateUserID <= 0)
                return;
            if (commentCreateUserID == userID)
                return;
            replayUserName = GetUserNameByUserID(userID);
            commentCreateUserName = GetUserNameByUserID(commentCreateUserID);
            if (replayUserName == null || commentCreateUserName == null)
                return;
            Constants.useOfficial();
            //在这里输入推送的AppID
//            Sender sender = new Sender("");
            String messagePayload = "新回复";
            String title = "新回复";
            String description = replayUserName + "回复:" + content;
            String useraccount = commentCreateUserName;    //useraccount非空白, 不能包含逗号, 长度小于128
            Message message = new Message.Builder()
                    .title(title)
                    .description(description).payload(messagePayload)
                    //在这里输入应用的包名
                    .restrictedPackageName("")
                    .notifyType(1)     // 使用默认提示音提示
                    .extra(Constants.EXTRA_PARAM_NOTIFY_EFFECT, Constants.NOTIFY_ACTIVITY) //让通知能打开对应的activity
                    .extra(Constants.EXTRA_PARAM_INTENT_URI, intentString) //告诉客户端要打开哪个Activity
                    .build();
            try {
                sender.sendToUserAccount(message, useraccount, 3);//根据useraccount, 发送消息到指定设备上
                System.out.println("向用户" + commentCreateUserName + "发送了" + content + "推送");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
