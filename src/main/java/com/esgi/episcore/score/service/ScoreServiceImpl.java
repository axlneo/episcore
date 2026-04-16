package com.esgi.episcore.score.service;

import com.esgi.episcore.score.dao.ScoreDAO;
import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.dto.ScoreDTO;
import com.esgi.episcore.score.exception.ScoreNotFoundException;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * TODO Groupe 3 :
 *   addScore()     → valider que playerId et gameId existent (appeler les autres DAOs !)
 *                    Lever InvalidScoreException si game inactive
 *   getLeaderboard()→ déléguer à scoreDAO.getTopScores()
 *   printStats()   → count total, top 5 tous jeux confondus
 */
public class ScoreServiceImpl implements ScoreService {

    private static final Logger log = AppLogger.getLogger(ScoreServiceImpl.class);

    private final ScoreDAO scoreDAO;

    public ScoreServiceImpl(ScoreDAO scoreDAO) {
        if (scoreDAO == null) throw new IllegalArgumentException("scoreDAO ne peut pas être null");
        this.scoreDAO = scoreDAO;
    }

    @Override public ScoreDTO        addScore(CreateScoreDTO dto)                   { throw new UnsupportedOperationException("À implémenter"); }
    @Override public ScoreDTO        getById(UUID id)                               { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<ScoreDTO>  getAll(int page, int size)                     { throw new UnsupportedOperationException("À implémenter"); }
    @Override public Page<ScoreDTO>  getScoresForPlayer(UUID playerId, int p, int s){ throw new UnsupportedOperationException("À implémenter"); }
    @Override public List<ScoreDTO>  getLeaderboard(UUID gameId, int limit)         { throw new UnsupportedOperationException("À implémenter"); }
    @Override public void            printStats()                                   { throw new UnsupportedOperationException("À implémenter"); }
}
