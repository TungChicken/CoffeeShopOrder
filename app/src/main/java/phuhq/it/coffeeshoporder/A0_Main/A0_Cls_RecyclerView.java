package phuhq.it.coffeeshoporder.A0_Main;

public class A0_Cls_RecyclerView {
    private String title;
    private int image;

    public A0_Cls_RecyclerView(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
