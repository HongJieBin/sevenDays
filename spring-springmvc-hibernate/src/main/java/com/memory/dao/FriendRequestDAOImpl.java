package com.memory.dao;

import com.memory.pojo.Friend;
import com.memory.pojo.FriendRequest;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName FriendRequestDAOImpl
 * @Description TODO
 * @Author SupreacyXXXXX
 * @Date 2020/4/26
 * @Version 1.0
 **/

@Repository
@Transactional
public class FriendRequestDAOImpl implements FriendRequestDAO{
    @Resource
    private HibernateTemplate hibernateTemplate;

    @Override
    public void add(FriendRequest friendRequest) {
        hibernateTemplate.save(friendRequest);
    }

    @Override
    public void update(FriendRequest friendRequest) {
        hibernateTemplate.update(friendRequest);
    }

    @Override
    public void delete(FriendRequest friendRequest) {
        hibernateTemplate.delete(friendRequest);
    }

    @Override
    public List<FriendRequest> getBySendID(int sendId) {
        String hql = "from FriendRequest as f where f.sendId = ?";
        return (List<FriendRequest>) hibernateTemplate.find(hql, sendId);
    }

    @Override
    public List<FriendRequest> getByRecieveID(int recieveId) {
        String hql = "from FriendRequest as f where f.recieveId = ?";
        return (List<FriendRequest>) hibernateTemplate.find(hql, recieveId);
    }

    @Override
    public FriendRequest getByBoth(int sendId, int recieveId) {
        String hql = "from FriendRequest as f where f.sendId = ? and f.recieveId = ?";
        return (FriendRequest) hibernateTemplate.find(hql, new Integer[]{sendId, recieveId});
    }
}
