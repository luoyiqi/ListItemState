package ch.shibastudio.listitemstate;

/**
 * Created by shibakaneki on 16.11.13.
 */
public class StatedListItem {
    public String label;
    public boolean checked;

    public StatedListItem(){
        label = new String("");
        checked = false;
    }

    public StatedListItem(String l, boolean b){
        label = l;
        checked = b;
    }
}
