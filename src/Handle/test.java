package Handle;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * Created by sunkai on 2017/3/15.
 */
public class test {
    public static void main(String args[]) {
        HandleUser w = new HandleUser();
//        w.User_Regist("test","test","test","test");
//        System.out.println(w.regist_in("test","test"));
//        System.out.println(w.Find_Password_Question("test"));
//        System.out.println(w.Check_Question_Answer("test","test"));
//        System.out.println(w.Change_Password("test","test"));
//        try {
//            InputStream in = new BufferedInputStream(new FileInputStream("D:\\Document\\AndroidStudioProjects\\heritage-online\\app\\src\\main\\res\\mipmap\\img4.jpg"));
//            ByteArrayOutputStream out=new ByteArrayOutputStream();
//            byte[] a;
//            byte[] buffer=new byte[1024];
//            int len;
//            while((len=in.read(buffer))!=-1){
//                out.write(buffer,0,len);
//            }
//            a=out.toByteArray();
//            String b= org.apache.axis.encoding.Base64.encode(a);
//            w.Add_Main_Activity("4","4",b);
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        String result=w.Read_Main_Activity();
//        try {
//            JSONObject object = new JSONObject(result);
//            JSONArray jsonArray=object.getJSONArray("main_Activity");
//            JSONObject activity=(JSONObject)jsonArray.get(0);
//            String imgCode=activity.getString("activity_image");
//            byte[] img= org.apache.axis.encoding.Base64.decode(imgCode);
//            FileOutputStream out=new FileOutputStream("d:/123.jpg");
//            out.write(img);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println(w.Get_Channel_Information("口头传统表现形式"));
//        w.Add_Classify_Activity_Information("1","1","1","1");
//        System.out.println(w.Get_Channel_Image("口头传统表现形式"));

//        try {
//
//            String[] texts={"一部视频文件，如果说帧率，码率，分辨率是他的硬指标的话，能否将这些硬指标完全的发挥出来就要取决于播放器，现在的播放器如同雨后春笋一般冒出来很多，大部分都打着“支持高清”的旗号，但实际上现在主流的视频播放器（比如暴风影音，迅雷看看什么的）对于H.264封装的高清mkv文件实际播放效果并不是十分的出色。"
//                    ,"一部视频，需要经过播放器的分离器将其中的视频流，音频流等等分离出来，常见的分离器比如LAV,HAALI等等，接下来解码器将分离出来的各个流进行解码，转换成可以识别出来的的通用视频流，接下来交由cpu/显卡（软解，硬解）对视频进行解码"
//                    ,"为什么说常见的暴风影音或者迅雷看看等播放器不怎么样呢，就是因为其分离器解码器渲染器都比较的次，而且渲染出来的之后视频往往会增加很多的特效上去，弄得很光啊，很柔啊，很亮啊，结果实际上这完全偏离了原本视频的内容",
//                    "迅雷看看本身，这些效果就好比给一个女人化妆一下，让他乍一看很好看，实际上其渲染出来的素颜惨不忍睹，当然QQ影音本身的硬解还是很出色，如果不喜欢完美解码的复杂选项，完全可以用potplayer"
//                    ,"更好级别的是完美解码（基本上完美解码的各种设置已经基本上可以满足各种各样的需求了），最高端的就是自由的选择播放器，解码器，分离器等等(比如MPC+各种开源分离器，解码器等等，实际上，完美解码干的就是这个工作），一个好的播放器，也是相当重要的。",
//            "日志里，我经常提到的视频编码就是H.264了，H.264是MPEG-4的一种视频规范之一，通常写为H.264/AVC，是一种压缩比率很高，同样也很清晰的高度压缩高清视频解码器标准，被广泛用于网络传输，蓝光视频当中。H.264所支持的视频封装格式有MKV,MP4,AVI,F4V等等"};
//            for(int i=13;i<=18;i++) {
//                InputStream in = new BufferedInputStream(new FileInputStream("F:\\img\\"+i+".jpg"));
//                byte[] img = new byte[in.available()];
//                in.read(img);
//                String b = org.apache.axis.encoding.Base64.encode(img);
//                w.Add_Classify_Activity_Information("测试活动"+(i+12), texts[i-13], "传统体育和技艺",b);
//            }
//        }
//        catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }

//        System.out.println(w.Get_Channel_Image(7));

//        String result=w.Get_Channel_Image(7);
//        try {
//            JSONObject object = new JSONObject(result);
//            JSONArray jsonArray=object.getJSONArray("classify_activity_image");
//            JSONObject activity=(JSONObject)jsonArray.get(0);
//            String imgCode=activity.getString("activity_image");
//            byte[] img= org.apache.axis.encoding.Base64.decode(imgCode);
//            FileOutputStream out=new FileOutputStream("d:/123.jpg");
//            out.write(img);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        try {
//            for(int i=1;i<5;i++) {
//                InputStream in = new BufferedInputStream(new FileInputStream("E:\\new\\" + i + ".jpg"));
//                byte[] img = new byte[in.available()];
//                in.read(img);
//                String b = org.apache.axis.encoding.Base64.encode(img);
//                System.out.println(w.Update_Main_Activity(b,i+1));
////            System.out.println(w.Update_classify_activity(b,12));
//            }
//            String[] locations={"华东","华东","华南","华北","西北","西南","东北","华北","西北"};
//            String titles[]={"Java程序设计第二讲(走进Java编程之门)","Java程序设计第三讲(Java语言基础)","Java程序设计第四讲(面向对象思想之封装机制)"
//                    ,"程序员编程艺术  第一~三十七章集锦by_July","大话设计模式","第二部分 集合论","第五部分 代数结构","第三部分 图论","离散数学CH02_命题逻辑"};
//            String contents[]={" 厨师：处理器 • 锅碗瓢盆:内存及外设，各种资源 • 食材：进程的输入（要处理的数据） • 做好的菜：进程的输出 • 菜谱：程序 • 过程有不同的状态：执行中，等待、就绪 ",
//                    " 正常退出（自愿，return，exit） \uF0A7 进程发现了错误：出错退出（输入错误，自愿） \uF0A7 进程引起了错误：严重错误（被0除，非自愿） \uF0A7 被其他进程杀死（非自愿",
//                    "\uF0A7 父进程和子进程们组成进程组 \uF0A7 像人类的家族树一样，具有继承关系 \uF0A7 所有的用户进程都是init进程的子孙 \uF076在windows操作系统中 ",
//                    "工作方式 \uF0A7 从客户端接收网页请求（协议？） \uF0A7 从磁盘上检索相关网页，读入内存 \uF0A7 将网页返回给对应的客户",
//                    "\uF0A7 有线程上下文（寄存器信息） \uF0A7 有一个执行栈 \uF0A7 有生命周期的状态 • 就绪、等待和运行 \uF0A7 有一些局部变量的静态存储 \uF0A7 可存取所在进程的内存和其他资源 \uF0A7 可以创建、撤消另一个线程",
//                    "\uF076并发性 \uF0A7 一个进程中可以有多个线程并发执行，提高系统资源 的利用率 \uF076系统开销 \uF0A7 系统要为进程分配",
//                    "库） 缺点： \uF076大多数系统调用是阻塞的，因此，由于内核阻塞 进程，故进程中所有线程也被阻塞 \uF076 内核只将处理器分配给进程，同",
//                    "“不是，是轻重的轻，媒体上都这么叫。是一个星期前在附近城市流行的，感染率很高，但症状很轻，不发烧，就是流鼻涕，部分患者可能嗓子疼。不用吃药，三天左右就自动痊愈了。”",
//                    "主席先生，”罗辑说，“另外三位面壁者都已经在自己的战略计划执行过程中调用了大量的资源，对我的计划的这种资源限制是不公平的。”"
//            };
//            for(int i=0;i<titles.length;i++){
//                InputStream in = new BufferedInputStream(new FileInputStream("D:\\Document\\AndroidStudioProjects\\heritage-online\\app\\src\\main\\res\\mipmap\\f"+(i+1)+".jpg"));
//                byte[] img=new byte[in.available()];
//                in.read(img);
//                String b= org.apache.axis.encoding.Base64.encode(img);
//                w.Add_Folk_Activity(titles[i],contents[i],locations[i],b);
//            }
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        w.Get_Folk_Count();
//        System.out.println(w.Get_Folk_Information());
//        System.out.println(w.Get_Folk_Image(1));
//        System.out.println(w.Get_Folk_Single_Information(1));
//        System.out.println(w.Add_User_Order(1,1));
//        System.out.println(w.Cancel_User_Order(2));
//        System.out.println(w.Get_User_Order(1));
//        System.out.println(w.Get_User_ID("sunkai"));
//        System.out.println(w.Check_Is_Order(12,9));
//        System.out.println(w.Get_Folk_Information(12));
//        System.out.println(w.Cancel_User_Order(12,9));

//        try {
//            for (int i = 0; i < 9; i++) {
//                InputStream in = new BufferedInputStream(new FileInputStream("E:\\new\\"+(i+1)+".jpg"));
//                byte[] img = new byte[in.available()];
//                in.read(img);
//                String b = org.apache.axis.encoding.Base64.encode(img);
//                System.out.println(w.Update_folk_activity_Image(b,i+1));
//            }
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println(w.Get_User_Comment_Information());
//        System.out.println(w.Get_User_Comment_Image(1));
//        System.out.println(w.Get_User_Is_Like(7,1));
//        System.out.println(w.Get_User_Comment_Reply(1));
//        System.out.println(w.Set_User_Like(1,1));
//        System.out.println(w.Cancel_User_Like(1,1));
//        System.out.println(w.Add_User_Comment_Reply(7,9,"ceshi"));
//        System.out.println(w.Update_User_Comment_Reply(9,"c123eshi2"));
//        System.out.println(w.Delete_User_Comment_Reply(9));
//        System.out.println(w.Get_User_Comment_Count(9));
//        System.out.println(w.Get_Follow_Number(12));
//        System.out.println(w.Get_Focus_Number(12));
//        System.out.println(w.Get_Follow_Information(12));
//        System.out.println(w.Get_Fans_Information(12));
//        System.out.println(w.Add_Focus(3,3));
//        System.out.println(w.Cancel_Focus(3,3));
//        System.out.println(w.Get_Search_UserInfo("sun"));
//        System.out.println(w.is_User_Follow(12,7));
//        try {
//            for (int i = 0; i < 4; i++) {
//                InputStream in = new BufferedInputStream(new FileInputStream("E:\\new\\"+(i+1)+".jpg"));
//                byte[] img = new byte[in.available()];
//                in.read(img);
//                String b = org.apache.axis.encoding.Base64.encode(img);
//                System.out.println(w.Add_Find_Activity(String.valueOf(i+1),String.valueOf(i+1),b));
//            }
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println(w.Get_Find_Activity_ID());
//        System.out.println(w.Get_Find_Activity_Information(1));
//        System.out.println(w.Search_Folk_Info("天气"));
//        try {
//            for (int i = 0; i < 9; i++) {
//                InputStream in = new BufferedInputStream(new FileInputStream("E:\\new\\"+(i+1)+".jpg"));
//                byte[] img = new byte[in.available()];
//                in.read(img);
//                String b = org.apache.axis.encoding.Base64.encode(img);
//                System.out.println(w.Update_folk_activity_Image(b,(i+1)));
//            }
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        System.out.println(w.Get_User_Comment_Information_By_User(12));
//        System.out.println(w.Get_User_Comment_Information_By_Own(12));
//        String a=w.Read_Main_Activity();
//        String a=w.Get_Find_Activity_ID();
//        try {
//            JSONObject jsonObject = new JSONObject(a);
//            JSONArray jsonArray=(JSONArray)jsonObject.get("id");
//            for(int i=0;i<jsonArray.length();i++){
////                JSONObject object=(JSONObject)jsonArray.get(i);
//                int id=(int)jsonArray.get(i);
//                String json=w.Get_Find_Activity_Information(id);
//                JSONObject jsonObject1=new JSONObject(json);
//                String imagecode=jsonObject1.getString("image");
//                byte[] image= org.apache.axis.encoding.Base64.decode(imagecode);
//                InputStream in=new ByteArrayInputStream(image);
//                Image img=ImageIO.read(in);
//                System.out.println(id);
//                double newWidth;
//                double newHeight;
//                newWidth=img.getWidth(null)*0.8;
//                newHeight=img.getHeight(null)*0.8;
//                BufferedImage tag = new BufferedImage((int)newWidth, (int)newHeight, BufferedImage.TYPE_INT_RGB);
//                tag.getGraphics().drawImage(
//                        img.getScaledInstance((int)newWidth, (int)newHeight,
//                                Image.SCALE_SMOOTH), 0, 0, null);
//                ByteArrayOutputStream out=new ByteArrayOutputStream();
//                ImageIO.write(tag,"jpg",out);
//                byte[] finalImage=out.toByteArray();
//                String result= org.apache.axis.encoding.Base64.encode(finalImage);
//                w.Update_Find_Activity_Image(result,id);
//            }
//        }
//        catch (JSONException|IOException e){
//            e.printStackTrace();
//        }
        w.Delete_User_Comment_By_ID(15);

//        Image img= ImageIO.read()
    }

}
