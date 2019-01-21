package com.marioszou.android.bakethat.Network;

import com.marioszou.android.bakethat.Models.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesNetworkService {

  @GET("/topher/2017/May/59121517_baking/baking.json")
  Call<List<Recipe>> getRecipes();
}
