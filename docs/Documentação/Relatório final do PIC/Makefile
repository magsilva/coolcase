TEXFILE = pic 

default: all

clean:
	rm -f *.dvi *.aux *.log *.ps *~ *.pdf *.lot *.ind *.toc *.lof *.blg *.bbl

pdf: 
	pdflatex ${TEXFILE}
	bibtex ${TEXFILE}
	pdflatex ${TEXFILE}
	pdflatex ${TEXFILE}
	pdflatex ${TEXFILE}

all: pdf
