package com.auo.generator;

import java.time.LocalDateTime;
import java.util.*;

public class Game {
    private static final int NUMBER_OF_ROUNDS = 7;
    public LocalDateTime dateTime;
    public StringBuilder commentBuilder;
    // LinkedHashMap because order is important.
    private LinkedHashMap<String, ArrayList<Integer>> score;

    public void result(String player, int playerScore) {
        if (score == null) {
            score = new LinkedHashMap<>();
        }

        score.compute(player, (key, value) -> {
            if (value == null) {
                value = new ArrayList<>(7);
            }
            value.add(playerScore);
            return value;
        });
    }

    public Set<String> players() {
        return score.keySet();
    }

    public String gameExit() {
        Optional<Map.Entry<String, ArrayList<Integer>>> exit = score.entrySet().stream()
                .filter(e -> e.getValue().get(NUMBER_OF_ROUNDS - 1) == 0)
                .findFirst();
        if (exit.isPresent()) {
            return exit.get().getKey();
        }

        throw new IllegalArgumentException("Invalid game.. missing a player with 0 for last round");
    }

    public void dateTime(String raw) {
        dateTime = LocalDateTime.parse(raw);
    }

    public void comment(String line) {
        if (commentBuilder == null) {
            commentBuilder = new StringBuilder();
        }

        commentBuilder.append(line);
    }

    public String getComment() {
        if (commentBuilder == null) {
            return "";
        }

        return commentBuilder.toString();
    }

    public String gameWinner() {
        int smallest = Integer.MAX_VALUE;
        String winner = "";

        for (Map.Entry<String, ArrayList<Integer>> entry : score.entrySet()) {
            int score = entry.getValue().stream().reduce(0, Integer::sum);
            boolean wonLast = entry.getValue().get(NUMBER_OF_ROUNDS - 1) == 0;

            if (smallest == score && wonLast) {
                smallest = score;
                winner = entry.getKey();
            } else if (smallest > score) {
                smallest = score;
                winner = entry.getKey();
            }
        }

        return winner;
    }

}

