#!/bin/bash

while read file; do
    dirname=$(dirname "$file")
    filename=$(basename "$file")
    filename="${filename%.*}"

    name="$dirname""/""$filename"".png"
    printf "process file %s...    " "$name"
    dot -Tpng "$file" -o "$name" & wait
    printf "OK\n"


done < <(find . -iname "*.dot")
