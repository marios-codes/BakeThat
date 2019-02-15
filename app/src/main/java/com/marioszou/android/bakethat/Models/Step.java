package com.marioszou.android.bakethat.Models;

import android.os.Parcel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* example of Steps JSON Array
[
         {
            "id":0,
            "shortDescription":"Recipe Introduction",
            "description":"Recipe Introduction",
            "videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
            "thumbnailURL":""
         },
         {
            "id":1,
            "shortDescription":"Starting prep",
            "description":"1. Preheat the oven to 350\u00b0F. Butter a 9\" deep dish pie pan.",
            "videoURL":"",
            "thumbnailURL":""
         }
  ]
 */

public class Step implements android.os.Parcelable {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("shortDescription")
  @Expose
  private String shortDescription;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("videoURL")
  @Expose
  private String videoURL;
  @SerializedName("thumbnailURL")
  @Expose
  private String thumbnailURL;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVideoURL() {
    return videoURL;
  }

  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
  }

  /*
  Parcelable implementation
  */

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.id);
    dest.writeString(this.shortDescription);
    dest.writeString(this.description);
    dest.writeString(this.videoURL);
    dest.writeString(this.thumbnailURL);
  }

  public Step() {
  }

  protected Step(Parcel in) {
    this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    this.shortDescription = in.readString();
    this.description = in.readString();
    this.videoURL = in.readString();
    this.thumbnailURL = in.readString();
  }

  public static final Creator<Step> CREATOR = new Creator<Step>() {
    @Override
    public Step createFromParcel(Parcel source) {
      return new Step(source);
    }

    @Override
    public Step[] newArray(int size) {
      return new Step[size];
    }
  };
}
