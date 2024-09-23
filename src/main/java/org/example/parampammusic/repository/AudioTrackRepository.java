package org.example.parampammusic.repository;

import org.example.parampammusic.entity.AudioTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioTrackRepository extends JpaRepository<AudioTrack, Integer> {
    @Query("SELECT a.title FROM AudioTrack a WHERE a.id = :audioTrackId")
    String getAudioTrackName(@Param("audioTrackId") int audioTrackId);

//    List<AudioTrack> findByOrder_User_IdAndOrder_Status(int userId, OrderStatus status);

}
