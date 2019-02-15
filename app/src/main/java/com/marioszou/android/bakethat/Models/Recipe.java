package com.marioszou.android.bakethat.Models;

import android.os.Parcel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* example of Recipes JSON Array

[
   {
      "id":1,
      "name":"Nutella Pie",
      "ingredients":[  ],
      "steps":[  ],
      "servings":8,
      "image":""
   },
   {
      "id":2,
      "name":"Brownies",
      "ingredients":[  ],
      "steps":[  ],
      "servings":8,
      "image":""
   }
 ]
 */

public class Recipe implements android.os.Parcelable {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("ingredients")
  @Expose
  private List<Ingredient> ingredients = null;
  @SerializedName("steps")
  @Expose
  private List<Step> steps = null;
  @SerializedName("servings")
  @Expose
  private Integer servings;
  @SerializedName("image")
  @Expose
  private String image;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  public Integer getServings() {
    return servings;
  }

  public void setServings(Integer servings) {
    this.servings = servings;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
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
    dest.writeString(this.name);
    dest.writeList(this.ingredients);
    dest.writeList(this.steps);
    dest.writeValue(this.servings);
    dest.writeString(this.image);
  }

  public Recipe() {
  }

  protected Recipe(Parcel in) {
    this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    this.name = in.readString();
    this.ingredients = new ArrayList<Ingredient>();
    in.readList(this.ingredients, Ingredient.class.getClassLoader());
    this.steps = new ArrayList<Step>();
    in.readList(this.steps, Step.class.getClassLoader());
    this.servings = (Integer) in.readValue(Integer.class.getClassLoader());
    this.image = in.readString();
  }

  public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
    @Override
    public Recipe createFromParcel(Parcel source) {
      return new Recipe(source);
    }

    @Override
    public Recipe[] newArray(int size) {
      return new Recipe[size];
    }
  };
}
