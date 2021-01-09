@import com.auo.generator.Game;import com.auo.generator.template.IndexPage;import java.time.LocalDate;import java.time.format.DateTimeFormatter

@param IndexPage indexPage

@tag.layout(
    page = indexPage,
    content = @`
    <h1>Score</h1>

    @for(LocalDate month : indexPage.months)
        <h2>${month.format(DateTimeFormatter.ofPattern("yyyy-MM"))}</h2>
        @for(Game game: indexPage.gamesPerMonth.get(month))
            <div>
                <h3>${game.dateTime.toString()}</h3>
                <p>${game.getComment()}</p>
                <table>
                    <tr>
                    @for(String player: game.players())
                        <td>${player}</td>
                    @endfor
                    </tr>
                @for(int i = 0; i < 7; i++)
                    <tr>
                    @for(String player: game.players())
                        <td>${game.score.get(player).get(i)}</td>
                    @endfor
                    </tr>
                @endfor
                </table>
            </div>
        @endfor
        <h3>Summary</h3>
        <table>
            <tr>
                <td></td>
                <td>wins</td>
                <td>exits</td>
                <td>won month</td>
            </tr>
            @for(IndexPage.MonthlyScore score : indexPage.monthlyScore.get(month))
            <tr>
                <td>${score.player}</td>
                <td>${score.wins}</td>
                <td>${score.exits}</td>
                <td>@if(score.monthlyWinner) 🍰🎂🎉 @endif </td>
            </tr>
            @endfor
        </table>



    @endfor
    `
)