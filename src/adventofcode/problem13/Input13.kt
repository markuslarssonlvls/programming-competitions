package adventofcode.problem13

const val input13 = "1,380,379,385,1008,2319,769929,381,1005,381,12,99,109,2320,1101,0,0,383,1102,1,0,382,20101,0,382,1,20101,0,383,2,21102,1,37,0,1105,1,578,4,382,4,383,204,1,1001,382,1,382,1007,382,42,381,1005,381,22,1001,383,1,383,1007,383,20,381,1005,381,18,1006,385,69,99,104,-1,104,0,4,386,3,384,1007,384,0,381,1005,381,94,107,0,384,381,1005,381,108,1105,1,161,107,1,392,381,1006,381,161,1101,-1,0,384,1105,1,119,1007,392,40,381,1006,381,161,1101,1,0,384,21001,392,0,1,21102,18,1,2,21102,0,1,3,21102,138,1,0,1105,1,549,1,392,384,392,21001,392,0,1,21102,1,18,2,21102,3,1,3,21101,161,0,0,1106,0,549,1101,0,0,384,20001,388,390,1,20101,0,389,2,21102,180,1,0,1105,1,578,1206,1,213,1208,1,2,381,1006,381,205,20001,388,390,1,21001,389,0,2,21102,1,205,0,1105,1,393,1002,390,-1,390,1102,1,1,384,21001,388,0,1,20001,389,391,2,21102,228,1,0,1106,0,578,1206,1,261,1208,1,2,381,1006,381,253,20102,1,388,1,20001,389,391,2,21101,253,0,0,1106,0,393,1002,391,-1,391,1101,0,1,384,1005,384,161,20001,388,390,1,20001,389,391,2,21101,0,279,0,1105,1,578,1206,1,316,1208,1,2,381,1006,381,304,20001,388,390,1,20001,389,391,2,21101,304,0,0,1105,1,393,1002,390,-1,390,1002,391,-1,391,1102,1,1,384,1005,384,161,20102,1,388,1,20101,0,389,2,21102,0,1,3,21101,0,338,0,1105,1,549,1,388,390,388,1,389,391,389,20101,0,388,1,20101,0,389,2,21102,1,4,3,21101,0,365,0,1106,0,549,1007,389,19,381,1005,381,75,104,-1,104,0,104,0,99,0,1,0,0,0,0,0,0,207,19,15,1,1,21,109,3,22101,0,-2,1,22101,0,-1,2,21102,0,1,3,21101,0,414,0,1106,0,549,22101,0,-2,1,22101,0,-1,2,21101,0,429,0,1106,0,601,2101,0,1,435,1,386,0,386,104,-1,104,0,4,386,1001,387,-1,387,1005,387,451,99,109,-3,2105,1,0,109,8,22202,-7,-6,-3,22201,-3,-5,-3,21202,-4,64,-2,2207,-3,-2,381,1005,381,492,21202,-2,-1,-1,22201,-3,-1,-3,2207,-3,-2,381,1006,381,481,21202,-4,8,-2,2207,-3,-2,381,1005,381,518,21202,-2,-1,-1,22201,-3,-1,-3,2207,-3,-2,381,1006,381,507,2207,-3,-4,381,1005,381,540,21202,-4,-1,-1,22201,-3,-1,-3,2207,-3,-4,381,1006,381,529,22102,1,-3,-7,109,-8,2106,0,0,109,4,1202,-2,42,566,201,-3,566,566,101,639,566,566,1201,-1,0,0,204,-3,204,-2,204,-1,109,-4,2106,0,0,109,3,1202,-1,42,593,201,-2,593,593,101,639,593,593,21002,0,1,-2,109,-3,2105,1,0,109,3,22102,20,-2,1,22201,1,-1,1,21101,0,431,2,21101,0,400,3,21101,840,0,4,21101,630,0,0,1106,0,456,21201,1,1479,-2,109,-3,2105,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,2,2,2,2,2,0,2,0,2,2,0,0,0,2,2,0,0,2,0,2,0,2,2,0,2,0,2,2,0,2,0,2,2,2,2,0,2,0,0,1,1,0,0,0,0,2,2,2,2,2,2,2,2,0,2,2,2,0,2,0,0,2,0,2,2,0,0,0,2,2,2,0,0,0,2,0,0,0,0,2,0,1,1,0,0,2,2,2,0,0,2,0,2,0,0,2,0,0,2,2,0,2,2,0,0,0,2,2,2,0,0,0,0,2,0,0,0,2,0,2,2,0,0,1,1,0,0,0,2,0,2,0,0,0,0,0,0,0,0,2,2,2,2,0,0,2,2,0,0,2,2,2,0,0,0,2,0,0,0,0,2,2,0,2,0,1,1,0,2,0,0,2,0,0,2,2,0,0,2,0,0,2,2,0,2,0,0,2,0,2,2,0,2,0,2,2,0,2,0,2,2,0,0,0,0,0,0,1,1,0,2,2,0,0,0,0,0,0,0,0,2,0,2,0,0,0,0,0,2,2,2,2,2,0,0,2,0,2,0,0,2,2,2,2,0,0,0,0,0,1,1,0,2,0,2,2,0,2,2,0,0,2,0,2,0,2,0,0,0,0,2,0,2,2,2,0,2,0,0,2,0,0,0,0,0,2,0,0,0,0,0,1,1,0,0,0,2,0,2,0,0,2,0,2,0,0,0,0,0,2,0,2,2,0,0,0,2,2,0,0,2,2,2,2,2,0,2,2,0,2,0,2,0,1,1,0,2,0,0,0,0,2,0,0,2,2,0,2,0,0,0,0,2,0,2,2,0,0,0,0,2,2,2,2,2,0,2,0,2,0,2,0,0,2,0,1,1,0,0,0,0,2,0,2,0,0,0,2,2,0,0,2,2,0,2,0,0,0,0,0,2,0,2,0,0,2,0,2,2,2,0,0,2,0,0,2,0,1,1,0,2,2,0,2,2,0,0,2,2,0,2,2,2,2,2,0,2,0,0,0,0,0,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,2,0,1,1,0,0,0,2,2,0,2,2,2,0,2,0,2,2,0,0,0,2,2,2,2,2,2,0,0,0,0,2,0,2,2,0,0,0,0,0,2,2,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,57,12,56,27,22,51,23,56,81,51,42,19,57,97,82,70,24,36,70,51,86,22,44,17,68,68,76,3,5,83,77,61,50,40,6,31,75,43,30,40,61,77,17,92,23,40,49,22,27,67,8,2,6,77,23,59,73,50,5,44,87,49,41,60,34,98,68,29,38,77,40,27,45,87,38,5,90,84,10,22,8,33,48,5,82,47,88,86,26,59,73,41,42,85,95,55,20,60,19,78,88,51,64,71,6,11,8,29,11,61,20,38,9,92,13,59,35,11,83,23,66,76,91,8,9,84,74,8,37,20,31,19,16,3,52,38,89,36,43,97,27,95,51,15,38,93,65,93,29,14,71,30,16,57,23,32,94,54,9,21,51,9,59,88,17,35,2,97,4,3,70,94,97,36,90,64,87,58,42,69,35,64,29,8,17,75,93,44,89,63,85,81,1,96,45,56,20,19,16,54,31,17,69,89,20,16,21,13,59,98,53,41,47,55,94,32,26,64,4,36,26,44,7,64,80,52,65,75,81,81,89,78,4,19,76,98,62,79,33,9,24,40,51,79,77,23,46,79,22,5,53,22,81,35,3,49,35,90,76,89,73,11,17,71,49,95,85,64,82,5,38,82,88,45,80,49,7,53,28,57,14,35,11,71,52,65,3,42,97,24,1,39,68,24,41,56,18,61,71,87,88,8,74,81,9,59,91,22,21,83,50,61,46,33,87,28,7,31,88,66,2,92,76,36,42,83,50,42,89,2,40,98,84,60,46,36,38,86,27,17,34,80,60,43,64,44,24,71,76,43,85,98,89,72,2,53,37,81,48,33,68,96,36,34,72,25,71,72,40,60,30,27,82,91,57,46,36,6,95,75,19,29,56,84,59,80,58,15,95,10,36,8,88,79,29,59,91,17,12,42,68,64,36,74,81,22,61,95,44,88,96,14,1,78,77,76,24,81,19,39,95,80,21,73,59,61,62,49,49,47,53,4,22,65,12,59,8,11,43,52,12,71,35,2,75,78,54,62,39,3,58,90,54,8,54,27,44,45,76,33,72,98,89,2,67,96,95,25,57,29,51,34,63,81,89,63,56,40,96,64,50,7,20,72,3,1,91,63,16,69,12,1,11,84,54,13,96,33,27,44,76,67,83,27,84,3,27,17,82,92,34,12,94,63,33,74,49,81,28,59,58,82,1,92,92,93,92,28,10,14,5,22,48,95,43,95,76,66,67,4,51,61,17,23,21,37,67,9,30,76,22,33,77,98,70,68,18,55,33,65,43,31,21,31,30,10,39,32,44,57,62,91,38,74,47,38,78,68,2,93,53,89,51,65,71,75,34,18,1,19,42,68,26,91,25,40,92,44,74,61,85,19,33,76,46,89,10,61,96,43,97,25,14,66,90,30,82,6,61,10,29,89,15,19,18,63,58,37,85,64,37,29,3,1,71,33,73,26,50,49,13,11,68,53,57,8,32,47,59,15,47,14,12,95,49,57,5,24,22,46,9,72,3,85,67,47,57,71,3,76,29,36,54,38,44,45,17,84,97,21,16,92,81,62,54,44,20,93,80,84,1,55,64,28,42,38,42,17,22,14,3,9,73,48,79,45,5,76,27,40,3,31,60,6,65,30,71,70,44,93,26,3,27,91,23,50,94,45,82,52,39,16,71,19,84,38,64,45,60,12,87,90,80,93,39,78,46,30,37,33,15,49,36,75,46,18,59,15,1,7,4,21,80,11,75,72,12,5,9,63,65,3,54,32,74,15,5,8,50,39,5,80,83,15,58,20,1,95,61,15,27,35,14,69,55,50,32,2,58,17,4,1,89,83,78,4,44,48,76,17,73,76,65,59,97,76,88,70,35,67,51,71,65,15,51,90,97,82,11,19,40,22,17,43,22,6,44,21,51,43,769929"