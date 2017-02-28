package com.queen.sandbox.algorithms.views.dispatch;

import com.queen.sandbox.algorithms.models.quickfind.Person;
import com.queen.sandbox.algorithms.views.action.*;
import com.queen.sandbox.algorithms.views.store.Store;
import javafx.collections.ObservableSet;

import java.util.LinkedList;
import java.util.List;

public class Dispatcher {
    private static Dispatcher instance = new Dispatcher();

    private List<Store> stores = new LinkedList<>();

    private Dispatcher() {}

    public static Dispatcher getInstance() {
        return instance;
    }

    public void registerStore(Store store) {
        stores.add(store);
    }

    public void mouseMoved(MouseMoved mouseMoved) {
        dispatchAction(new MouseMovedAction(mouseMoved));
    }

    public void mouseEntered(Person person) {
        dispatchAction(new MouseEnteredAction(new MouseEntered(person)));
    }

    public void mouseExited(Person person, ObservableSet<Integer> pressedRectangles) {
        dispatchAction(new MouseExitedAction(new MouseExited(person, pressedRectangles)));
    }

    public void mousePressed(Person person, ObservableSet<Integer> pressedRectangles) {
        dispatchAction(new MousePressedAction(new MousePressed(person, pressedRectangles)));
    }
//    public void menuItemSelected(MenuItem menuItem) {
//        dispatchAction(new MenuAction(menuItem));
//        switch (menuItem) {
//            case HOME:
//            case PRODUCTS:
//            default:
//                dispatchAction(new ContentAction(Content.PRODUCTS));
//                break;
//            case COMPANY:
//                dispatchAction(new ContentAction(Content.COMPANY));
//                break;
//        }
//    }

    private void dispatchAction(Action action) {
        stores.stream().forEach(store -> store.onAction(action));
    }
}
