package org.example.parampammusic.repository;

import org.example.parampammusic.entity.AudioTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioTrackRepository extends JpaRepository<AudioTrack, Integer> {
}
