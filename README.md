qutie
============

Query to &lt;table>

usage
----------
```
QueryTo<table> 1.0
Usage: qutie [options] <file> or <url>

  -s <indices> | --select <indices>
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
  -se | --skip-empty
        skip epmty line
  <file> or <url>
        input file or URL

  <ope>: operators
      (null) | eq : equal
      ne     | !  : not equal
      lt   : less than
      lte  : less than or equal
      gt   : greater than
      gte  : greater than or equal
      like   | ~  : regular expression (String only)

  --help
        prints this usage text
```

examples
----------

```
# query to the results of Honolulu Marathon 2013
$ java -jar queryToTable-assembly.jar http://www.pseresults.com/events/568/results -se \
  -s "1,2,4,5,7" -w:1:~="\d+" -e:1:gt=10000 --indexed
"1","25742","2:23:20","Tsukamoto","Shuji","JPN"
"2","25767","2:31:13","Hino","Yuya","JPN"
"3","22466","2:32:14","Yagi","Daizo","JPN"
"4","29074","2:32:45","Tsukiyama","Yoshihiro","JPN"
"5","22691","2:36:36","Fujimoto","Takeshi","JPN"
"6","24193","2:37:10","Makino","Saeki","JPN"
"7","31077","2:38:50","Sato","Eiji","JPN"
"8","29743","2:42:23","Ogura","Yosuke","JPN"
"9","27226","2:42:44","Nakazawa","Kei","JPN"
"10","15066","2:43:33","Meyer","Timo","GER"
```
