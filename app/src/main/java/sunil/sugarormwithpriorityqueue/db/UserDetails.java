package sunil.sugarormwithpriorityqueue.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class UserDetails extends SugarRecord implements Parcelable, Comparable<UserDetails> {

    @Unique
    Long id;
    String name;
    private Integer mSequence;

    public void setSequence(Integer val) {
        this.mSequence = val;
    }

    @Override
    public int compareTo(UserDetails other) {
        return this.mSequence - other.mSequence;
    }


    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public UserDetails() {
    }

    protected UserDetails(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };
}
