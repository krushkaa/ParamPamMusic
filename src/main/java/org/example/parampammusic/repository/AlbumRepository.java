package org.example.parampammusic.repository;

import org.example.parampammusic.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

}