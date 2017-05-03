package VMS.model.postenv;

import java.util.ArrayList;

public class Response
{
  private int count;

  public int getCount() { return this.count; }

  public void setCount(int count) { this.count = count; }

  private ArrayList<Item> items;

  public ArrayList<Item> getItems() { return this.items; }

  public void setItems(ArrayList<Item> items) { this.items = items; }
}