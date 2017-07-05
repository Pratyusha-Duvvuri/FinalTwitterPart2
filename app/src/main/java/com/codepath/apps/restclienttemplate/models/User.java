package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pratyusha98 on 6/26/17.
 */

public class User implements Parcelable{

    // list the attributes

    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public String tagLine;
    public int followersCount;
    public int followingCount;

    // deserialize the JSON
    public static User fromJSON(JSONObject json) throws JSONException{

        User user = new User();

        //extract and fill the values
        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.screenName= json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        user.tagLine = json.getString("description");
        user.followersCount = json.getInt("followers_count");
        user.followingCount = json.getInt("friends_count");


        return user;
    }
    public User(){}

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private User(Parcel in) {
        uid = in.readLong();

        name = in.readString();
        screenName = in.readString();

        profileImageUrl = in.readString();
        tagLine = in.readString();
        followersCount = in.readInt();
        followingCount =  in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeLong(uid);
        out.writeString(name);
        out.writeString(screenName);
        out.writeString(profileImageUrl);
        out.writeString(tagLine);
        out.writeInt(followersCount);
        out.writeInt(followingCount);

    }
}
