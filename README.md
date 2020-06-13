
Android RecyclerView Sample
===================================

Simple application to search a database of books, imported from an Amazon list.

Introduction
------------

The database can be populated from the application menu. The user select a file
and books information will be parsed. The file format is one property per line as follows:

1. book title (string)
2. book author (string)
3. date of Amazon purchase (string)
4. number of Amazon list in which the book is (integer)

So 4 lines for each book. An example can be found in the sampledata folder.
The file can be obtained from an Amazon account simply by copy-pasting the web page content
from the "my content and devices" page.

Pre-requisites
--------------

- Android SDK 28
- Android Build Tools v30.0.0
- Android Support Repository
