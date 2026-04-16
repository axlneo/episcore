package com.esgi.episcore.score.dao;

import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.dto.ScoreDTO;
import com.esgi.episcore.util.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScoreDAO {
    ScoreDTO  save(CreateScoreDTO dto);
    Optional<ScoreDTO>  findById(UUID id);
    Page<ScoreDTO>      findAll(int page, int size);
    Page<ScoreDTO>      findByPlayerId(UUID playerId, int page, int size);
    Page<ScoreDTO>      findByGameId(UUID gameId, int page, int size);
    /** Top N scores pour un jeu donné, triés par points DESC. */
    List<ScoreDTO>      getTopScores(UUID gameId, int limit);
    long                count();
}
