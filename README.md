forumhr_parser
==============

HTML parser for www.forum.hr (Java)

- Parsing is done with JSoup in Java programming language

Currently the parser is able to scrape Topics and Threads.

For Topic, these attributes are scraped:
	- name
	- uri
	- description
	- list of subtopics

For Thread, attributes are as follows:
	- thread ID
	- thread name
	- thread uri
	- thread author
	- number of pages in thread
	- boolean is top thread? (top = trending)
	- information about last post in thread (time + author)

I intend to update this list as often as possible.

Feel free to use any portion of this code without any permissions. 