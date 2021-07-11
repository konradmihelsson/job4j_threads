package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, User> store = new ConcurrentHashMap<>();

    synchronized boolean add(User user) {
        return user.equals(this.store.put(user.getId(), user));
    }

    synchronized boolean update(User user) {
        return user.equals(this.store.replace(user.getId(), user));
    }

    synchronized boolean delete(User user) {
        return user.equals(this.store.remove(user.getId()));
    }

    synchronized void transfer(int fromId, int toId, int amount) {
        User fromUser = this.store.get(fromId);
        User toUser = this.store.get(toId);
        fromUser.setAmount(fromUser.getAmount() - amount);
        toUser.setAmount(toUser.getAmount() + amount);
    }
}
