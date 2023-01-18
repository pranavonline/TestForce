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

  @poc1
  Scenario: Validate Yahoo Page
    Given I open "yahoo" page
    Then "Yahoo" page should be displayed

  @poc2
  Scenario Outline: Validate Google search
    Given I open "google" page
    When I search for <keyword>
    Then I verify the search related to <keyword> is displayed

    Examples: 
      | keyword |
      | testing |
      | quality |
