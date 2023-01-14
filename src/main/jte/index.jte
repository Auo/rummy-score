@import com.auo.generator.Game
@import com.auo.generator.template.IndexPage
@import gg.jte.support.ForSupport
@import java.time.LocalDate
@import java.time.format.DateTimeFormatter
@import java.util.Comparator
@import java.util.List
@import java.util.stream.Collectors
@import gg.jte.support.ForSupport

@param IndexPage indexPage

@tag.layout(
    page = indexPage,
    content = @`
    <h1>Score</h1>

    @for(LocalDate month : indexPage.months)
        <details>
        <summary><h2>${month.format(DateTimeFormatter.ofPattern("yyyy-MM"))}</h2></summary>

            @for(Game game: indexPage.gamesPerMonth.get(month))
                <div>
                    <h3>${game.dateTime.toString()}</h3>
                    <p>${game.getComment()}</p>
                    <table>
                        <tr>
                            @for(String player: game.players())
                                <th>${player}</th>
                            @endfor
                        </tr>
                        @for(int i = 0; i <= 7; i++)

                            @if(i < 7)
                                <tr>
                                    @for(String player: game.players())
                                        <td>${game.score.get(player).get(i)}</td>
                                    @endfor
                                </tr>
                            @else
                                <tr>
                                    @for(String player: game.players())
                                        <td class="total-score">${game.score.get(player).stream().reduce(0, Integer::sum)}</td>
                                    @endfor
                                </tr>
                            @endif
                        @endfor
                    </table>
                </div>
            @endfor
        </details>
        <h3>Summary ${month.format(DateTimeFormatter.ofPattern("yyyy-MM"))}</h3>
        <table>
            <tr>
                <th></th>
                <th></th>
                <th>wins</th>
                <th>exits</th>
                <th>total points</th>
                <th>won month</th>
            </tr>

            !{List<IndexPage.MonthlyScore> sortedScores = indexPage.monthlyScore.get(month).stream().sorted(IndexPage.SCORE_COMPARATOR.reversed()).collect(Collectors.toList())}
            @for(ForSupport<IndexPage.MonthlyScore> entry : ForSupport.of(sortedScores))
            !{var score = entry.get(); }
            <tr>
                <td>${entry.getIndex() + 1}</td>
                <td class="player">${score.player}</td>
                <td>${score.wins}</td>
                <td>${score.exits}</td>
                <td>${score.totalPoints}</td>
                <td>@if(score.monthlyWinner) 🍰🎂🎉 @endif </td>
            </tr>
            @endfor
        </table>



    @endfor
    `
)