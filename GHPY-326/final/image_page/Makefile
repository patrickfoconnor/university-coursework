LATEX = latexmk

default: pdf

.PHONY:
clean: 
	${LATEX} -c
	rm -f *.vtc *.bbl

pdf:
	${LATEX} -pdf
