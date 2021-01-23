
@import com.auo.generator.template.Page
@import gg.jte.Content


@param Page page
@param Content content
@param Content headContent = null

<!doctype html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>${page.title()}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
           html {
                font-family:verdana;
           }
           body {
                padding:10px;
           }
          table {
            width:100%;
            max-width:500px;
          }

          th, .player {
            font-weight:bold;
          }
          td, th {
            text-align:right;
          }
          .total-score {
            font-weight: bold;
          }
        </style>

        $unsafe{"<link rel=\"icon\" href=\"data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>🃏</text></svg>\" />"}
        @if (headContent != null) ${headContent} @endif

    </head>
    <body>
    ${content}
    </body>
</html>
