package com.marioszou.android.bakethat.Models;

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
public class Ingredient {

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
   *
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

}
