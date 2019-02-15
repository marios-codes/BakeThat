package com.marioszou.android.bakethat.Models;

import android.os.Parcel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* example of ingredients JSON Array
[
         {
            "quantity":2,
            "measure":"CUP",
            "ingredient":"Graham Cracker crumbs"
         },
         {
            "quantity":6,
            "measure":"TBLSP",
            "ingredient":"unsalted butter, melted"
         }
 ]
 */
public class Ingredient implements android.os.Parcelable {

  @SerializedName("quantity")
  @Expose
  private Double quantity;
  @SerializedName("measure")
  @Expose
  private String measure;
  @SerializedName("ingredient")
  @Expose
  private String ingredient;

  /**
   * No args constructor for use in serialization
   */
  public Ingredient() {
  }


  public Double getQuantity() {
    return quantity;
  }

  public void setQuantity(Double quantity) {
    this.quantity = quantity;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public String getIngredient() {
    return ingredient;
  }

  public void setIngredient(String ingredient) {
    this.ingredient = ingredient;
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
    dest.writeValue(this.quantity);
    dest.writeString(this.measure);
    dest.writeString(this.ingredient);
  }

  protected Ingredient(Parcel in) {
    this.quantity = (Double) in.readValue(Double.class.getClassLoader());
    this.measure = in.readString();
    this.ingredient = in.readString();
  }

  public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
    @Override
    public Ingredient createFromParcel(Parcel source) {
      return new Ingredient(source);
    }

    @Override
    public Ingredient[] newArray(int size) {
      return new Ingredient[size];
    }
  };
}
