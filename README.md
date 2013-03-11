forumhr_parser
==============

HTML parser for www.forum.hr (Java)

Parsing is done with JSoup in Java programming language

Currently the parser is able to scrape Topics and Threads.

UPDATE: post scraping available (24.02.2013.)

For Topic, these attributes are scraped:
* name (String)
* uri (String)
* description (String)
* list of subtopics (HashMap<SubtopicName, SubtopicUrl>)

For Thread, attributes are as follows:
* thread ID (int)
* thread name (String)
* thread uri (String)
* thread author (String)
* number of pages in thread (int)
* boolean is top thread? (top = trending) (boolean)
* information about last post in thread (time + author) (String)

For Post, attributes are:
* postAuthor (String)
* postDate (String)
* postText (String)
* postHtml (String)
* postAuthorAvatarPath (String)

For user, attributes are:
* username (String)
* userLastActivity (String)
* userDefinedInfo (registrationDate, totalPost, age) (HashMap<String, String>)


I intend to update this list as often as possible.

Feel free to use any portion of this code without any permissions. 