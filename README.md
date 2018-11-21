# YouTubeDemo

the app uses unofficial API from youtube why?

I had tried using YouTube Data API To get subtitle but the request requires authorization with at least one of 
the following scopes:

1.https://www.googleapis.com/auth/youtube.force-ssl

2.https://www.googleapis.com/auth/youtubepartner

that's mean I need to make use to log in with google account to get permission to download caption(subtitle), and then take the 
access token and send it with the API request. so I make and get 401 unauthorized ????????

after the search found that the Caption API let you download subtitle from your youtube channel only you can't download subtitle 
for other videos with it so !!!! search again 

found unofficial API from youtube wow! so start test it also I found that youtube use it just open any video have a subtitle
(make sure subtitle on in the video) then from inspect element go to Network tab then search for timedtext and you will find 
request 

baseUrl  https://www.youtube.com/api/timedtext?

**name** &emsp; **required** &emsp;   the track name, Required only if it is set. (and with this is my problem.)

**lang**  &emsp;  **required**  &emsp;  language en, ar

**v**    &emsp;&emsp;   **required** &emsp;   video id

**tlang** &emsp;  optional  &emsp;  translation to language. optional (should be set if you like to translate the CC to another language.

**fmt**  &emsp;  optional  &emsp; [ttml,vtt,srv3]

**type**   &emsp; optional   &emsp; [list,track]

