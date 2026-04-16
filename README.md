# 🎮 EpiScore — Backend Java + Supabase

Plateforme de leaderboard gaming — TP ESGI B2 Java OOP

## Setup

### 1. Configurer la base de données
Renseigner vos credentials dans `DatabaseConnection.java` :
```java
private static final String URL      = "jdbc:postgresql://[HOST]:5432/postgres";
private static final String USER     = "postgres";
private static final String PASSWORD = "[PASSWORD]";
```

### 2. Créer les tables
Exécuter le script SQL dans le Supabase SQL Editor :
```sql
CREATE TABLE IF NOT EXISTS players (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL,
  level INT DEFAULT 1, xp INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS games (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  title VARCHAR(100) NOT NULL, genre VARCHAR(50) NOT NULL,
  max_players INT DEFAULT 4, is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT NOW()
);
CREATE TABLE IF NOT EXISTS scores (
  id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
  player_id UUID REFERENCES players(id),
  game_id UUID REFERENCES games(id),
  points INT NOT NULL, duration_seconds INT DEFAULT 0,
  played_at TIMESTAMP DEFAULT NOW()
);
```

### 3. Lancer le projet
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.esgi.episcore.Main"
```

## Architecture

```
com.esgi.episcore/
├── config/       DatabaseConnection.java
├── util/         AppLogger.java · Page.java
├── player/       model · dto · dao · service · seeder · exception
├── game/         model · dto · dao · service · seeder · exception
└── score/        model · dto · dao · service · seeder · exception
```

## Groupes

| Groupe | Entité | Volume |
|--------|--------|--------|
| Groupe 1 🔵 | Player | 500 joueurs |
| Groupe 2 🟢 | Game   | 50 jeux     |
| Groupe 3 🟠 | Score  | 2000 scores |
