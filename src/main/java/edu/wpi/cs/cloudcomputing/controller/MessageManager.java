package edu.wpi.cs.cloudcomputing.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.wpi.cs.cloudcomputing.database.PrivateMessageDAO;
import edu.wpi.cs.cloudcomputing.model.PrivateMessage;
import edu.wpi.cs.cloudcomputing.utils.Common;

import java.util.List;
import java.lang.reflect.Type;


/**
 * Created by tonggezhu on 3/7/18.
 */
public class MessageManager {

    PrivateMessageDAO privateMessageDAO = null;

    public String getAllMessage(String email) throws Exception {
        privateMessageDAO = new PrivateMessageDAO();
        List<PrivateMessage> privateMessageList = privateMessageDAO.getInboxByUserEmail(email);
        Type listType = new TypeToken<List<PrivateMessage>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();
        return gson.toJson(privateMessageList, listType);
    }

    public Boolean addMessage(String fromUserEmail, String toUserEmail, String title, String content, String type) throws Exception {
        PrivateMessage privateMessage = new PrivateMessage();
        String pmId =System.currentTimeMillis()%100000000+"";
        privateMessage.setPmId(pmId);
        privateMessage.setSenderEmail(fromUserEmail);
        privateMessage.setReceiverEmail(toUserEmail);
        privateMessage.setTitle(title);
        privateMessage.setContent(content);
        privateMessage.setType(type);
        privateMessage.setStatus(Common.UNOPEN);
        privateMessageDAO = new PrivateMessageDAO();
        privateMessageDAO.addPrivateMessage(privateMessage);
        return true;
    }

    public Boolean readMessage(String pmId) throws Exception {
        privateMessageDAO = new PrivateMessageDAO();
        privateMessageDAO.readPrivateMessage(pmId);

        return true;
    }


    public Boolean deleteMessage(String pmId) throws Exception {
        privateMessageDAO = new PrivateMessageDAO();
        privateMessageDAO.deletePrivateMessage(pmId);
        return true;
    }

    public Boolean ignoreMessage(String pmId) throws Exception {
        privateMessageDAO = new PrivateMessageDAO();
        privateMessageDAO.ignorePrivateMessage(pmId);
        return true;

    }

}
