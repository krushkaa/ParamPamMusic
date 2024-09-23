package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    Optional<Album> findByTitle(String title);
}