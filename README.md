## Cardinfofinder
Android app which make use of BINLIST API to get credit card details and display the card information.

Well tested Unit and UI tests with JUnit and Expresso

Design pattern used - MVVM (Model-View-ViewModel), Retrofit2, ViewModel, Repository pattern, and Android Recommended App Architecture

App can scan Credit card using PAY-CARD library - ( https://github.com/faceterteam/PayCards_Android) which use ocr

App also automated to send request once your card number 16 - digits is completed

## Images of Application Snippet for Project

![Card Info App Image One](https://github.com/Emmanuelcookey15/images/blob/main/Screenshot_20210228-193421.png)

![Card Info App Image Two](https://github.com/Emmanuelcookey15/images/blob/main/Screenshot_20210228-193526.png)

# Project file
DB folder store the responds data from api responds

API folder contain class to make request to the server (using retrofit)

UI folder contains { *activity - to hold ui *base -- for activity that share same property, *repository -- since database it contain network request{ do the dirty work to talk to server or local database} *view -- interface to transfer data *viewmodel -- to give any activity that want to observe} }
