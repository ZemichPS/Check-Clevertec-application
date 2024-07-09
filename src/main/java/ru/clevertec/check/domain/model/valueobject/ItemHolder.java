package ru.clevertec.check.domain.model.valueobject;

import java.util.ArrayList;
import java.util.List;

public class ItemHolder {
    private final List<CheckItem> items = new ArrayList<>();

    public ItemHolder() {
    }

    public void addItem(CheckItem checkItem) {
        this.items.add(checkItem);
    }

    public List<CheckItem> getItems(){
        return List.copyOf(this.items);
    }

    public int getItemsCount(){
        return items.size();
    }
}
