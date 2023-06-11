package assignment2.assignment2.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private String name;
    private int weight;
    private int height;
    private String hair_color;
    private float gpa;
    private String favorite_food;

    public User() {
    }

    public User(String name, int weight, int height, String hair_color, float gpa, String favorite_food) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.hair_color = hair_color;
        this.gpa = gpa;
        this.favorite_food = favorite_food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHair_color() {
        return hair_color;
    }

    public void setHair_color(String hair_color) {
        this.hair_color = hair_color;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getfavorite_food() {
        return favorite_food;
    }

    public void setfavorite_food(String favorite_food) {
        this.favorite_food = favorite_food;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
