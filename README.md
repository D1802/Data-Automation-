# Data Automation - _Project Author (Darpan Sarode)_

## Project Name and Description:

# Project Name :-Web Data Automation and JSON Storage

# Description :- 
This project automates the process of scraping data from a website and storing the collected data in JSON format. The automation will:

1. Navigate to a specified website and fetch hockey team data from a paginated table.
2. Fetch Oscar-winning films data by clicking on each year and extracting the top 5 movies.
3. Store the collected data into JSON files.
4. Use the TestNG framework to assert that the JSON files are created and not empty.

## Implementation sudo code

# Hockey Teams Data Scraping:

1.Navigate to the website and click on “Hockey Teams: Forms, Searching and Pagination”.
2.Iterate through the table to collect Team Name, Year, and Win % for teams with Win % less than 40%.
3.Collect data from 4 pages and store it in an ArrayList<LinkedHashMap>.
4.Add Epoch Time of Scrape to each LinkedHashMap.
5.Convert the ArrayList LinkedHashMap to a JSON file named hockey-team-data.json and store it in the output folder.
6.Assert that the JSON file is present and not empty.

 
# Oscar Winning Films Data Scraping:

1.Navigate to the website and click on “Oscar Winning Films”.
2.For each year, click to view the top 5 movies.
3.Collect the Year, Title, Nomination, Awards, and isWinner (true for the best picture).
4.Add Epoch Time of Scrape to each LinkedHashMap.
5.Convert the ArrayList<LinkedHashMap> to a JSON file named oscar-winner-data.json and store it in the output folder.
6.Assert that the JSON file is present and not empty.

## Install tion Instructons:
Clear steps to set up and run the project locall .
> Example:
``
# java version 17
java --version
```


## Important Links:
Details about useful external links
 
 https://docs.google.com/document/d/1glgFc5kpDw0rfiEVuuHuccR-U5xPGp2I-HvfUJKRwh8/edit?usp=sharing
