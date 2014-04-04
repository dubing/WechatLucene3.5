package com.nike.wechatlucene.model;

import java.util.Date;

/**
 * Created by bing.du on 4/3/14.
 */
public class WeMessage {
    private String Id;
    private String GroupId;
    private String SendUser;
    private String Content;
    private Date SendDate;

    public WeMessage()
    {

    }
    public WeMessage(String id, String groupId, String sendUser, String content, Date sendDate) {
        Id = id;
        GroupId = groupId;
        SendUser = sendUser;
        Content = content;
        SendDate = sendDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getSendUser() {
        return SendUser;
    }

    public void setSendUser(String sendUser) {
        SendUser = sendUser;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getSendDate() {
        return SendDate;
    }

    public void setSendDate(Date sendDate) {
        SendDate = sendDate;
    }
}