package praktikum.order;

import java.util.List;

public class IngredientList {


    private List<String> ingredients;

    public IngredientList(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientList() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}