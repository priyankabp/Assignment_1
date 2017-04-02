# Assignment_1
Student registration

Completed the following requirements:

1. Create an activity for "forget password" with at least:
  -	One TextView,
  -	One EditText for username,
  -	One EditText for email,
  -	One Button.
  
2. Link the "forget password" activity to its button on the MainActivity using intent.

3. Link the reg UI to the main UI.

4. The current registration page fails when the device is in landscape mode (with small screen). You need to fix this problem.

5. You need to add a new EditText (DoB field) on the registration page for Date of Birth.
  - when clicked you need to show DatePickerDialog, accept selected date,  and display it on the DoB field.
  
6. You need to make sure all fields in the registration form are of the right input types.
  - For example: email field should show email keyboard. Hint: use 'android:inputType'.
  - Limit the password to a minimum of 6 characters.
  - Validate that the email has "montclair.edu".
  -All UI text (strings) should be in the String.xml file.
  
7. Place a picture on the landing screen. It should be different to the provided logo.

8. Replace major with a list of pre-defined majors (i.e Computer Science, IT .. ).

Then you have to complete the following assignment-2 requirements:

1- Create an SQLite database that:
  - allows users to register only if:
  - a chosen username doesn’t already exist,
  - have a Montclair email,
  - ‘password’ field and ‘confirm password’ fields match,
  - password should include numbers and characters,
  - DoB has the correct format,
  - None of the registration fields are empty.
  - allows users to update their information (i.e, email, name…),
  - enables forget password validation, 
    you may show the sought password on a separate activity.
  - allows login.
  
2- Create a SharedPreference data file that:

  - if a user logged in successfully, registers all main actions. 
    i.e, if a user with username: JohnSmith then you should record all main actions of John such as visited page1 (activity1) on this   date/time then John moved to page2 on… .
  - have one register per user (i.e, each user has her/his own browsing history).
  
3- All activities should work in portrait mode as well as in landscape mode (must use fragments).

4- Create additional activities to accommodate the previous requirements.
  - i.e: an activity to show the forget password or/and an activity to show the browsing history of a user and so on.

5- You should allow a verified user to write a note and display previous note/s.
  - You should save the notes using FILES.
  
Be creative in the way you link notes to users. i.e by naming, or by creating session-ids and so on.

