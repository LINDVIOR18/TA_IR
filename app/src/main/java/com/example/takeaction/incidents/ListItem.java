package com.example.takeaction.incidents;
public class ListItem {

    private String head;
    private String desc;
    private String adr;

    public ListItem(String head, String desc, String adr) {
        this.head = head;
        this.desc = desc;
        this.adr = adr;
    }

    public String getAdr() {
        return adr;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}
