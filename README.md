queryToTable
============

Query to &lt;table>

QueryTo<table> 1.0
Usage: qutie [options] <file> or <url>

  -s <incices> | --select <incices>
        comma separated field indices(0-origin)
  -w:<index>:<ope>=<value> | --where:<index>:<ope>=<value>
        String field filter
  -e:<index>:<ope>=<value> | --nwhere:<index>:<ope>=<value>
        Number field filter
  -i | --indexed
        prepend an index column
  --head
        show only 10 rows.
  --encoding <value>
        document encoding (file only)
  -ti <value> | --table-id <value>
        table ID
  -se <value> | --skip-empty <value>
        skip epmty line
  <file> or <url>
        input file or URL

  <ope>: operators
      (null) | eq : equal
      ne     | !  : not equal
      lt   : less than
      lte  : less than equal
      gt   : greater than
      gte  : greater than equal
      like : regular expression (String only)

  --help
        prints this usage text
