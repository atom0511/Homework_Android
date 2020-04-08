package trungatom.tqt.lesson5.models;

public class ItemModel {
    private int position;
    private int mId;
    private String mImage;
    private String mTitle;
    private String mDescription;
    private String mAuthor;

    public ItemModel(int mId, String mImage, String mTitle, String mDescription, String mAuthor) {
        this.mId = mId;
        this.mImage = mImage;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mAuthor = mAuthor;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}