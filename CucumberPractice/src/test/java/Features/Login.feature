Feature: Application Login

Scenario: Home Page Default Login
Given User is NetBanking on Landing Page
When User Login into application with username and password
Then Home Page is populated
And Cards are displayed