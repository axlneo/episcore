package com.esgi.episcore.score.service;

import com.esgi.episcore.score.dao.ScoreDAO;
import com.esgi.episcore.score.dto.CreateScoreDTO;
import com.esgi.episcore.score.dto.ScoreDTO;
import com.esgi.episcore.score.exception.ScoreNotFoundException;
import com.esgi.episcore.util.AppLogger;
import com.esgi.episcore.util.Page;

import com.esgi.episcore.game.dao.GameDAO;

import com.esgi.episcore.player.dao.PlayerDAO;

import java.time.LocalDateTime;
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
    private final PlayerDAO playerDAO;
    private final GameDAO gameDAO;

    public ScoreServiceImpl(ScoreDAO scoreDAO, PlayerDAO playerDAO, GameDAO gameDAO) {
        if (scoreDAO == null) throw new IllegalArgumentException("scoreDAO ne peut pas être null");
        this.scoreDAO = scoreDAO;
        this.playerDAO = playerDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public ScoreDTO addScore(CreateScoreDTO dto) {

        // Vérifier player
        if (playerDAO.findById(dto.getPlayerId()) == null) {
            throw new RuntimeException("Player not found");
        }

        // Vérifier game
        if (gameDAO.findById(dto.getGameId()) == null) {
            throw new RuntimeException("Game not found");
        }

        // Sauvegarde
        ScoreDTO scoredto = scoreDAO.save(dto);

        // Calcul du rang
        int rank = 1;
        List<ScoreDTO> scores = scoreDAO.getTopScores(dto.getGameId(), 1000);

        for (ScoreDTO s : scores) {
            if (s.getPoints() > dto.getPoints()) {
                rank++;
            }
        }

        log.info("Score ajouté avec rang = " + rank);

        // Retour simple (sans enrichissement compliqué)
        return new ScoreDTO(
                scoredto.getId(),
                scoredto.getPlayerUsername(),
                scoredto.getGameTitle(),
                scoredto.getPoints(),
                scoredto.getDurationSeconds(),
                scoredto.getPlayedAt()
        );
    }

    @Override public ScoreDTO           getById(UUID id) { throw new UnsupportedOperationException("Not implemented."); };
    @Override public Page<ScoreDTO>     getAll(int page, int size) { throw new UnsupportedOperationException("Not implemented."); };;
    @Override public Page<ScoreDTO>     getScoresForPlayer(UUID playerId, int page, int size) { throw new UnsupportedOperationException("Not implemented."); };;
    @Override public List<ScoreDTO>     getLeaderboard(UUID gameId, int limit) { throw new UnsupportedOperationException("Not implemented."); };;
    @Override public void               printStats() { throw new UnsupportedOperationException("Not implemented."); };;

}