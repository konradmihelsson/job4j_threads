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
        return this.store.putIfAbsent(user.getId(), user) == null;
    }

    synchronized boolean update(User user) {
        return this.store.replace(user.getId(), user) != null;
    }

    synchronized boolean delete(User user) {
        return this.store.remove(user.getId(), user);
    }

    synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User fromUser = this.store.get(fromId);
        User toUser = this.store.get(toId);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            fromUser.setAmount(fromUser.getAmount() - amount);
            toUser.setAmount(toUser.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
