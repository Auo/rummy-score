@import com.auo.generator.template.IndexPage;import java.time.LocalDate;import java.time.format.DateTimeFormatter

@param IndexPage indexPage

@tag.layout(
    page = indexPage,
    content = @`
    <h1>Score</h1>

    @for(LocalDate month : indexPage.months)
        <h2>${month.format(DateTimeFormatter.ofPattern("yyyy-MM"))}</h2>

        <table>
            <tr>
                <td>player</td>
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