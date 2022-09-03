package ru.yandex.practicum.entity;

public class Order {

    String[] ingredients;


    public Order(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredients(String[] ingredients){
        this.ingredients = ingredients;
    }

    public String[] getIngredients(){
        return ingredients;
    }

}
