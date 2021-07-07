package com.memory.dao;

import com.memory.pojo.Friend;
import com.memory.pojo.FriendRequest;

import java.util.List;

/**
 * @InterfaceName FriendRequestDAO
 * @Description TODO
 * @Author SupreacyXXXXX
 * @Date 2020/4/26
 * @Version 1.0
 **/
public interface FriendRequestDAO {
    void add(FriendRequest friendRequest);
    void update(FriendRequest friendRequest);
    void delete(FriendRequest friendRequest);
    List<FriendRequest> getBySendID(int sendId);
    List<FriendRequest> getByRecieveID(int recieveId);
    FriendRequest getByBoth(int sendId, int recieveId);
}
