#Author: pranav.smei@gmail.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@poc
Feature: POC
  I want to create POC scenarios
  
  Background:
	Given I open application page

  #@poc1
  #Scenario: Validate Yahoo Page
    #Given I open "test" application page
    #Then The application page with title "Yahoo" should be displayed

  @wip @poc2
  Scenario: Validate Shadow Dom Page
    Then The application page with title "Home - SHOP" should be displayed
    When I click the link "Ladies Outerwear" on the "Shop" page
    Then The application page with title "Ladies Outerwear - SHOP" should be displayed
    When I click the link "Ladies Modern Stretch Full Zip" on the "Ladies Outwear - SHOP" page
    Then The application page with title "Ladies Modern Stretch Full Zip" should be displayed

  #@poc3
  #Scenario Outline: Validate Google search
    #When I search for <keyword>
    #Then I verify the search related to <keyword> is displayed
#
    #Examples: 
      #| keyword |
      #| testing |
      #| quality |
