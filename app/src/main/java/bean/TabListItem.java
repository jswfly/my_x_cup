package bean;

/**
 * Created by 殇痕 on 2017/3/13.
 */

public class TabListItem {
    private String itemName;
    private int page;

    public TabListItem(String itemName, int page){
        this.itemName = itemName;
        this.page = page;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
