/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.User;

/**
 *
 * @author hacongtuan
 */
public class SocketHandle implements Runnable{
    private BufferedWriter os;
    private BufferedReader is;
    private Socket socketOfClient;
    private int ID_Server;

   
    
    public User getUserFromString(int start, String[] message){
        return new User(
                Integer.parseInt(message[start]),
                message[start+1],
                message[start+2],
                message[start+3],
                Integer.parseInt(message[start+4]),
                Integer.parseInt(message[start+5]),
                Integer.parseInt(message[start+6]),
                Integer.parseInt(message[start+7]),
                      Integer.parseInt(message[start+8]));
    }
    public List<User> getListUser(String[] message){
        List<User> friend = new ArrayList<>();
        for(int i=1; i<message.length; i=i+4){
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i+1],
                    Integer.parseInt(message[i+2]),
                    Integer.parseInt(message[i+3])));
        }
        return friend;
    }
    
    @Override
    public void run(){
    try{
        //gui yeu cau ket noi den serer
    socketOfClient = new Socket("127.0.0.1", 5500); 
    System.out.println("Kết nối thành công!");
    
    
    // Tạo luồng đầu ra tại client (Gửi dữ liệu tới server)
    os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
  // Luồng đầu vào tại Client (Nhận dữ liệu từ server).
    is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
            String message;
           while(true){
            System.out.println("vao lang nghe");
            message = is.readLine();
            if(message == null){
            break;
            }
            
            System.out.println(message);
            
            String[] messageSplit = message.split(",");
            
            if(messageSplit[0].equals("server-send-id")){
                    ID_Server = Integer.parseInt(messageSplit[1]);
                }
            if(messageSplit[0].equals("login-success")){
                    System.out.println("Đăng nhập thành công");
                    Client.closeAllViews();
                    User user= getUserFromString(1,messageSplit);
                    Client.user = user;
                    System.out.println(Client.user.getNickname());
                    Client.openView(Client.View.HOMEPAGE);
                }
            if(messageSplit[0].equals("wrong-user")){
                    System.out.println("Thông tin sai");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.LOGIN);
                    Client.loginFrm.showError("Tài khoản hoặc mật khẩu không chính xác");
                }
            if(messageSplit[0].equals("chat-server")){
                    if(Client.homePageFrm!=null){
                        Client.homePageFrm.addMessage(messageSplit[1]);
                    }
            }
            if(messageSplit[0].equals("your-created-room")){
                    Client.closeAllViews();
                    Client.openView(Client.View.WAITINGROOM);
                    Client.waitingRoomFrm.setRoomName(messageSplit[1]);
                    if(messageSplit.length==3)
                        Client.waitingRoomFrm.setRoomPassword("Mật khẩu phòng: "+messageSplit[2]);
                }
            
            if(messageSplit[0].equals("go-to-room")){
                    System.out.println("Vào phòng");
                    int roomID = Integer.parseInt(messageSplit[1]);
                    String competitorIP = messageSplit[2];
                    int isStart = Integer.parseInt(messageSplit[3]);
                    
                    User competitor = getUserFromString(4, messageSplit);
                    if(Client.findRoomFrm!=null){
                        Client.findRoomFrm.showFindedRoom();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.findRoomFrm, "Lỗi khi sleep thread");
                        }
                    } else if(Client.waitingRoomFrm!=null){
                        Client.waitingRoomFrm.showFindedCompetitor();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ex) {
                            JOptionPane.showMessageDialog(Client.waitingRoomFrm, "Lỗi khi sleep thread");
                        }
                    }
                    Client.closeAllViews();
                    System.out.println("Đã vào phòng: "+roomID);
                    //Xử lý vào phòng
                    Client.openView(Client.View.GAMECLIENT
                            , competitor
                            , roomID
                            ,isStart
                            ,competitorIP);
                    Client.gameClientFrm.newgame();
                }
             //Xử lý đánh một nước trong ván chơi
                if(messageSplit[0].equals("caro")){
                    Client.gameClientFrm.addCompetitorMove(messageSplit[1], messageSplit[2]);
                }
                if(messageSplit[0].equals("chat")){
                    Client.gameClientFrm.addMessage(messageSplit[1]);
                }
             //game moi 
                if(messageSplit[0].equals("new-game")){
                    System.out.println("New game");
                    Thread.sleep(4000);
                    Client.gameClientFrm.updateNumberOfGame();
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.gameClientFrm.newgame();
                }
                if(messageSplit[0].equals("draw-request")){
                    Client.gameClientFrm.showDrawRequest();
                }
                if(messageSplit[0].equals("draw-refuse")){
                    if(Client.gameNoticeFrm!=null) Client.closeView(Client.View.GAMENOTICE);
                    Client.gameClientFrm.displayDrawRefuse();
                }
                 if(messageSplit[0].equals("draw-game")){
                    System.out.println("Draw game");
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.openView(Client.View.GAMENOTICE, "Ván chơi hòa", "Ván chơi mới dang được thiết lập");
                    Client.gameClientFrm.displayDrawGame();
                    Thread.sleep(4000);
                    Client.gameClientFrm.updateNumberOfGame();
                    Client.closeView(Client.View.GAMENOTICE);
                    Client.gameClientFrm.newgame();
                }
                 //Xử lý lấy danh sách phòng
                if(messageSplit[0].equals("room-list")){
                    Vector<String> rooms = new Vector<>();
                    Vector<String> passwords = new Vector<>();
                    for(int i=1; i<messageSplit.length; i=i+2){
                        rooms.add("Phòng "+messageSplit[i]);
                        passwords.add(messageSplit[i+1]);
                    }
                    Client.roomListFrm.updateRoomList(rooms,passwords);
                }
                // danh sach de thach dau
                if(messageSplit[0].equals("return-challe-list")){
                    if(Client.challegeFrm !=null){
                        Client.challegeFrm.updateChallengeList(getListUser(messageSplit));
                    }
                }
                // xu ly nhan thach dau
                 if(messageSplit[0].equals("duel-notice")){
                    int res = JOptionPane.showConfirmDialog(Client.getVisibleJFrame(), "Bạn nhận được lời thách đấu của "+messageSplit[2]+" (ID="+messageSplit[1]+")", "Xác nhận thách đấu", JOptionPane.YES_NO_OPTION);
                    if(res == JOptionPane.YES_OPTION){
                        Client.socketHandle.write("agree-duel,"+messageSplit[1]+",0");
                    }
                    else{
                        Client.socketHandle.write("disagree-duel,"+messageSplit[1]);
                    }
                }
                 // xu ly tu choi thanh dau
                 if(messageSplit[0].equals("disagree-duel")){
                    Client.closeAllViews();
                    Client.openView(Client.View.HOMEPAGE);
                    JOptionPane.showMessageDialog(Client.homePageFrm, "Đối thủ không đồng ý thách đấu");
                }
                 //
                if(messageSplit[0].equals("check-friend-response")){
                    if(Client.competitorInfoFrm!=null){
                        Client.competitorInfoFrm.checkFriend((messageSplit[1].equals("1")));
                    }
                }
           }
    }catch(UnknownHostException e){
    e.printStackTrace();
    }catch (IOException e) {
          e.printStackTrace();
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }   
    
     public void write(String message) throws IOException{
        System.out.println("gui mess "+message);
        os.write(message);
        os.newLine();
        os.flush();
    }  
       
    public Socket getSocketOfClient() {
        return socketOfClient;
    }
   
   
}
