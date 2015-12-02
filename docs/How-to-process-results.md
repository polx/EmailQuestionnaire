# EmailQuestionnaire: How to Process Results?
For each questionnaire of name _questionnaireName_, a directory is designated to contain the responses. The responses are stored in this directory as single XML files.
The responses can be seen there, by looking at the XML files or organizing them there.

The responses can also be looked at using a big table served by the following file: http://myserver/EmailQuestionnaire/list.jsp?quest=questionnaireName 
This JSP file will display all XMLs displaying one column per input name and summarizing the values that have no spaces at the end.

If you want to configure order of the fields, try naming them alphabetically or create a file _fieldNames.xml_ inside the questionnaire directory with a content similar to the following:
    <?xml version="1.0" encoding="UTF-8"?>
    <fieldNames>
      <name>question1</name>
      <name>subQuestion1</name>
      <name>subQuestion2</name>
    </fieldNames>

In the _list.jsp_ file, you can inpsect the session id, which allows you to identify when multiple submissions have been made using the same browser. You can also remove submissions by pressing the "x" at the file name. This will move the file to the trash (which can be restored by moving files by hand).

Once you are happy with the results, you can copy and paste the HTML table into a spreadsheet (tested with Excel and Google Docs, note that Firefox is the only browser that has a table selection for this copy to better work). You can also easily create pie-charts of the distribution of the data using the summaries below.
