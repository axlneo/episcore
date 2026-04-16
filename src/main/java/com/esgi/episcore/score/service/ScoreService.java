package com.esgi.episcore.score.service;

import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.dto.ScoreDTO;
import com.esgi.episcore.util.Page;

import java.util.List;
import java.util.UUID;

public interface ScoreService {
    ScoreDTO        addScore(CreateScoreDTO dto);
    ScoreDTO        getById(UUID id);
    Page<ScoreDTO>  getAll(int page, int size);
    Page<ScoreDTO>  getScoresForPlayer(UUID playerId, int page, int size);
    List<ScoreDTO>  getLeaderboard(UUID gameId, int limit);
    void            printStats();
}
