Feature: Checkers Game Play and Restart

  Scenario: Complete game cycle
    Given I am on the Games for the Brain checkers page
    And the site is active
    When I start a game as orange
    And make five moves including taking a blue piece with "Make a move" confirmations
    Then I restart the game
    And confirm the board is reset with no previous moves
