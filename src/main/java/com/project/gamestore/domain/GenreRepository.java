package com.project.gamestore.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Integer> {

    @Query("select g from Genre g order by g.popularity")
    public List<Genre> getGenresByPopularity();

}
