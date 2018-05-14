#! /bin/bash
for i in `seq 1 1000`;
do
    java forsale.Test 2> /dev/null >> winners.txt
done
