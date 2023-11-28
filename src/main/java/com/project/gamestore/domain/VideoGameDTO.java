package com.project.gamestore.domain;

public record VideoGameDTO(int id, String name, String description, String released, String background_image, double rating, int playtime, String esrb_rating) {
}
