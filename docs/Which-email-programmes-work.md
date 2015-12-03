# EmailQuestionnaires: Which Email Programmes Work?

## Successes
EmailQuestionnaire has been tested successfully with the following programmes:
* Apple Mail
* Thunderbird
* Outlook
* GMail on desktop and mobiles
* K9 Mails on Android
* many "default mail programmes" on mobiles (e.g. on Blackberry, iPhone, and many Android).
For each of these systems, elementary form elements are displayed properly and the submit button appears and calls the default browser to submit the form, passing all responses in the URL. It should be noted, however, that no javascript is working and CSS often fails.

## Failures
It is known to be failing on the following programmes:
* the [web.de](http://web.de) web-mailer (no form element is displayed, this is probably a security measure)

## Warning
Since at least one mail-system exists which prevents the use of the form functions, it is recommended that each questionnaire starts with an indication such as the following:
_This questionnaire is designed to be answered to in the email. Before doing so, please proof that answers can be chosen and that a submit button is displayed below. If not, please use [the online version ](http://myserver/static/questionnaire.html)._