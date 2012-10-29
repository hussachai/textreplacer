textreplacer
============

I spent 4 hours on tiny java console application for job interview.
It's Jr. Java Developer (Entry level)
Keep in mind that this tiny project is for job interview. It does not intent
to be practical but it should be able to show some skills on designing software
and proficiency on language and API.

Assignment
-----------
Write a class using Python or JAVA that complies with the following requirements:
* The Program will allow these arguments:
	- A path to a directory of files.
	- Original text or pattern which will be replaced if found in the files (as many times as it was found).
	- New text or pattern which will replace the original one if found in the file (as many times as it was found).
* Optionally, the class might also get an argument for a path to a file for outputting a list of which files were modified.
* Add comments to your class.
* Extra credit if you back up the original file before replacing the text.
* Extra credit if you implement some simple and creative logging (start time, errors, end time, pattern found and where, etc)


Usage
------

###Help

java -jar textreplacer-1.0.jar 

###Batch mode: 

Full options:

java -jar textreplacer-1.0.jar -f"d:/tmp/text" -s"Your Text" -r"Replace with this " -R -L"d:/tmp/text/filelog.txt" -p0 -b".bak" -F"*.txt" --encoding UTF-8 --terminateOnError

Min options

java -jar textreplacer-1.0.jar -f"d:/tmp/text" -s"Your Text" -r"Replace with this "

###Interactive mode:

java -jar textreplacer-1.0.jar -i

###File structure

binary file: textreplacer-1.0.jar

source file: textreplacer-1.0-sources.jar

maven project (src, pom.xml, test) : textreplacer

java docs: apidocs

Total line of codes: 1,554 lines

###Requirement

Java Runtime Environment: >=1.6

For developer: You may need Apache Maven to build this project.

