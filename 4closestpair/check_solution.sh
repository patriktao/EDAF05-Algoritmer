#!/bin/bash
# make exacutable: chmod +x check_solution.sh
# run: ./check_solution.sh pypy A.py
# or
# ./check_solution.sh java solution
# ./check_solution.sh ./a.out

for f in data/sample/*.in data/more/*.in data/secret/*.in; do
    start_time=`date +%s`
    echo $f
    pre=${f%.in}
    out=$pre.out
    ans=$pre.ans
    $* < $f > $out
    DIFF=$(diff -w $ans $out)
    if [ "$DIFF" == "" ]
    then 
        end_time=`date +%s`
        echo Execution time was `expr $end_time - $start_time` s.
        echo Correct!
    else
        echo $f Incorrect!
        exit 1
    fi
done
