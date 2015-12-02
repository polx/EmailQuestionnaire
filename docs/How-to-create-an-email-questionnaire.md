# EmailQuestionnaire: How to Create an Email-Questionnaire

## 1) Create an HTML form
EmailQuestionnaire employs simple HTML forms and you are expected to input them by hand. The forms are made of the following ingredient:
*  a _form_ element surrounding it all. This one should have the attributes _method="GET"_ and _action="http://server/EmailQuestionnaire/receive/pollName"_ where _server_ is your server's host-name and _pollName_ the questionnaire's name.
* inside there, _input_ elements which can be of type text or radio... also _textarea_ and _button_ elements. Each of them should have a unique _name_ attribute which is used as column-name in the result table
The simplest form you can use is as follows:
    <form method="GET" action="http://server/EmailQuestionnaire/receive/pollName">
    text: <input name="text" value="">
    </form>
This form presents a single text field and its submissions will be made with the column name _text_.
The "GET" method is necessary because email-programme can only communicate to web-browsers using the _open-url_ command and not with a submission of values. This limits somewhat the size of the response in the form that you can let the users input (about 5000 characters nowadays).

Each time you have a form that is somewhat in a good state, you should preview it in the browser and also try to copy it into a mail and send it.

## 2) Pack into a mail
Contemporary email-programmes all handle HTML mails end to end with a security model and rendering capabilities that are slightly different than those of a web-browser.
An EmailQuestionnaire is best edited by hand (or with [Kompozer](http://kompozer.net) or others) then previewed and tested in the browser, then copy and pasted to the corresponding mail application where you send the questionnaire the intended recipients.
You should copy the complete from start to end of the _form_ element.
To preserve the HTML nature, and avoid translations such as going through RTF, I recommend that you match the browser environment and email programme: Firefox -> Thunderbird (or Postbox), Safari -> Apple Mail, Chrome -> GMail.
You should try to send it to yourself to check whether your mail programme supports displaying forms and how it gets changed in appearance. Opening the mail on a mobile phone, for example, can bring quite many surprises.

## 3) Create the questionnaire
The file _web.xml_ which was adjusted at the installation process has indicted a directory of XML files. Inside there, each questionnaire, identified by the name above, will store its responses within the directory of its name. This way, administrators of the web-application can allow only the submission of  named questionnaires. This tiny security measure can avoid that systematic spamming occurs on EmailQuestionnaire server instances since the questionnaire names are local to each installation.

Try to submit a questionnaire, you should see a page such as _Thank you_ displayed after the submission has been made. An XML file should be created in the directory of the questionnaire.
You are now ready to [process the results](How-to-process-results.md).