/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cinematicketingsystem;
    import java.util.Vector;
/**
 *
 * @author hp
 */
public class Movie {



    private String title;
    private String director;
    private int releaseYear;

    // Constructor
    public Movie(String title, String director, int releaseYear) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    // Main method to create a test Vector
    public static void main(String[] args) {
        // Create a Vector to store Movie objects
        Vector<Movie> data = new Vector<>();

        // Add three random movies to the Vector
        data.add(new Movie("Inception", "Christopher Nolan", 2010));
        data.add(new Movie("The Shawshank Redemption", "Frank Darabont", 1994));
        data.add(new Movie("Pulp Fiction", "Quentin Tarantino", 1994));

}
}
