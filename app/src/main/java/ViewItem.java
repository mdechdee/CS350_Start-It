public class ViewItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public ViewItem(int img, String tx1, String tx2){
        mImageResource = img;
        mText1 = tx1;
        mText2 = tx2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
