package com.autumnia.shop.userservice.repository;

import com.autumnia.shop.userservice.domain.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// 미사용
@Repository
public class UserRepositoryXXX {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 4;

    static {
        users.add( new User(1, "김가을", new Date(), "pass1", "700823-1111111") );
        users.add( new User(2, "최영선", new Date(), "pass2", "700823-2222222") );
        users.add( new User(3, "김수민", new Date(), "pass3", "700823-3333333") );
        users.add( new User(4, "김해든", new Date(), "pass4", "700823-4444444") );
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id ) {
        for ( User user : users) {
            if ( user.getId() == id ) {
                return user;
            }
        }
        return null;
    }

    public User create(User user) {
        if ( user.getId() == null ) {
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User update(User updateUser)  {
        Iterator<User> iterator = users.iterator();

        while( iterator.hasNext() ) {
            User beforeUser = iterator.next();
            if ( beforeUser.getId() == updateUser.getId() ) {
                users.set(beforeUser.getId()-1, updateUser);

                return findOne(updateUser.getId());
            }
        }

        return findOne(updateUser.getId());
    }

    public User delete(int id ) {
        Iterator<User> iterator = users.iterator();

        while( iterator.hasNext() ) {
            User user = iterator.next();
            if ( user.getId() == id ) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
