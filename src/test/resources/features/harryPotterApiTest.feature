@TestAll

Feature: Harry Potter Api Testing
  @Test1
  Scenario: Verify sorting hat

    Given Send a get request to "sortingHat"
    When Verify status code 200, content type "application/json; charset=utf-8"
    Then that response body contains one of the following houses:
        |Gryffindor| Ravenclaw| Slytherin| Hufflepuff|


@Test2

Scenario: Verify bad key

Given Send a get request to "characters"
And Header Accept with value application json
And Query param key with value invalid
When Verify status code 401, content type "application/json; charset=utf-8"
 Then Verify response status line include message "Unauthorized"
And Verify that response body says "error" "API Key Not Found"


  @Test3

  Scenario: Verify no key

  Given Send a get request to "characters"
  And Header Accept with value application json
  When Verify status code 409, content type "application/json; charset=utf-8"
  Then Verify response status line include message "Conflict"
  And Verify that response body says "error" "Must pass API key for request"

@Test4

Scenario: Verify number of characters
Given Send a get request to "characters"
 And Header Accept with value application json
And Query param key with value apiKey
When Verify status code 200, content type "application/json; charset=utf-8"
Then Verify response contains 195 characters

  @Test5

  Scenario: Verify number of character id and house
  Given Send a get request to "characters"
  And Header Accept with value application json
  And Query param key with value apiKey
  When Verify status code 200, content type "application/json; charset=utf-8"
  Then Verify all characters in the response have id field which is not empty
    And Verify that value type of the field dumbledoresArmy is a boolean in all characters in the response
  And Verify value of the house in all characters in the response is one of the following:
  |Gryffindor| Ravenclaw|Slytherin|Hufflepuff|

@Test6
Scenario: Verify all character information

Given Send a get request to "characters"
And Header Accept with value application json
And Query param key with value apiKey
When Verify status code 200, content type "application/json; charset=utf-8"
And Select name of any random character
Then Send a get request to "characters"
And Header Accept with value application json
And Query param key with value apiKey
And Query param name with value from step 3
And Verify that response contains the same character information from step 3. Compare all fields.


  @Test7

  Scenario: Verify name search
  Given Send a get request to "characters"
  And  Header Accept with value application json
  And Query param key with value apiKey
  And Query param name with value Harry Potter
  And Verify status code 200, content type "application/json; charset=utf-8"
  When Verify name Harry Potter
  And Send a get request to "characters"
  And Header Accept with value application json
  And Query param key with value apiKey
  And Query param name with value Marry Potter
  Then Verify status code 200, content type "application/json; charset=utf-8"
  And Verify response body is empty

@Test9 @Test10 @Test15

    Scenario:    Verify house members
    Given Send a get request to "houses"
    And Header Accept with value application json
    And Query param key with value apiKey
    And Verify status code 200, content type "application/json; charset=utf-8"
    When Capture the id of the "Gryffindor" house
    And Capture the ids of the all members of the "Gryffindor" house
    And Send a get request to /houses/:id
    And Header Accept with value application json
    Then Query param key with value apiKey
    And Path param id with value from step 3
    And Verify that response contains the same member ids as the step 4


