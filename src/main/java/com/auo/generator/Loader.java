package com.auo.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private final File dir;

    public Loader(File dir) {
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Path: " + dir.getAbsolutePath() + " is not a directory");
        }

        this.dir = dir;
    }

    public List<Game> load() throws IOException {
        File[] days = dir.listFiles((dir, name) -> name.matches("^[0-9]{4}-[0-2]{2}-[0-9]{2}.txt$"));
        if (days == null || days.length == 0) {
            return List.of();
        }

        List<Game> games = new ArrayList<>();

        for (File day : days) {
            Game game = new Game();

            try (BufferedReader reader = new BufferedReader(new FileReader(day))) {
                String[] players = null;
                while (reader.ready()) {
                    String line = reader.readLine();

                    if (line.isEmpty()) {
                        games.add(game);
                        game = new Game();
                        continue;
                    }

                    if (line.startsWith("#")) {
                        game.comment(line.substring(1));
                        continue;
                    }

                    String[] columns = line.split("\t");

                    if (columns.length == 1) {
                        game.dateTime(day.getName().replace(".txt", ("T" + columns[0])));
                        continue;
                    }

                    if (!columns[0].chars().allMatch(Character::isDigit)) {
                        players = columns;
                        continue;
                    }

                    if (players == null) {
                        throw new IOException("Invalid file format: " + day.getPath());
                    }

                    for (int i = 0; i < columns.length; i++) {
                        game.result(players[i], Integer.parseInt(columns[i]));
                    }
                }
            }
        }

        return games;
    }
}
