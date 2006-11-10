%% name of this file abnt-alf-20010813.bst
%% Copyright 2001 by the abnTeX group at http://groups.yahoo.com/group/abntex
%%
%% This file is distributed under the LaTeXProject Public License (LPPL) http://www.latex-project.org/lppl.html
%% You are free to modify this file under the LPPL.
%%
%% This file is an *extensive* modification from a file generated with the docstrip utility.
%% The original source files were:
%% merlin.mbs  (with options: `babel,nm-revf,aunm-semi,nmlm,x3,m3,dt-end,yr-com,xmth,vnum-x,volp-com,jwdpg,jwdvol,num-xser,btit-rm,bt-rm,pg-bk,add-pub,pub-date,pub-xpar,pre-pub,isbn,issn,pp,ed,abr,ednx,nfss')
%% These sourefiles are copyrighted by Patrick W Daly
%% 
ENTRY
  { address author booktitle booksubtitle chapter edition editor dimensions
    howpublished institution
    isbn issn journal key month note marginnote number organization pages publisher school
    series subtitle title type url urlaccessdate
    volume year 
    abnt-emphasize abnt-full-initials abnt-repeated-field-omit
    abnt-show-options abnt-thesis-year abnt-dont-use-etal}{}{ label }
STRINGS {abnt.emphasize}
INTEGERS {abnt.alf abnt.full.initials abnt.repeated.field.omit abnt.show.options
          abnt.thesis.year abnt.dont.use.etal
          output.state before.all mid.sentence after.sentence after.block }
FUNCTION {init.state.consts}
{ #0 'before.all :=
  #1 'mid.sentence :=
  #2 'after.sentence :=
  #3 'after.block :=
}
STRINGS { s t}
%output.nonnull decides what is written before the argument s, if a comma, period etc.
FUNCTION {output.nonnull} 
{ 's :=                                     %the argument is written into s
  output.state mid.sentence =
    { ", " * write$ }                       %mid.sentence first write comma
    { output.state after.block =
        { add.period$ write$ newline$ "\newblock " write$}
        { output.state before.all = 'write$
            { add.period$ " " * write$ }
          if$}
      if$
      mid.sentence 'output.state := }
  if$
  s                                         %now comes s
}
FUNCTION {output.nonnull.dash} 
{ 's :=                                     %the argument is written into s
  output.state mid.sentence =
    { " --- " * write$ }                       %mid.sentence first write comma
    { output.state after.block =
        { add.period$ write$ newline$ "\newblock " write$}
        { output.state before.all = 'write$
            { add.period$ " " * write$ }
          if$}
      if$
      mid.sentence 'output.state := }
  if$
  s                                         %now comes s
}
FUNCTION {output}     %does nothing or calls output.nonull
{ duplicate$ empty$
    'pop$
    'output.nonnull
  if$
}
FUNCTION {output.dash}     %does nothing or calls output.nonull
{ duplicate$ empty$
    'pop$
    'output.nonnull.dash
  if$
}
FUNCTION {output.check}  %like ouput, but complains if t empty
{ 't :=
  duplicate$ empty$
    { pop$ "empty " t * " in " * cite$ * warning$ }
    'output.nonnull
  if$
}
FUNCTION {fin.entry} { add.period$  write$ newline$ }

FUNCTION {new.block}
{ output.state before.all =
    'skip$
    { after.block 'output.state := }
  if$
}
FUNCTION {new.sentence}
{ output.state after.block =
    'skip$
    { output.state before.all =
        'skip$
        { after.sentence 'output.state := }
      if$
    }
  if$
}
FUNCTION {add.blank} {  " " * before.all 'output.state :=}

FUNCTION {date.block} {new.block}

FUNCTION {not}
{   { #0 }
    { #1 }
  if$
}
FUNCTION {and}
{   'skip$
    { pop$ #0 }
  if$
}
FUNCTION {or}
{   { pop$ #1 }
    'skip$
  if$
}
FUNCTION {new.block.checka}
{ empty$
    'skip$
    'new.block
  if$
}
FUNCTION {new.block.checkb}
{ empty$
  swap$ empty$
  and
    'skip$
    'new.block
  if$
}
FUNCTION {new.sentence.checka}
{ empty$
    'skip$
    'new.sentence
  if$
}
FUNCTION {new.sentence.checkb}
{ empty$
  swap$ empty$
  and
    'skip$
    'new.sentence
  if$
}
FUNCTION {field.or.null}
{ duplicate$ empty$
    { pop$ "" }
    'skip$
  if$
}
FUNCTION {emphasize}
{ duplicate$ empty$
    { pop$ "" }
    { "{" abnt.emphasize * " " * swap$ * "}" * }
  if$
}


FUNCTION {same.field} { "\underline{\ \ \ \ \ \ }" }

FUNCTION {tie.or.space.prefix} %if text length less than 3 chars. uses a tie
{ duplicate$ text.length$ #3 <
    { "~" }
    { " " }
  if$
  swap$
}

FUNCTION {no.space} { { "" } swap$ }

FUNCTION {capitalize} { "\capitalize " swap$ * }

FUNCTION {space.word} { " " swap$ * " " * }
FUNCTION {bbl.and} { "; "}

FUNCTION {bbl.etal}{ " et al."}

FUNCTION {bbl.editors} { "Ed." }

FUNCTION {bbl.editor} { "Ed." }

FUNCTION {bbl.edby} { "editado por" } 

FUNCTION {bbl.edition} { "ed." }

FUNCTION {bbl.volume} { "v." }

FUNCTION {bbl.of} { "de" }

FUNCTION {bbl.number} { "n." }

FUNCTION {bbl.nr} { "n." }

FUNCTION {bbl.in} { "In: " }

FUNCTION {bbl.pages} { "p." }

FUNCTION {bbl.page} { "p." }

FUNCTION {bbl.chapter} { "cap." }

FUNCTION {bbl.techrep} { "Rel. Tec." }

FUNCTION {bbl.mthesis} { "Dissertação" } 

FUNCTION {bbl.phdthesis} { "Tese" }

FUNCTION {bbl.monography} { "Monografia" } 

FUNCTION {bbl.first} { "1." }

FUNCTION {bbl.second} { "2." }

FUNCTION {bbl.third} { "3." }

FUNCTION {bbl.fourth} { "4." }

FUNCTION {bbl.fifth} { "5." }

FUNCTION {bbl.st} { "." } 

FUNCTION {bbl.nd} { "." } 

FUNCTION {bbl.rd} { "." }

FUNCTION {bbl.th} { "." }

MACRO {jan} {"jan."}

MACRO {feb} {"fev."}

MACRO {mar} {"mar."}

MACRO {apr} {"abr."}

MACRO {may} {"maio"}

MACRO {jun} {"jun."}

MACRO {jul} {"jul."}

MACRO {aug} {"ago."}

MACRO {sep} {"set."}

MACRO {oct} {"out."}

MACRO {nov} {"nov."}

MACRO {dec} {"dez."}

FUNCTION {eng.ord}
{ duplicate$ "1" swap$ *
  #-2 #1 substring$ "1" =
     { bbl.th * }
     { duplicate$ #-1 #1 substring$
       duplicate$ "1" =
         { pop$ bbl.st * }
         { duplicate$ "2" =
             { pop$ bbl.nd * }
             { "3" =
                 { bbl.rd * }
                 { bbl.th * }
               if$
             }
           if$
          }
       if$
     }
   if$
}

FUNCTION {is.num}
{ chr.to.int$
  duplicate$ "0" chr.to.int$ < not
  swap$ "9" chr.to.int$ > not and
}
FUNCTION {bibinfo.check}
{ swap$ duplicate$ missing$
    {pop$ pop$ ""}
    { duplicate$ empty$
        {swap$ pop$}
        {swap$ pop$}
      if$
    }
  if$
}
FUNCTION {bibinfo.warn}
{ swap$
  duplicate$ missing$
    {swap$ "missing " swap$ * " in " * cite$ * warning$ pop$ ""}
    { duplicate$ empty$
        {swap$ "empty " swap$ * " in " * cite$ * warning$}
        {swap$ pop$}
      if$
    }
  if$
}
STRINGS  { bibinfo}
INTEGERS { nameptr namesleft numnames }

FUNCTION {format.names.for.label} %abnt-alf only
{  's :=
   s #1 "{ll}{jj}" format.name$ "u" change.case$ }

FUNCTION {format.names}
{ 'bibinfo :=  duplicate$ empty$ 'skip$ 
  {'s := "" 
   't :=
   #1 'nameptr :=  s num.names$ 'numnames :=
    numnames 'namesleft :=
    { namesleft #0 > }
    { s nameptr 
      "{ll}{ jj}" format.name$ "u" change.case$
      abnt.full.initials
        {s nameptr "{, ff}{ vv}" format.name$ *}  
        {s nameptr "{, f.}{ vv}" format.name$ *}  
      if$
      bibinfo bibinfo.check
      't :=
      nameptr #1 >
        {nameptr #1 #1 + = numnames #3 > and
            {abnt.dont.use.etal 'skip$ {"others" 't := #1 'namesleft :=} if$ }
            'skip$
          if$
          namesleft #1 >
            { "; " * t * }
            { numnames #2 >
                { "" * }
                'skip$
              if$
              s nameptr "{ll}" format.name$ duplicate$ "others" =
                { 't := }
                { pop$ }
              if$
              t "others" =
                { bbl.etal *}
                { "; " * t *}
              if$ }
          if$ }
        't
      if$
      nameptr #1 + 'nameptr :=
      namesleft #1 - 'namesleft := }
  while$
  } if$
}
FUNCTION {format.names.ed} {format.names}

FUNCTION {format.authors} { author "author" format.names}

FUNCTION {get.bbl.editor} { editor num.names$ #1 > 'bbl.editors 'bbl.editor if$ }

FUNCTION {format.editors}
{ editor  empty$
   {"inbook" type$ = 'same.field 'skip$ if$}
   {author empty$
    {editor "editor" format.names " (" * bbl.editor ")" * *}
    {"inbook" type$ =
      { same.field }
      {editor "editor" format.names " (" * bbl.editor ")" * *}
    if$}
   if$}
  if$
}
FUNCTION {format.book.pages}
{ pages "pages" bibinfo.check  duplicate$ empty$ 
    'skip$
    {  "~p." * }
  if$
}
FUNCTION {format.dimensions}
{ dimensions empty$
    'skip$
    { dimensions output}
  if$
}
FUNCTION {format.isbn}
{ isbn "isbn" bibinfo.check duplicate$ empty$ 
    'skip$
    {new.block "ISBN " swap$ *}
  if$
}

FUNCTION {format.issn}
{ issn "issn" bibinfo.check
  duplicate$ empty$ 'skip$
    {new.block "ISSN " swap$ *}
  if$
}

FUNCTION {format.note}
{ note empty$
    { "" }
    { note #1 #1 substring$
      duplicate$ "{" =
        'skip$
        { output.state mid.sentence =
          { "l" }
          { "u" }
        if$
        change.case$ }
      if$
      note #2 global.max$ substring$ * "note" bibinfo.check }
  if$
}
FUNCTION { format.marginnote }
{ marginnote empty$ 'skip$
  {"{\marginpar{" marginnote * "}}" * write$}
 if$
}

INTEGERS { title.lenght title.pos first.word}
STRINGS { title.char}
FUNCTION {format.title.as.author}
{ 's :=
  s text.length$ 'title.lenght :=
  #1 'title.pos :=   #1 'first.word :=
  "" 't :=
  {title.pos title.lenght > not}
    {s title.pos #1 substring$ 'title.char :=
     title.char " " = %title.pos #3 > and
       {#0 'first.word :=
        t s title.pos global.max$ substring$ * 't :=
        title.lenght #1 + 'title.pos :=}
       {first.word {title.char "u" change.case$ 'title.char :=} 'skip$ if$
        t title.char * 't :=
        title.pos #1 + 'title.pos :=}
     if$
     }
  while$
t
}
FUNCTION {format.title.for.label}
{ 's :=
  s text.length$ 'title.lenght :=
  #1 'title.pos :=   #1 'first.word :=
  "" 't :=
  {title.pos title.lenght > not}
    {s title.pos #1 substring$ 'title.char :=
     title.char " " = title.char ":" = or %title.pos #3 > and
       {#0 'first.word :=
        title.lenght #1 + 'title.pos :=}
       {first.word {title.char "u" change.case$ 'title.char :=} 'skip$ if$
        t title.char * 't :=
        title.pos #1 + 'title.pos :=}
     if$
     }
  while$
t
}

FUNCTION {format.title}
{author empty$ editor empty$ and organization empty$ and
  {title format.title.as.author}
  {title  duplicate$ empty$ 'skip$ { "t" change.case$} if$}
 if$
 subtitle  empty$ 'skip$ {subtitle ": " swap$ * "t" change.case$ *} if$
}
FUNCTION {output.bibitem}
{ newline$
  abnt.alf
  {"\bibitem[" write$ label write$ "]{"}
  {"\bibitem{"}
  if$
  write$ cite$ write$ "}" write$
  newline$ "" before.all 'output.state :=
}

FUNCTION {n.dashify}
{
  't :=
  ""
    { t empty$ not }
    { t #1 #1 substring$ "-" =
        { t #1 #2 substring$ "--" = not
            { "--" *
              t #2 global.max$ substring$ 't :=
            }
            {   { t #1 #1 substring$ "-" = }
                { "-" *
                  t #2 global.max$ substring$ 't :=
                }
              while$
            }
          if$
        }
        { t #1 #1 substring$ *
          t #2 global.max$ substring$ 't :=
        }
      if$
    }
  while$
}

FUNCTION {word.in} { bbl.in " " * }

FUNCTION {format.month}
{ month empty$ 'skip$
  {month "month" bibinfo.check}
  if$
}

FUNCTION {format.date}
{ year empty$
    { month empty$
	{ "" }
	{ "there's a month but no year in " cite$ * warning$ month}
      if$
    }
    { month empty$
	'year
	{ month " " * year * }
      if$
    }
  if$
}
FUNCTION {format.btitle}
{author empty$ editor empty$ and organization empty$ and
    {title format.title.as.author "title" bibinfo.check duplicate$ empty$ 'skip$ {} if$
    subtitle  empty$ 'skip$ {subtitle ": " swap$ * "t" change.case$ *} if$}
    {title "title" bibinfo.check emphasize duplicate$ empty$ 'skip$ {} if$
    subtitle  empty$ 'skip$ {subtitle ": " swap$ * "t" change.case$ *} if$}
if$
}
FUNCTION {either.or.check}
{ empty$
    'pop$
    { "can't use both " swap$ * " fields in " * cite$ * warning$ }
  if$
}
FUNCTION {format.bvolume}
{ volume empty$
    { "" }
    { bbl.volume volume tie.or.space.prefix
      "volume" bibinfo.check * *
      series "series" bibinfo.check
      duplicate$ empty$ 'pop$
        { swap$ bbl.of space.word * swap$
          emphasize * }
      if$
      "volume and number" number either.or.check
    }
  if$
}
FUNCTION {format.number.series}
{ number empty$
        { series empty$
          { "" }
          {" (" series "series" bibinfo.check * ")" *}
        if$}
        { series empty$
            { number "number" bibinfo.check }
        { " ("  series "series" bibinfo.check * ", " *  
          number ")" * "number" bibinfo.check *  }
      if$ }
  if$
}

FUNCTION {extract.num}
{ duplicate$ 't :=
  "" 's :=
  { t empty$ not }
  { t #1 #1 substring$
    t #2 global.max$ substring$ 't :=
    duplicate$ is.num
      { s swap$ * 's := }
      { pop$ "" 't := }
    if$ }
  while$
  s empty$
    'skip$
    { pop$ s }
  if$
}
 
FUNCTION {format.edition}
{ edition duplicate$ empty$ 'skip$
    {output.state mid.sentence = { "l" } { "t" } if$ change.case$
      "edition" bibinfo.check " " * bbl.edition * }
  if$
}
INTEGERS { multiresult }
FUNCTION {multi.page.check}
{ 't :=
  #0 'multiresult :=
    { multiresult not
      t empty$ not
      and }
    { t #1 #1 substring$
      duplicate$ "-" =
      swap$ duplicate$ "," =
      swap$ "+" =
      or or
        { #1 'multiresult := }
        { t #2 global.max$ substring$ 't := }
      if$ }
  while$
  multiresult
}
FUNCTION {format.pages}
{ pages duplicate$ empty$ 'skip$
    { duplicate$ multi.page.check
        { bbl.pages swap$ n.dashify}
        { bbl.page swap$ }
      if$
      tie.or.space.prefix "pages" bibinfo.check * * }
  if$
}
FUNCTION {format.journal.pages}
{ pages duplicate$ empty$ 'pop$
    { swap$ duplicate$ empty$
        { pop$ pop$ format.pages }
        { ", " * swap$ n.dashify pages 
          multi.page.check
            'bbl.pages
            'bbl.page
          if$
          swap$ tie.or.space.prefix "pages" bibinfo.check * * * }
      if$ }
  if$
}

FUNCTION {format.url}
{ url duplicate$ empty$ 'skip$ 
    { "Dispon\'\i vel em: $<$" swap$ * "$>$" *}
  if$
  "url" bibinfo.check output new.sentence
 urlaccessdate duplicate$ empty$ 'skip$ 
    { "Acesso em: " swap$ *}
  if$
  "urlaccessdate" bibinfo.check output new.sentence
}

FUNCTION {format.number}
{ number duplicate$ empty$ 
    'skip$
    {bbl.number swap$ tie.or.space.prefix "number" bibinfo.check * *}
  if$  
}

FUNCTION {format.volume}
{volume duplicate$ empty$ 
    'skip$
    %{duplicate$ is.num (need to find a function which test if volume is a number
       {bbl.volume swap$ tie.or.space.prefix "volume" bibinfo.check * *}
       %{"volume" bibinfo.check *}
     %if$}
  if$
}

FUNCTION {format.chapter.pages}
{ chapter empty$
    'format.pages
    { type empty$
        { bbl.chapter }
        { type "l" change.case$
          "type" bibinfo.check }
      if$
      chapter tie.or.space.prefix "chapter" bibinfo.check * *
      pages empty$
        'skip$
        { ", " * format.pages * }
      if$ }
  if$
}

FUNCTION {format.booktitle}
{ %booktitle  duplicate$ empty$ 'skip$ { "t" change.case$ emphasize} if$
  %booksubtitle  empty$ 'skip$ {":  " booksubtitle * "t" change.case$ * "booksubtitle" bibinfo.check} if$
author empty$ editor empty$ and organization empty$ and
    {booktitle format.title.as.author "booktitle" bibinfo.check duplicate$ empty$ 'skip$ {} if$
    booksubtitle  empty$ 'skip$ {booksubtitle ": " swap$ * "t" change.case$ *} if$}
    {booktitle "booktitle" bibinfo.check emphasize duplicate$ empty$ 'skip$ {} if$
    booksubtitle  empty$ 'skip$ {booksubtitle ": " swap$ * "t" change.case$ *} if$}
if$
}
FUNCTION {format.in.ed.booktitle}
{ editor empty$ 
    {organization empty$
      {word.in booktitle empty$ 'skip$ {format.booktitle * } if$}
      {word.in organization "u" change.case$ *
      booktitle empty$ 'skip$ {". " format.booktitle * * } if$}
     if$}
    {word.in format.editors *
     booktitle empty$ 'skip$ {". " format.booktitle * * } if$} 
  if$
}
FUNCTION {empty.misc.check}
{ author empty$ title empty$ howpublished empty$
  month empty$ year empty$ note empty$
  and and and and and
  key empty$ not and
    { "all relevant fields are empty in " cite$ * warning$ }
    'skip$
  if$
}
FUNCTION {format.thesis.type}
{ type duplicate$ empty$
    'pop$
    {" (" swap$ * ")" * "type" bibinfo.check}
  if$
}
FUNCTION {format.type}
{ type "type" bibinfo.check
  duplicate$ empty$ 'skip$
    {new.block "" swap$ * }
  if$
}


FUNCTION {format.tr.number}
{ number "number" bibinfo.check
  type duplicate$ empty$
    { pop$ bbl.techrep }
    'skip$
  if$
  "type" bibinfo.check
  swap$ duplicate$ empty$
    { pop$ "t" change.case$ }
    { tie.or.space.prefix * * }
  if$
}
FUNCTION {format.article.crossref}
{
  key duplicate$ empty$
    { pop$
      journal duplicate$ empty$
        { "need key or journal for " cite$ * " to crossref " * crossref * warning$ }
        { "journal" bibinfo.check emphasize word.in swap$ * }
      if$
    }
    { word.in swap$ * " " *}
  if$
  " \cite{" * crossref * "}" *
}
FUNCTION {format.crossref.editor}
{ editor #1 "{vv~}{ll}" format.name$ "editor" bibinfo.check
  editor num.names$ duplicate$ #2 >
    { pop$ "editor" bibinfo.check " " * bbl.etal *}
    { #2 <
        'skip$
        { editor #2 "{ff }{vv }{ll}{ jj}" format.name$ "others" =
            {"editor" bibinfo.check " " * bbl.etal * }
            {bbl.and space.word * editor #2 "{vv~}{ll}" format.name$
            "editor" bibinfo.check  *}
          if$}
      if$}
  if$
}
FUNCTION {format.book.crossref}
{ volume duplicate$ empty$
    { "empty volume in " cite$ * "'s crossref of " * crossref * warning$
      pop$ word.in }
    { bbl.volume capitalize
      swap$ tie.or.space.prefix "volume" bibinfo.check * * bbl.of space.word *}
  if$
  editor empty$
  editor field.or.null author field.or.null = or
    { key empty$
        { series empty$
            { "need editor, key, or series for " cite$ * " to crossref " *
              crossref * warning$ "" * }
            { series emphasize * }
          if$ }
        { key * }
      if$ }
    { format.crossref.editor * }
  if$
  " \cite{" * crossref * "}" *
}
FUNCTION {format.incoll.inproc.crossref}
{ editor empty$
  editor field.or.null author field.or.null = or
    { key empty$
        { format.booktitle duplicate$ empty$
            { "need editor, key, or booktitle for " cite$ * " to crossref " *
              crossref * warning$ }
            { word.in swap$ * }
          if$ }
        { word.in key * " " *}
      if$ }
    { word.in format.crossref.editor * " " *}
  if$
  " \cite{" * crossref * "}" *
}
FUNCTION {format.org.or.pub}
{ 't := "" year empty$
    { "empty year in " cite$ * warning$ }
    'skip$
  if$
  address empty$ t empty$ and  year empty$ and
    'skip$
    {address "address" bibinfo.check * t empty$
      { address empty$ { "[S.l.: s.n.]" *} { ": [s.n.]" * } if$}
      { address empty$ { "[S.l.]: " *} { ": " * } if$ t * }
     if$
     year empty$ 'skip$ { ", " * year "year" bibinfo.check *} if$}
  if$
}
FUNCTION {format.address.sl}
{address empty$
  { "[S.l.]"}
  { address}
 if$
}
FUNCTION {format.publisher.address.year}
{ publisher "publisher" bibinfo.check format.org.or.pub }

FUNCTION {format.organization.address}
{ organization "organization" bibinfo.check format.org.or.pub }

FUNCTION { abnt.display.options}
{ 's := 't :=
  abnt.show.options #1 = % "warn"
  {"option " t * " changed to " * s * warning$ } 'skip$ if$

  abnt.show.options #2 = % "list"
  {"Option {\tt " t * "=}\verb+" * s * "+" * output new.sentence } 'skip$ if$
}

FUNCTION { abnt-options }
  {
    abnt-show-options empty$
    'skip$
      {abnt-show-options "no" =
        {#0} {abnt-show-options "warn" =
           {#1} {abnt-show-options "list" =
             {#2 } {#0 } if$} if$} if$
      'abnt.show.options :=} % #0 "no"; #1 "warn"; #2 "list"
    if$

    abnt.show.options #2 =
    {output.bibitem "{\em Style changes after this point}" output new.sentence} 'skip$ if$

    abnt-show-options empty$
    'skip$ {"abnt-show-options" abnt-show-options abnt.display.options} if$

    abnt-emphasize duplicate$ empty$
    'pop$ {'abnt.emphasize :=
    "abnt-emphasize" abnt.emphasize abnt.display.options} if$

    %abnt-repeated-field-omit duplicate$ empty$ 'pop$ {'abnt.repeated.field.omit :=} if$

    abnt-full-initials empty$
    'skip$ {abnt-full-initials "yes" = 'abnt.full.initials :=
    "abnt-full-initials" abnt-full-initials abnt.display.options} if$

    abnt-thesis-year empty$
    'skip$ {abnt-thesis-year "final" =
              {#0} {abnt-thesis-year "title" =
                {#1} {abnt-thesis-year "both" =
                  {#2} {#0} if$} if$} if$
            'abnt.thesis.year :=
    "abnt-thesis-year" abnt-thesis-year abnt.display.options} if$

    abnt-dont-use-etal empty$
    'skip$ {abnt-dont-use-etal "yes" = 'abnt.dont.use.etal :=
    "abnt-dont-use-etal" abnt-dont-use-etal abnt.display.options} if$


  abnt.show.options #2 = 'fin.entry  'skip$ if$
  }

FUNCTION {article}
{ output.bibitem
  format.authors output new.block
  format.marginnote
  format.title "title" output.check new.block
  crossref missing$
    { journal "journal" bibinfo.check emphasize "journal" output.check
      address "address" bibinfo.check output
      format.volume output 
      format.number output
      format.journal.pages}
    { format.article.crossref output.nonnull
      format.pages output }
  if$
  format.date output
  format.issn output new.block
  format.note output new.block
  format.url
  fin.entry
}
FUNCTION {book}
{ output.bibitem
  author empty$
    { editor empty$ 'skip$ {format.editors "editor" output.check} if$}
    { format.authors output.nonnull
      crossref missing$
        { "author and editor" editor either.or.check }
        'skip$
      if$}
  if$
  new.sentence
%  format.marginnote
  format.type output new.sentence
  format.btitle "title" output.check new.sentence
  format.edition output new.sentence
  crossref missing$
    { format.bvolume output new.sentence
      format.publisher.address.year output new.sentence
      format.book.pages output
      format.dimensions  new.sentence
      format.number.series output new.sentence }
    { new.block format.book.crossref output.nonnull }
  if$
  format.note output
  format.isbn output new.sentence
  format.url
  fin.entry
}
FUNCTION {booklet}
{ output.bibitem
%  format.marginnote
  author empty$
    { editor empty$ 'skip$ {format.editors "editor" output.check} if$}
    { format.authors output.nonnull
      crossref missing$
        { "author and editor" editor either.or.check }
        'skip$
      if$}
  if$
  new.sentence
  format.type output new.sentence
  format.btitle "title" output.check new.sentence
  format.edition output new.sentence
  crossref missing$
    { address "address" bibinfo.check output
      format.bvolume output
      format.date output  new.sentence
      format.book.pages output new.sentence
      format.number.series output new.sentence }
    { new.block format.book.crossref output.nonnull }
  if$
  format.note output
  format.isbn output new.sentence
  format.url
  fin.entry
}

FUNCTION {inbook}
{ output.bibitem
%  format.marginnote
  format.authors "author" output.check  new.block
  format.title "title" output.check new.block
  crossref missing$
    { format.in.ed.booktitle "booktitle" output.check new.sentence
      format.edition output new.sentence
      format.publisher.address.year output new.sentence
      format.bvolume output
      format.number.series output
      format.chapter.pages output new.sentence
      format.isbn output }
    { format.incoll.inproc.crossref output.nonnull
      format.chapter.pages output }
  if$
  new.block
  format.note output new.block
  format.url
  fin.entry
}

FUNCTION {incollection}
{ output.bibitem
%  format.marginnote
  format.authors "author" output.check  new.block
  format.title "title" output.check new.block
  crossref missing$
    { format.in.ed.booktitle "booktitle" output.check new.sentence
      format.edition output new.sentence
      format.publisher.address.year output
      format.bvolume output
      format.number.series output new.sentence
      format.chapter.pages output new.sentence
      format.isbn output }
    { format.incoll.inproc.crossref output.nonnull
      format.chapter.pages output }
  if$
  new.block
  format.note output new.block
  format.url
  fin.entry
}
FUNCTION {inproceedings}
{ output.bibitem
%  format.marginnote
  format.authors "author" output.check  new.block
  format.title "title" output.check  new.block
  crossref missing$
    {format.in.ed.booktitle "booktitle" output.check new.sentence
    format.publisher.address.year output new.sentence
    format.bvolume output
    format.number.series output
    format.pages output
    format.isbn output
    format.issn output }
    { format.incoll.inproc.crossref output.nonnull
      format.pages output }
  if$
  new.sentence
  format.note output new.sentence
  format.url
  fin.entry
}
FUNCTION {conference} { inproceedings }
FUNCTION {manual}
{ output.bibitem
%  format.marginnote
  author empty$
    { organization "u" change.case$ "organization"  bibinfo.check output}
    { format.authors output.nonnull }
  if$
  new.block
  format.btitle "title" output.check new.block
  format.edition output new.sentence
  format.address.sl output
  format.date output new.sentence
  format.book.pages output new.sentence
  format.number.series output new.sentence
  format.note output new.sentence
  format.url
  fin.entry
}

FUNCTION {misc}
{ output.bibitem
%  format.marginnote
  format.authors output
  format.type output
  title howpublished new.block.checkb
  format.title output
  howpublished new.block.checka
  howpublished "howpublished" bibinfo.check output  new.block
  format.note output  new.block
  format.date output  new.block
  format.url
  fin.entry
  empty.misc.check
}
STRINGS { thesisname}
FUNCTION {thesis}
{ 'thesisname :=
  output.bibitem
%  format.marginnote
  format.authors "author" output.check new.block
  format.btitle "title" output.check new.block
  abnt.thesis.year #1 = abnt.thesis.year #2 = or
    {format.date "year" output.check new.sentence} 'skip$ if$
  format.book.pages output new.sentence
  thesisname format.thesis.type * output.nonnull
  school "school" bibinfo.warn output.dash
  address "address" bibinfo.check output
  abnt.thesis.year #0 = abnt.thesis.year #2 = or
    {format.date "year" output.check new.sentence} 'skip$ if$
  format.note output new.block
  format.url
  fin.entry
}

FUNCTION {phdthesis}     { bbl.phdthesis  thesis }
FUNCTION {mastersthesis} { bbl.mthesis    thesis }
FUNCTION {monography}    { bbl.monography thesis }

FUNCTION {proceedings}
{ output.bibitem
%  format.marginnote
  editor empty$
    { organization "u" change.case$ "organization" bibinfo.check output }
    { format.editors output.nonnull }
  if$
  new.block
  format.btitle "title" output.check
  format.bvolume output 
  format.number.series output
  editor empty$
    { publisher empty$
        'skip$
        { new.sentence format.publisher.address.year output}
      if$
    }
    { publisher empty$
        { new.sentence
          format.organization.address output }
        { new.sentence
          organization "organization" bibinfo.check output
          format.publisher.address.year output }
      if$ }
  if$
  new.sentence format.book.pages output new.sentence
  format.isbn output
  format.issn output  new.block
  format.note output  new.block
  format.url
  fin.entry
}

FUNCTION {techreport} {manual}

FUNCTION {unpublished}
{ output.bibitem
%  format.marginnote
  format.authors "author" output.check  new.block
  format.title "title" output.check  new.block
  format.note "note" output.check  new.block
  format.date output new.block
  format.url
  fin.entry
}

FUNCTION {default.type} { misc }
READ
FUNCTION {sortify}
{ purify$
  "l" change.case$
}
INTEGERS { len }
FUNCTION {chop.word}
{ 's :=
  'len :=
  s #1 len substring$ =
    { s len #1 + global.max$ substring$ }
    's
  if$
}
FUNCTION {sort.format.names}
{ 's :=
  #1 'nameptr :=
  ""
  s num.names$ 'numnames :=
  numnames 'namesleft :=
    { namesleft #0 > }
    { s nameptr
      "{vv{ } }{ll{ }}{  ff{ }}{  jj{ }}"
      format.name$ 't :=
      nameptr #1 >
        {
          nameptr #3
          #1 + =
          numnames #3
          > and
            { "others" 't :=
              #1 'namesleft := }
            'skip$
          if$
          "   "  *
          namesleft #1 = t "others" = and
            { "zzzzz" * }
            { t sortify * }
          if$
        }
        { t sortify * }
      if$
      nameptr #1 + 'nameptr :=
      namesleft #1 - 'namesleft :=
    }
  while$
}

FUNCTION {sort.format.title}
{ 't :=
  "A " #2
    "An " #3
      "The " #4 t chop.word
    chop.word
  chop.word
  sortify
  #1 global.max$ substring$
}
FUNCTION {author.sort}
{ author empty$
    { key empty$
        { "to sort, need author or key in " cite$ * warning$
          ""
        }
        { key sortify }
      if$
    }
    { author sort.format.names }
  if$
}
FUNCTION {author.editor.sort}
{ author empty$
    { editor empty$
        { key empty$
            { "to sort, need author, editor, or key in " cite$ * warning$
              ""
            }
            { key sortify }
          if$
        }
        { editor sort.format.names }
      if$
    }
    { author sort.format.names }
  if$
}
FUNCTION {author.organization.sort}
{ author empty$
    { organization empty$
        { key empty$
            { "to sort, need author, organization, or key in " cite$ * warning$
              ""
            }
            { key sortify }
          if$
        }
        { "The " #4 organization chop.word sortify }
      if$
    }
    { author sort.format.names }
  if$
}
FUNCTION {editor.organization.sort}
{ editor empty$
    { organization empty$
        { key empty$
            { "to sort, need editor, organization, or key in " cite$ * warning$
              ""
            }
            { key sortify }
          if$
        }
        { "The " #4 organization chop.word sortify }
      if$
    }
    { editor sort.format.names }
  if$
}
FUNCTION {calc.label}
{  author empty$ not
    {author format.names.for.label 'label :=}
    {editor empty$ not
     {editor format.names.for.label 'label :=}
     {organization empty$ not
      {organization "u" change.case$ 'label :=}
      {title empty$ not
       {title format.title.for.label 'label :=}
       {"nada" 'label :=}
      if$}
     if$}
    if$}
   if$
 year empty$ 'skip$
  {label ", " * year * 'label :=}
 if$
 "article" type$ = 'skip$
  {pages empty$ 'skip$
   {label ", " * format.pages * 'label :=}
  if$}
 if$
}
FUNCTION {presort}
{   type$ "book" =  type$ "inbook" = or
    'author.editor.sort
    { type$ "proceedings" =
        'editor.organization.sort
        %{ type$ "manual" =
            'author.organization.sort
        %    'author.sort
        %  if$ }
      if$ }
  if$
  "    " * year field.or.null sortify * "    " * title field.or.null
  sort.format.title * #1 entry.max$ substring$
  'sort.key$ :=
  calc.label
}
ITERATE {presort}
%abnt-alf: for sorting uncomment the following line
SORT
STRINGS { longest.label }
INTEGERS { number.label longest.label.width }
FUNCTION {initialize.longest.label}
{ "" 'longest.label :=
  #1 'number.label :=
  #0 'longest.label.width :=
}
FUNCTION {longest.label.pass}
{ #1 'abnt.alf := %#1 for abnt-alf.bst and #0 for abnt-num.bst
  abnt.alf not
  {number.label int.to.str$ 'label :=
   number.label #1 + 'number.label :=
   label width$ longest.label.width >
     { label 'longest.label :=
       label width$ 'longest.label.width := }
     'skip$
   if$}
  {#0 'longest.label.width :=}
  if$
}
EXECUTE {initialize.longest.label}
ITERATE {longest.label.pass}
FUNCTION {begin.bib}
{ "\bf" 'abnt.emphasize :=
  #0 'abnt.full.initials :=
  #0 'abnt.repeated.field.omit :=
  #0 'abnt.show.options := % #0 "no"; #1 "warn"; #2 "list"
  #0 'abnt.thesis.year :=  % #0 "final"; #1 "title"; #2 "both"
  #0 'abnt.dont.use.etal :=
  preamble$ empty$
    'skip$
    { preamble$ write$ newline$ }
  if$
  "\begin{thebibliography}{"  longest.label  * "}\setlength{\rightskip}{0pt plus 2cm}" *
%  "\begin{thebibliography}{}\setlength{\rightskip}{0pt plus 2cm}"
  write$ newline$
%  "\input{babelbst.tex}" write$ newline$
  "\newcommand\C[1]{\c#1}\newcommand{\Capitalize}[1]{\uppercase{#1}}" write$ newline$
  "\newcommand{\capitalize}[1]{\expandafter\Capitalize#1}" write$ newline$
}
EXECUTE {begin.bib}
EXECUTE {init.state.consts}
ITERATE {call.type$}
FUNCTION {end.bib} { newline$ "\end{thebibliography}" write$ newline$}
EXECUTE {end.bib}
%% End of customized bst file
%%
%% End of file `abnt.bst'.
