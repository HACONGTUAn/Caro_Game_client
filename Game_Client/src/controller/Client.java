/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.swing.JFrame;
import model.User;
import view.ChallengeFrm;
import view.CompetitorInfoFrm;
import view.CreateRoomPasswordFrm;
import view.FindRoomFrm;
import view.FriendRequestFrm;
import view.GameClientFrm;
import view.GameNoticeFrm;
import view.HomePageFrm;
import view.JoinRoomPasswordFrm;
import view.LoginFrm;
import view.RegisterFrm;
import view.RoomListFrm;
import view.RoomNameFrm;
import view.WaitingRoomFrm;

/**
 *
 * @author hacongtuan
 */
public class Client {
    public enum View{
    LOGIN,
    HOMEPAGE,
    GAMENOTICE,
    REGISTER,
    GAMECLIENT,
    CREATEROOMPASSWORD,
    ROOMNAMEFRM,
    WAITINGROOM,
    COMPETITORINFO,
    FINDROOM,
    ROOMLIST,
    JOINROOMPASSWORD,
    CHALLENGE
    }
    public static User user;
    public static LoginFrm loginFrm;
    public static HomePageFrm homePageFrm;
    public static GameNoticeFrm gameNoticeFrm;
    public static RegisterFrm registerFrm;
    public static GameClientFrm gameClientFrm;
    public static CreateRoomPasswordFrm createRoomPasswordFrm;
    public static RoomNameFrm roomNameFrm;
    public static WaitingRoomFrm waitingRoomFrm;
    public static CompetitorInfoFrm competitorInfoFrm;
    public static FindRoomFrm findRoomFrm;
    public static RoomListFrm roomListFrm;
    public static JoinRoomPasswordFrm joinRoomPasswordFrm;
    public static ChallengeFrm challegeFrm;
    
    public static SocketHandle socketHandle;
    public Client(){}
    
    
    public static JFrame getVisibleJFrame(){
       /* if(roomListFrm!=null&&roomListFrm.isVisible())
            return roomListFrm;
        if(friendListFrm!=null&&friendListFrm.isVisible()){
            return friendListFrm;
        }
        if(createRoomPasswordFrm!=null&&createRoomPasswordFrm.isVisible()){
            return createRoomPasswordFrm;
        }
        if(joinRoomPasswordFrm!=null&&joinRoomPasswordFrm.isVisible()){
            return joinRoomPasswordFrm;
        }
        if(rankFrm!=null&&rankFrm.isVisible()){
            return rankFrm;
        }*/
        return homePageFrm;
    }
    public void initView() {
    loginFrm = new LoginFrm();
    loginFrm.setVisible(true);
    socketHandle = new SocketHandle();
    socketHandle.run();
    }
    
    public static void openView(View viewName){
    if(viewName != null){
    switch(viewName){
        case LOGIN:
            loginFrm = new LoginFrm();
            loginFrm.setVisible(true);
            break;
        case HOMEPAGE:
            homePageFrm = new HomePageFrm();
            homePageFrm.setVisible(true);
            break;
        case REGISTER:
            registerFrm = new RegisterFrm();
            registerFrm.setVisible(true);
            break;
        case CREATEROOMPASSWORD:
            createRoomPasswordFrm = new CreateRoomPasswordFrm();
            createRoomPasswordFrm.setVisible(true);
            break;
        case ROOMNAMEFRM:
            roomNameFrm = new RoomNameFrm();
            roomNameFrm.setVisible(true);
            break;
        case WAITINGROOM:
            waitingRoomFrm = new WaitingRoomFrm();
            waitingRoomFrm.setVisible(true);
            break;
        case FINDROOM:
            findRoomFrm = new FindRoomFrm();
            findRoomFrm.setVisible(true);
            break;
        case ROOMLIST:
            roomListFrm = new RoomListFrm();
            roomListFrm.setVisible(true);
            break;
        case CHALLENGE:
            challegeFrm = new ChallengeFrm();
            challegeFrm.setVisible(true);
            break;
    }
  }
}
    public static void openView(View viewName, String arg1, String arg2){
        if(viewName != null){
            switch(viewName){
                case GAMENOTICE:
                    gameNoticeFrm = new GameNoticeFrm(arg1, arg2);
                    gameNoticeFrm.setVisible(true);
                    break;
                case LOGIN:
                    loginFrm = new LoginFrm(arg1, arg2);
                    loginFrm.setVisible(true);
                
            }
        }
    }
    public static void openView(View viewName, int arg1, String arg2){
        if(viewName != null){
            switch(viewName){
                case JOINROOMPASSWORD:
                    joinRoomPasswordFrm = new JoinRoomPasswordFrm(arg1, arg2);
                    joinRoomPasswordFrm.setVisible(true);
                    break;
            }
        }
    }
    public static void openView(View viewName, User competitor, int room_ID, int isStart, String competitorIP){
        if(viewName != null){
            switch(viewName){
                case GAMECLIENT:
                    gameClientFrm = new GameClientFrm(competitor, room_ID, isStart, competitorIP);
                    gameClientFrm.setVisible(true);
                    break;
            }
        }
    }
    public static void openView(View viewName, User user) throws IOException{
        if(viewName != null){
            switch(viewName){
                case COMPETITORINFO:
                    competitorInfoFrm = new CompetitorInfoFrm(user);
                    competitorInfoFrm.setVisible(true);
                    break;
            }
        }
    }
    public static void closeView(View viewName){
    if(viewName != null){
    switch(viewName){
        case LOGIN:
            loginFrm.dispose();
            break;
        case HOMEPAGE:
            homePageFrm.dispose();
            break;
        case CREATEROOMPASSWORD:
            createRoomPasswordFrm.dispose();
            break;
        case GAMENOTICE :
            gameNoticeFrm.dispose();
            break;
        case FINDROOM:
            findRoomFrm.dispose();
            break;
        case ROOMLIST:
            roomListFrm.dispose();
            break;
    }
    }
    }
    public static void closeAllViews(){
    if(loginFrm != null) loginFrm.dispose();
    if(homePageFrm != null) homePageFrm.dispose(); 
    if(gameNoticeFrm != null) gameNoticeFrm.dispose();
    if(waitingRoomFrm!=null) waitingRoomFrm.dispose();
    if(createRoomPasswordFrm!=null) createRoomPasswordFrm.dispose();
    if(roomNameFrm!=null) roomNameFrm.dispose();
    }
    
    public static void main(String[] args){
    new Client().initView();
    }
}
