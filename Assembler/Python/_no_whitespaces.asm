(RESTART)
@SCREEN
D=A
@0
(KBDCHECK)
@KBD
D=M
@BLACK
@WHITE
@KBDCHECK
0;JMP
(BLACK)
@1
@CHANGE
0;JMP
(WHITE)
@1
@CHANGE
0;JMP
(CHANGE)
@0
@0
@KBD
@0
A=M
@CHANGE
@RESTART
0;JMP