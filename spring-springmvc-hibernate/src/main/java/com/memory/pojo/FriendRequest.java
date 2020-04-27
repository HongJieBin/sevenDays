package com.memory.pojo;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @ClassName FriendRequest
 * @Description 好友请求发送表
 * @Author SupreacyXXXXX
 * @Date 2020/4/26
 * @Version 1.0
 **/

@Entity
@Table(name = "friend_request")
@DynamicInsert
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "send_user_id", nullable = false)
    private int sendId;

    @Column(name = "recieve_user_id", nullable = false)
    private int recieveId;

    @Column(name = "send_time", nullable = false)
    private Timestamp sendTime;

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getRecieveId() {
        return recieveId;
    }

    public void setRecieveId(int recieveId) {
        this.recieveId = recieveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendRequest that = (FriendRequest) o;
        return id == that.id &&
                sendId == that.sendId &&
                recieveId == that.recieveId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sendId, recieveId);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", sendId=" + sendId +
                ", recieveId=" + recieveId +
                '}';
    }
}
