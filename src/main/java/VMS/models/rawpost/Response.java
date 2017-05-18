package vms.models.rawpost;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by magic on 04.05.2017.
 */
public class Response
{
    @JsonProperty("count")
    private int count;

    public int getCount() { return this.count; }

    public void setCount(int count) { this.count = count; }

    @JsonProperty("items")
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() { return this.items; }

    public void setItems(ArrayList<Item> items) { this.items = items; }
}