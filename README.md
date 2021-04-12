# Rummy score

Why write the scores in a notepad and potentially lose pages
when you can build a site generator and save the results in git?

I'm probably going to regret persisting this, since I suspect I won't win 😥

## Input

- One file per day, e.g. `2021-01-01.txt`
- Each file can contain multiple games
- Support comments of game with `#`
- First player is first to act. Last player is dealer for the first round.
- There must exist a newline between matches as well as at the end of the file.

```
# Text to add to template
HOUR:MINUTE
A	B	C
0	2	2
2	0	2
2	0	2
100	0	25
0	25	5
7	8	0
0	100	102

```
