package com.auo.generator.template;

import com.auo.generator.Game;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class IndexPage extends Page {
    private static final TemporalAdjuster MONTH = TemporalAdjusters.firstDayOfMonth();
    private static final Comparator<MonthlyScore> SCORE_COMPARATOR;

    static {
        Comparator<MonthlyScore> comparing = Comparator.comparing(s -> s.wins);
        SCORE_COMPARATOR = comparing.thenComparing(s -> s.exits);
    }

    public final List<LocalDate> months;
    public final Map<LocalDate, List<Game>> gamesPerMonth;
    public final Map<LocalDate, List<MonthlyScore>> monthlyScore = new LinkedHashMap<>();

    public IndexPage(List<Game> games) {
        this.gamesPerMonth = games.stream()
                .collect(Collectors.groupingBy(game -> game.dateTime.toLocalDate().with(MONTH)));
        this.gamesPerMonth.forEach((k, v) -> v.sort(Comparator.comparing(game -> game.dateTime)));

        this.months = gamesPerMonth.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        for (LocalDate month : months) {
            List<Game> monthlyGames = gamesPerMonth.get(month);
            Map<String, MonthlyScore> score = new HashMap<>();

            for (Game game : monthlyGames) {
                String winner = game.gameWinner();
                String exit = game.gameExit();

                for (String player : game.players()) {
                    score.compute(player, (key, value) -> {
                        if (value == null) {
                            value = new MonthlyScore(player);
                        }
                        value.incGames();

                        if (winner.equals(player)) {
                            value.incWins();
                        }

                        if (exit.equals(player)) {
                            value.incExits();
                        }

                        return value;
                    });
                }
            }

            monthlyScore.put(month, new ArrayList<>(score.values()));
            LocalDateTime now = LocalDateTime.now().with(MONTH);

            if (now.getYear() != month.getYear() && now.getMonth() != month.getMonth()) {
                Optional<MonthlyScore> winner = score.values().stream().max(SCORE_COMPARATOR);
                winner.ifPresent(value -> value.monthlyWinner = true);
            }
        }
    }

    public static class MonthlyScore {
        public final String player;
        public int wins = 0;
        public int games = 0;
        public int exits = 0;

        public boolean monthlyWinner = false;

        public MonthlyScore(String player) {
            this.player = player;
        }

        void incWins() {
            wins++;
        }

        void incGames() {
            games++;
        }

        void incExits() {
            exits++;
        }
    }
}
