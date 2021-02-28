## Cardinfofinder
A simple android application fetches details of a debit or credit card by the card number using the Binlist API.
It accepts input from a user either by manual input or scanning via OCR

# Getting Started
This file will attempt to explain my approach in creating this app. You can download or clone this project to see the source code and run it.

# Development Approach
Language: Kotlin

Architecture: This app is built using the MVVM architecture with one activity (Main), view model as well as directory structures for networking, utils, models and views.

Libraries: Third party dependecies are used in this project: Retrofit library for network calls, espresso for UI test and SpinKit for progress loader

Android app which make use of BINLIST API to get credit card details and display the card information.

Well tested Unit and UI tests with JUnit and Expresso


## Coding / Design
Design pattern used - MVVM (Model-View-ViewModel), Retrofit2, ViewModel, Repository pattern, and Android Recommended App Architecture

App can scan Credit card using PAY-CARD library - ( https://github.com/faceterteam/PayCards_Android) which use ocr

App also automated to send request once your card number 16 - digits is completed


# Project file

api folder contain class to make request to the server (using retrofit)

view folder contains activity - to hold ui

viewmodels folder contain CardViewModel to give any activity that want to observe changes in life cycle.

model folder contain CardDetail, Country, Bank and Number models defined from the response returned from api

interfaces contains -- ApiService which defines the retrofit method and CardResponseInterface to transfer data

utils contains Utils.kt (contains a function to check if there's internet connection and a function to space card number)

## Images of Application Snippet for Project

![Card Info App Image One](https://github.com/Emmanuelcookey15/images/blob/main/Screenshot_20210228-193421.png)

![Card Info App Image Two](https://github.com/Emmanuelcookey15/images/blob/main/Screenshot_20210228-193526.png)



