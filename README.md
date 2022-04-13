# EDAF05-labs
Labs for EDAF05, held at LTH during study period 4 2018-19. Lecturer and course convenor is Jonas Skeppstedt. This version of the lab assignments are written and maintained by Lars Åström. Previous versions have been written by Thore Husfeldt and Måns Magnusson, lots of credit is given to them regarding the labs! If you get the wrong answer on test cases, contact a lab assistant for help. If you find any errors, ambiguities or anything else that should be corrected - please contact me via e-mail: lars96astrom@gmail.com, or submit an issue on [github](https://github.com/LarsAstrom/EDAF05-labs-public/issues).   

## Dependencies:
- `python3`
- `bash` (for Linux and Mac, not needed in Windows)

## How to do labs:
1. Clone repository. (Only needed in the beginning of the course, for the later labs, start at 2.)
2. Write solution in your preferred language. The supported languages in the course are Python and Java. Some lab instructors may help with C and C++, but it is not guaranteed. Put the solution in the lab directory.
3. Navigate (in terminal / command prompt) to the directory of the current lab. 
4. If your solution is in Java, C++ or C, compile your file.
5. 
    - On Linux or Mac, write: `bash check_solution.sh $args`
    - On Windows, write: `.\check_solution.bat $args`
    - where `$args` is the command line to run your program. For example:
        - `bash check_solution.sh python3 solution.py`
        - `bash check_solution.sh java solution`
        - `bash check_solution.sh ./a.out`
    - where solution.py / solution is the solution file. ./a.out is the compiled file of a C or C++ solution. If your solution is in Java, it has to be compiled first, and the main class file has to be placed in the folder directory.

`check_solution.sh/.bat $args` tries to execute whatever arguments you give it in this way: `$args < input.in > output.out` for every test case, and then validate your output file. This means that you can use whichever language you like that can read on stdin and write to stdout.

6. If the solution was correct, this will be written in the terminal. Otherwise you will see which instance your solution failed on.
7. When your solution is correct on all test cases, show this to your lab instructor who will pass you on the lab.
8. After showing the output of the bash/bat-script you and your lab instructor will look at your code and discuss it thoroughly, as well as your report and the answer to the questions in the lab instructions.
9. Note that it is considered cheating to manipulate the scripts in order to trick the instructor into passing you.
