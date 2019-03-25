package app.coalery.wordsshuffler;

public class DBdata {

    private int ID;
    private String English;
    private String Korean;
    private int Page;

    public DBdata(int ID, String English, String Korean, int Page) {
        this.ID = ID;
        this.English = English;
        this.Korean = Korean;
        this.Page = Page;
    }

    public int getID() { return ID; }
    public String getEnglish() { return English; }
    public String getKorean() { return Korean; }
    public int getPage() { return Page; }

    public void setID(int ID) { this.ID = ID; }
    public void setEnglish(String english) { English = english; }
    public void setKorean(String korean) { Korean = korean; }
    public void setPage(int page) { Page = page; }

    public String toString() { return ID + " / " + English + " : " + Korean + " / " + Page + "p"; }
}
