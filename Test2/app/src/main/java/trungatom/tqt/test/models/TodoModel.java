package trungatom.tqt.test.models;

public class TodoModel {
    int mId;
    String mTitle;
    String mContent;
    String mTag;
    String mShowTime;

    public TodoModel(int mId, String mTitle, String mContent, String mTag, String mShowTime) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mTag = mTag;
        this.mShowTime = mShowTime;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmTag() {
        return mTag;
    }

    public void setmTag(String mTag) {
        this.mTag = mTag;
    }

    public String getmShowTime() {
        return mShowTime;
    }

    public void setmShowTime(String mShowTime) {
        this.mShowTime = mShowTime;
    }

    @Override
    public String toString() {
        return "TodoModel{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mTag='" + mTag + '\'' +
                ", mShowTime='" + mShowTime + '\'' +
                '}' + "\n";
    }

}
